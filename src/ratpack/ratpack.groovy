import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import jooq.generated.tables.pojos.PlayerHittingStatline
import jooq.generated.tables.pojos.Tweet
import jooq.generated.tables.pojos.User
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.mindrot.jbcrypt.BCrypt
import org.mlbtwits.auth.DatabaseUsernamePasswordAuthenticator
import org.mlbtwits.auth.MLBTwitsProfile
import org.mlbtwits.services.MLBTwitsService
import org.mlbtwits.services.MLBTwitsSchedulingService
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.profile.UserProfile
import org.pac4j.http.client.direct.DirectBasicAuthClient
import org.pac4j.http.profile.HttpProfile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.sql.SqlModule
import ratpack.groovy.template.TextTemplateModule
import ratpack.handling.Context
import ratpack.handling.RequestLogger
import ratpack.hikari.HikariModule
import ratpack.pac4j.RatpackPac4j
import ratpack.server.BaseDir
import ratpack.server.ServerConfig
import ratpack.session.SessionModule
import org.mlbtwits.postgres.PostgresConfig
import org.mlbtwits.postgres.PostgresModule

import javax.sql.DataSource

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode

import static jooq.generated.Tables.*;

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

        bind MLBTwitsSchedulingService
        bind MLBTwitsService
        bind TextTemplateModule
        bind DatabaseUsernamePasswordAuthenticator
    }

    handlers { MLBTwitsService mlbTwitsService, DatabaseUsernamePasswordAuthenticator dbAuthenticator ->
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

        ['login',
         'home',
         'profile',
         'settings',
         'player', 'players', 'players/:playerId',
         'user', 'users', 'users/:userId', 'team', 'teams', 'teams/:teamId'
        ].each { path ->
            get(path) {
                response.contentType('text/html').send new File(this.class.getResource('dist/index.html').toURI()).text
            }
        }

        get('playerNews') {
            render 'mlb player news!'
        }

//        all RatpackPac4j.authenticator(new DirectBasicAuthClient(dbAuthenticator))

        prefix('api/v1') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'Authorization, origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()
            }

            path('login') {
                byMethod {
                    post {
                        parse(jsonNode()).map { params ->
                            def username = params.get('username')?.textValue()
                            def password = params.get('password')?.textValue()

                            assert username
                            assert password

                            def user = mlbTwitsService.getUser(username)
                            if (user) {

                                boolean passwordMatches = BCrypt.checkpw(password, user.getPassword())
                                if (passwordMatches) {
                                    return user
                                } else {
                                    throw new IllegalArgumentException()
                                }
                            }
                        }.onError { Throwable e ->
                            if (e instanceof IllegalArgumentException) {
                                clientError(401)
                            }
                        }.then { User user ->
                            if (user) {
                                render json(user)
                            } else {
                                clientError(404)
                            }
                        }
                    }
                }
            }
            post('register') {
                parse(jsonNode()).map { params ->
                    def username = params.get('username')?.textValue()
                    def password = params.get('password')?.textValue()
                    def emailAddress = params.get('emailAddress')?.textValue()

                    mlbTwitsService.registerUser(username, password, emailAddress)
                }.onError { Throwable e ->
                    if (e.message.contains('unique constraint')) {
                        clientError(409)
                    }
                    throw e
                }.then { User user ->
                    render json(user)
                }
            }
            path('mlbtwits') {
                byMethod {
                    get {
                        def result = mlbTwitsService.getMLBTwits()
                        render json(result)
                    }
                }
            }

//            all RatpackPac4j.requireAuth(DirectBasicAuthClient)

            path('users') {
                byMethod {
                    get {
                        def users = mlbTwitsService.getUsers()
                        render json(users)
                    }
                }
            }
            path('users/:username') {
                def username = pathTokens['username']
                byMethod {
                    get {
                        def user = mlbTwitsService.getUser(username)
                        render json(user)
                    }
                }
            }
            path('users/:username/tweets') {
                def username = pathTokens['username']
                byMethod {
                    get {
                        def user = mlbTwitsService.getUser(username)
                        def tweets = mlbTwitsService.getTweetsByUser(user)
                        render json(tweets)
                    }
                    post {
                        parse(jsonNode()).map { params ->
                            log.info(params.toString())
                            def message = params.get('message')?.textValue()
                            def userId = params.get('userId')?.intValue()

                            assert message
                            assert userId

                            mlbTwitsService.tweet(userId.toString(), message)
                        }.then { Tweet tweet ->
                            println "created tweet with id: " + tweet.getTweetId()
                            render json(tweet)
                        }
                    }
                }
            }
            path('teams') {
                byMethod {
                    get {
                        def teams = mlbTwitsService.getTeams()
                        render json(teams)
                    }
                }
            }
            path('teams/:teamId') {
                def teamId = pathTokens['teamId']
                byMethod {
                    get {
                        def team = mlbTwitsService.getTeam(teamId)
                        render json(team)
                    }
                }
            }
            path('teams/:teamId/roster') {
                def teamId = pathTokens['teamId']
                byMethod {
                    get {
                        def teamRoster = mlbTwitsService.getTeamRoster(teamId)
                        render json(teamRoster)
                    }
                }
            }
            path('players/info') {
                byMethod {
                    get {
                        def players = mlbTwitsService.getPlayersWithTeams()
                        render json(players)
                    }
                }
            }
            path('players') {
                byMethod {
                    get {
                        def players = mlbTwitsService.getPlayers()
                        render json(players)
                    }
                }
            }
            path('players/:playerId') {
                def playerId = pathTokens['playerId']
                byMethod {
                    get {
                        def player = mlbTwitsService.getPlayer(playerId)
                        render json(player)
                    }
                }
            }
            path('players/:playerId/tweets') {
                def playerId = pathTokens['playerId']
                byMethod {
                    get {
                        def tweets = mlbTwitsService.getTweets(playerId)
                        render json(tweets)
                    }
                    post {
                        parse(jsonNode()).map { params ->
                            def message = params.get('message')?.textValue()
                            def userId = params.get('userId')?.intValue()

                            assert message
                            assert userId

                            mlbTwitsService.tweet(playerId, userId.toString(), message)
                        }.then { Tweet tweet ->
                            println "created tweet with id: " + tweet.getTweetId()
                            render json(tweet)
                        }
                    }
                }
            }
            path('players/:playerId/stats') {
                def playerId = pathTokens['playerId']
                byMethod {
                    get {
                        def stats = mlbTwitsService.getPlayerStats(playerId)
                        render json(stats)
                    }
                }
            }
            path('playerLabels') {
                byMethod {
                    get {
                        def players = mlbTwitsService.getPlayers()
                        render json(players.collect { ['playerName': it.playerName, 'label': it.playerNamePlain] })
                    }
                }
            }
            path('tweets') {
                byMethod {
                    get {
                        def tweets = mlbTwitsService.getTweets()
                        render json(tweets)
                    }
                    post {
                        parse(jsonNode()).map { params ->
                            def message = params.get('message')?.textValue()
                            def userId = params.get('userId')?.intValue()
                            assert message
                            assert userId

                            List<Tweet> insertedRecords = mlbTwitsService.tweet(userId.toString(), message)
                        }.then { List<Tweet> insertedRecords ->
                            render json(insertedRecords)
                        }
                    }
                }
            }
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}