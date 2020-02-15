import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import jooq.generated.tables.pojos.MlbTeam
import org.baseballsite.services.BaseballService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.groovy.sql.SqlModule
import ratpack.handling.RequestLogger
import ratpack.hikari.HikariModule
import org.baseballsite.postgres.PostgresConfig
import org.baseballsite.postgres.PostgresModule

import static ratpack.groovy.Groovy.ratpack

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    serverConfig {
        props("db.properties")
        require("/postgres", PostgresConfig)
    }

    bindings {
        module HikariModule, { HikariConfig config ->
            config.setMaximumPoolSize(5)
            config.dataSource = new PostgresModule().dataSource(serverConfig.get('/postgres', PostgresConfig))
        }
        module SqlModule

        bind BaseballService
    }

    handlers { BaseballService baseballService ->
        all RequestLogger.ncsa(log)

        all {
            String forwardedProto = 'X-Forwarded-Proto'
            if (request.headers.contains(forwardedProto)
                    && request.headers.get(forwardedProto) != 'https') {
                redirect(301, "https://${request.headers.get('Host')}${request.rawUri}")
            } else {
                next()
            }
        }

        prefix('api/v1') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'Authorization, origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()

                path('teams') {
                    byMethod {
                        get {
                            render 'GET /api/v1/teams'
                        }
                    }
                }
            }
        }

        prefix('api/data/update') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'Authorization, origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()
            }

            get('teams') {
                def response = 'https://statsapi.mlb.com/api/v1/teams'.toURL().text
                def allTeams = new JsonSlurper().parseText(response).teams

                mlbTeams = allTeams.findAll { team ->
                    def leagueName = team.league.name
                    def active = team.active

                    if (active && leagueName in ['American League', 'National League']) {
                        return true
                    }
                }.collect { team ->
                    return [
                            'mlb_team_id': team.id,
                            'name'       : team.name,
                            'code'       : team.abbreviation,
                            'league'     : team.league.name
                    ]
                }

                mlbTeams.each { mlbTeam ->
                    MlbTeam team = new MlbTeam()
                    team.mlbTeamId = mlbTeam.mlb_team_id
                    team.name = mlbTeam.name
                    team.code = mlbTeam.code
                    team.league = mlbTeam.league


                    // TODO:
                    // def upsertedTeam = baseballService.upsertTeam(team)
                    // println upsertedTeam.toString()
                }

                render new JsonBuilder(mlbTeams).toPrettyString()
            }
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}
