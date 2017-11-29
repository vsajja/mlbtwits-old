import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import jooq.generated.tables.pojos.Player
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
import redis.clients.jedis.Jedis

import javax.sql.DataSource
import java.text.SimpleDateFormat

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

//        get('clearRedis') {
//            String REDIS_URL = "redis://h:pf26cae7217cfb68da5689a2e216e920aca515b310952a09e06d42a6a23f2668f@ec2-34-198-54-21.compute-1.amazonaws.com:29439"
//            URI redisURI = new URI(REDIS_URL);
//            Jedis jedis = new Jedis(redisURI);
//            jedis.del('MLBPlayerNewsFeedItemIds')
//            jedis.del('MLBPlayerNewsFeedIds')
//            jedis.close()
//            render 'test'
//        }

        get('mlb/player/info') {

            def players = mlbTwitsService.getPlayers()

            players.each { player ->
//                log.info(player.playerNamePlain)
                if(player.mlbPlayerId) {
                    def name = URLEncoder.encode("'${player.playerNamePlain.toLowerCase()}'", 'UTF-8')
                    def url = "http://mlb.mlb.com/lookup/json/named.search_player_all.bam?&name_part=${name}"

                    def result = new JsonSlurper().parseText(url.toURL().getText())

                    def playerRow = result?.search_player_all?.queryResults?.row

                    // 2nd part of if statement: some players have multiple player ids!
                    if(playerRow && !playerRow.playerId) {
                        println playerRow.player_id

                        def format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);
                        def parsedDate = format.parse(playerRow.birth_date.toString())
                        def birthDate = new java.sql.Timestamp(parsedDate.getTime())

//                        Player updatedPlayer = new Player(player.playerId,
//                                player.playerName,
//                                player.playerNamePlain,
//                                player.teamId,
//                                playerRow.player_id,
//                                playerRow.bats,
//                                birthDate,
//                                Integer.parseInt(playerRow.height_feet.toString()),
//                                Integer.parseInt(playerRow.height_inches.toString()),
//                                playerRow.position,
//                                playerRow.throws,
//                                playerRow.weight,
//                                playerRow.birth_country)
//                        mlbTwitsService.updatePlayer(updatedPlayer)
                    }
                    else {
                        // still missing
                        log.info('STILL MISSING: ' + player.playerNamePlain)
                    }
                }
            }

            def name = URLEncoder.encode("'aaron judge'", 'UTF-8')
            def url = "http://mlb.mlb.com/lookup/json/named.search_player_all.bam?&name_part=${name}"

            def result = new JsonSlurper().parseText(url.toURL().getText())

            render new JsonBuilder(result).toPrettyString()
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
                        // FIXME: some players are missing stats! eg. weight
                        def players = mlbTwitsService.getPlayers().findAll { it.weight != null }
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
            path('players/:mlbPlayerId/mugshot') {
                def mlbPlayerId = pathTokens['mlbPlayerId']
                byMethod {
                    get {
                        if(!mlbPlayerId) {
                            clientError(400)
                        }

                        def bytes = "http://gdx.mlb.com/images/gameday/mugshots/mlb/${mlbPlayerId}@3x.jpg".toURL().getBytes()
                        response.send('image/jpg', bytes)
                    }
                }
            }
            path('playerLabels') {
                byMethod {
                    get {
                        def players = mlbTwitsService.getPlayers()
                        render json(players.collect { ['playerName': it.playerName, 'playerNamePlain': it.playerNamePlain, 'mlbPlayerId' : it.mlbPlayerId] })
                    }
                }
            }
            path('tweets') {
                byMethod {
                    get {
                        def tweets = mlbTwitsService.getTweets()
                        render json(tweets)
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