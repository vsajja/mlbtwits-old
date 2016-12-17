import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import org.ccil.cowan.tagsoup.Parser
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.sql.SqlModule
import ratpack.handling.RequestLogger
import ratpack.hikari.HikariModule
import ratpack.http.client.HttpClient
import org.mlbtwits.postgres.PostgresConfig
import org.mlbtwits.postgres.PostgresModule
import ratpack.http.client.RequestSpec

import javax.sql.DataSource

import static ratpack.groovy.Groovy.ratpack
import static jooq.generated.Tables.*;

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    bindings {
        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.props(
                    ['postgres.user'        : 'elnsxicscvthpy',
                     'postgres.password'    : '42YdDI0OVjJhMhzBEvxA-f5rze',
                     'postgres.portNumber'  : 5432,
                     'postgres.databaseName': 'ddqeubt8e101m',
                     'postgres.serverName'  : 'ec2-54-243-202-113.compute-1.amazonaws.com'])
            builder.build()
        }

        bindInstance PostgresConfig, configData.get('/postgres', PostgresConfig)

        module HikariModule, { HikariConfig config ->
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }
        module SqlModule
    }

    handlers {
        all RequestLogger.ncsa(log)

        get {
            redirect('index.html')
        }

        prefix('api/v1') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()
            }

            path('mlbtwits') {
                byMethod {
                    get {
                        render 'hello plebs'

//                        DataSource dataSource = registry.get(DataSource.class)
//                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
//
//                        def schools = context.selectCount().from(SCHOOL).asField('schools')
//                        def students = context.selectCount().from(STUDENT).asField('students')
//                        def companies = context.selectCount().from(COMPANY).asField('companies')
//                        def jobs = context.selectCount().from(JOB).asField('jobs')
//                        def mines = context.selectCount().from(JOB_MINE).asField('mines')
//
//                        def result = context.select(schools, students, companies, jobs, mines).fetchOneMap()
//                        result.put('countries', 1)
//
//                        render json(result)
                    }
                }
            }
        }

        prefix('test/data') {
            path('stattleship') {
                byMethod {
                    get {
                        URI uri = new URI('https://api.stattleship.com/baseball/mlb/players' +
//                        '?team_id=mlb-bos' +
                        '?per_page=40' +
                        '&player_id=mlb-francisley-bueno')
                        HttpClient httpClient = registry.get(HttpClient.class)
                        httpClient.get(uri) { RequestSpec spec ->
                            spec.headers.set 'Authorization', 'Token token=7181e74000a30ca2a3b10c9bb14f1a09'
                            spec.headers.set 'Content-Type', 'application/json'
                            spec.headers.set 'Accept', 'application/vnd.stattleship.com; version=1'
                        }.then {
                            render JsonOutput.prettyPrint(it.body.text)
                        }
                    }
                }
            }

            path('br/teams/2016') {
                byMethod {
                    get {

                        File mlb2016Teams = new File('src/ratpack/data/baseball-reference-mlb-teams-2016.html')

                        XmlSlurper slurper = new XmlSlurper()

                        def teams = slurper.parse(mlb2016Teams)

                        teams.children().children().each {
                            assert it.a.@title.toString()
                            assert it.a.@href.toString()

                            String bTeamName = it.a.@title.toString()
                            String bTeamCode = it.a.toString()
                            String bTeamUrl = 'http://www.baseball-reference.com' + it.a.@href.toString()

                            log.info(bTeamCode)
                            log.info(bTeamName)
                            log.info(bTeamUrl)
                        }

                        render mlb2016Teams.text
                    }
                }
            }

            path('br/players/2016') {
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

                        File mlb2016Teams = new File('src/ratpack/data/baseball-reference-mlb-teams-2016.html')

                        XmlSlurper slurper = new XmlSlurper()

                        def teams = slurper.parse(mlb2016Teams)

                        teams.children().children().each {
                            assert it.a.@title.toString()
                            assert it.a.@href.toString()

                            String brUrl = 'http://www.baseball-reference.com'

                            String bTeamName = it.a.@title.toString()
                            String bTeamCode = it.a.toString()
                            String bTeamUrl = brUrl + it.a.@href.toString()

                            log.info(bTeamCode)
                            log.info(bTeamName)
                            log.info(bTeamUrl)

                            slurper = new XmlSlurper(new Parser())

                            HttpClient httpClient = registry.get(HttpClient.class)

                            httpClient.get(new URI(bTeamUrl)).then {
                                def teamPage = slurper.parseText(it.body.text)

                                def players = teamPage.depthFirst().findAll {
                                    it.name() == 'a' &&
                                            it.@href.toString().contains('/players/') &&
                                            it.@href.toString() != '/players/'
                                }

                                playerLinks = players.collect { player ->
                                    player.@href.toString().trim()
                                }

                                playerLinks.unique(false).sort().each { playerLink ->
                                    String playerUrl = brUrl + playerLink.toString()

                                    httpClient.get(new URI(playerUrl)).then {
                                        log.info(playerUrl)

                                        def playerPage = slurper.parseText(it.body.text)

                                        String name = playerPage.depthFirst().find {
                                            it.name() == 'span' &&
                                            it.@id == 'player_name'
                                        }.toString()

                                        log.info(name)

                                        log.info("Inserting player: $name")

//                                        context.insertInto(PLAYER)
//                                            .set(PLAYER.NAME, name.trim())
//                                            .execute()
                                    }
                                }
                            }
                        }

                        render mlb2016Teams.text
                    }
                }
            }

            path('br/player') {
                byMethod {
                    get {
                        def playerUrl = 'http://www.baseball-reference.com/players/m/martiru01.shtml'

                        XmlSlurper slurper = new XmlSlurper(new Parser())

                        HttpClient httpClient = registry.get(HttpClient.class)

                        httpClient.get(new URI(playerUrl)).then {
                            def playerPage = slurper.parseText(it.body.text)

                            String name = playerPage.depthFirst().find {
                                it.name() == 'span' &&
                                        it.@id == 'player_name'
                            }.toString()

                            log.info(name)

                            /*
                            def playerInfo = playerPage.depthFirst().find {
                                            it.name() == 'div' &&
                                                    it.@id == 'info_box'
                                        }


                            String headshotUrl  = playerPage.depthFirst().find {
                                it.name().contains('img') &&
                                it.@src.toString().contains('/headshots/')
                            }.toString()

                            log.info(name)
                            log.info(headshotUrl)
                            */
                        }

                        render 'hello'
                    }
                }
            }
        }

        files {
            dir 'dist'
        }
    }
}