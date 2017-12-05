import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import jooq.generated.tables.pojos.Player
import jooq.generated.tables.pojos.PlayerHittingStatline
import jooq.generated.tables.pojos.PlayerPitchingStatline
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


        get('mlb/player/stats/hitting') {
            def players = mlbTwitsService.getPlayers()
//            def players = mlbTwitsService.getPlayersByTerm('jacoby')
//
            players.each { player ->
                assert player.mlbPlayerId

                def url = "http://m.mlb.com/lookup/json/named.sport_hitting_composed.bam?league_list_id=%27mlb_hist%27&player_id=${player.mlbPlayerId}"
                def result = new JsonSlurper().parseText(url.toURL().getText())

                def row = result?.sport_hitting_composed?.sport_hitting_agg?.queryResults?.row.findAll {
                    it != null
                }

                def hittingStatlines = []

                try {
                    if (row instanceof Map) {
                        new JsonBuilder(row).toPrettyString()

                        def year = row?.get('season').toString().toInteger()
                        def games = row?.get('g').toString().toInteger()
                        def atbats = row?.get('ab').toString().toInteger()
                        def runs = row?.get('r').toString().toInteger()
                        def hits = row?.get('h').toString().toInteger()
                        def totalBases = row?.get('tb').toString().toInteger()
                        def doubles = row?.get('d').toString().toInteger()
                        def triples = row?.get('t').toString().toInteger()
                        def homeRuns = row?.get('hr').toString().toInteger()
                        def rbis = row?.get('rbi').toString().toInteger()
                        def walks = row?.get('bb').toString().toInteger()
                        def intentionalWalks = row?.get('ibb').toString().toInteger()
                        def strikeOuts = row?.get('so').toString().toInteger()
                        def stolenBases = row?.get('sb').toString().toInteger()
                        def caughtStealing = row?.get('cs').toString().toInteger()

                        def average = null
                        def obp = null
                        def slg = null
                        def ops = null

                        try {
                            average = Double.parseDouble(row?.get('avg').toString())
                        } catch (NumberFormatException nfe) {
                            average = 0.0
                        }

                        try {
                            obp = Double.parseDouble(row?.get('obp').toString())
                        } catch (NumberFormatException nfe) {
                            obp = 0.0
                        }


                        try {
                            slg = Double.parseDouble(row?.get('slg').toString())
                        } catch (NumberFormatException nfe) {
                            slg = 0.0
                        }


                        try {
                            ops = Double.parseDouble(row?.get('ops').toString())
                        } catch (NumberFormatException nfe) {
                            ops = 0.0
                        }

                        def playerId = player.playerId
                        def teamId = player.teamId

                        assert year != null
                        assert games != null
                        assert atbats != null
                        assert runs != null
                        assert hits != null
                        assert totalBases != null
                        assert doubles != null
                        assert triples != null
                        assert homeRuns != null
                        assert rbis != null
                        assert walks != null
                        assert intentionalWalks != null
                        assert strikeOuts != null
                        assert stolenBases != null
                        assert caughtStealing != null
                        assert average != null
                        assert obp != null
                        assert slg != null
                        assert ops != null

                        PlayerHittingStatline hittingStatline = new PlayerHittingStatline(null,
                                year, player.teamId, games, atbats, runs, hits,
                                totalBases, doubles, triples, homeRuns, rbis,
                                walks, intentionalWalks, strikeOuts, stolenBases,
                                caughtStealing, average, obp, slg, ops, player.playerId)

                        println "${player.playerName} hittingStats: ${hittingStatline.toString()}"
                        mlbTwitsService.addPlayerHittingStatline(hittingStatline)
                    } else {
                        row.each { statLine ->

                            new JsonBuilder(statLine).toPrettyString()

                            def year = statLine?.season.toString().toInteger()
                            def games = statLine?.g.toString().toInteger()
                            def atbats = statLine?.ab.toString().toInteger()
                            def runs = statLine?.r.toString().toInteger()
                            def hits = statLine?.h.toString().toInteger()
                            def totalBases = statLine?.tb.toString().toInteger()
                            def doubles = statLine?.d.toString().toInteger()
                            def triples = statLine?.t.toString().toInteger()
                            def homeRuns = statLine?.hr.toString().toInteger()
                            def rbis = statLine?.rbi.toString().toInteger()
                            def walks = statLine?.bb.toString().toInteger()
                            def intentionalWalks = statLine?.ibb.toString().toInteger()
                            def strikeOuts = statLine?.so.toString().toInteger()
                            def stolenBases = statLine?.sb.toString().toInteger()
                            def caughtStealing = statLine?.cs.toString().toInteger()


                            def average = null
                            def obp = null
                            def slg = null
                            def ops = null

                            try {
                                average = Double.parseDouble(statLine?.avg.toString())
                            } catch (NumberFormatException nfe) {
                                average = 0.0
                            }

                            try {
                                obp = Double.parseDouble(statLine?.obp.toString())
                            } catch (NumberFormatException nfe) {
                                obp = 0.0
                            }


                            try {
                                slg = Double.parseDouble(statLine?.slg.toString())
                            } catch (NumberFormatException nfe) {
                                slg = 0.0
                            }


                            try {
                                ops = Double.parseDouble(statLine?.ops.toString())
                            } catch (NumberFormatException nfe) {
                                ops = 0.0
                            }

                            assert year != null
                            assert games != null
                            assert atbats != null
                            assert runs != null
                            assert hits != null
                            assert totalBases != null
                            assert doubles != null
                            assert triples != null
                            assert homeRuns != null
                            assert rbis != null
                            assert walks != null
                            assert intentionalWalks != null
                            assert strikeOuts != null
                            assert stolenBases != null
                            assert caughtStealing != null
                            assert average != null
                            assert obp != null
                            assert slg != null
                            assert ops != null

                            PlayerHittingStatline hittingStatline = new PlayerHittingStatline(null,
                                    year, player.teamId, games, atbats, runs, hits,
                                    totalBases, doubles, triples, homeRuns, rbis,
                                    walks, intentionalWalks, strikeOuts, stolenBases,
                                    caughtStealing, average, obp, slg, ops, player.playerId)

                            println "${player.playerName} hittingStats: ${hittingStatline.toString()}"
                            mlbTwitsService.addPlayerHittingStatline(hittingStatline)
                        }
                    }
                }
                catch (error) {
                    println 'ERROR'
                    println error.message
                    error.printStackTrace()
                }
            }
            render 'test'
        }

        get('mlb/player/stats/pitching') {
            def players = mlbTwitsService.getPlayers()
//            def players = mlbTwitsService.getPlayersByTerm('clay')
//
            players.each { player ->
                assert player.mlbPlayerId

                def url = "http://m.mlb.com/lookup/json/named.sport_pitching_composed.bam?league_list_id=%27mlb_hist%27&player_id=${player.mlbPlayerId}"
                def result = new JsonSlurper().parseText(url.toURL().getText())

                def row = result?.sport_pitching_composed?.sport_pitching_agg?.queryResults?.row.findAll {
                    it != null
                }

                try {
                    if (row instanceof Map) {
                        def year = row?.get('season').toString().toInteger()
                        def wins = row?.get('w').toString().toInteger()
                        def losses = row?.get('l').toString().toInteger()
                        def games = row?.get('g').toString().toInteger()
                        def gamesStarted = row?.get('gs').toString().toInteger()
                        def completeGames = row?.get('cg').toString().toInteger()
                        def shutouts = row?.get('sho').toString().toInteger()
                        def saves = row?.get('sv').toString().toInteger()
                        def saveOpps = row?.get('svo').toString().toInteger()
                        def hits = row?.get('h').toString().toInteger()
                        def runs = row?.get('r').toString().toInteger()
                        def earnedRuns = row?.get('er').toString().toInteger()
                        def hitBatsmen = row?.get('hb').toString().toInteger()
                        def walks = row?.get('bb').toString().toInteger()
                        def homeRuns = row?.get('hr').toString().toInteger()
                        def intentionalWalks = row?.get('ibb').toString().toInteger()
                        def strikeOuts = row?.get('so').toString().toInteger()
                        def gidpOpp = row?.get('gidp_opp').toString().toInteger()

                        Double era = null
                        try {
                            era = row?.get('era').toString().toDouble()
                        } catch (NumberFormatException e) {
                            era = 0.0
                        }

                        Double innings = null
                        try {
                            innings = row?.get('ip').toString().toDouble()
                        } catch (NumberFormatException e) {
                            innings = 0.0
                        }

                        Double average = null
                        try {
                            average = row?.get('avg').toString().toDouble()
                        } catch (NumberFormatException e) {
                            average = 0.0
                        }

                        Double whip = null
                        try {
                            whip = row?.get('whip').toString().toDouble()
                        } catch (NumberFormatException e) {
                            whip = 0.0
                        }

                        Double babip = null
                        try {
                            babip = row?.get('babip').toString().toDouble()
                        } catch (NumberFormatException e) {
                            babip = 0.0
                        }

                        Double hr9 = null
                        try {
                            hr9 = row?.get('hr9').toString().toDouble()
                        } catch (NumberFormatException e) {
                            hr9 = 0.0
                        }

                        Double bb9 = null
                        try {
                            bb9 = row?.get('bb9').toString().toDouble()
                        } catch (NumberFormatException e) {
                            bb9 = 0.0
                        }

                        Double obp = null
                        try {
                            obp = row?.get('obp').toString().toDouble()
                        } catch (NumberFormatException e) {
                            obp = 0.0
                        }

                        Double ops = null
                        try {
                            ops = row?.get('ops').toString().toDouble()
                        } catch (NumberFormatException e) {
                            ops = 0.0
                        }

                        Double slg = null
                        try {
                            slg = row?.get('slg').toString().toDouble()
                        } catch (NumberFormatException e) {
                            slg = 0.0
                        }

                        assert year != null
                        assert wins != null
                        assert losses != null
                        assert games != null
                        assert gamesStarted != null
                        assert completeGames != null
                        assert shutouts != null
                        assert saves != null
                        assert saveOpps != null
                        assert hits != null
                        assert runs != null
                        assert earnedRuns != null
                        assert hitBatsmen != null
                        assert walks != null
                        assert homeRuns != null
                        assert intentionalWalks != null
                        assert strikeOuts != null
                        assert era != null
                        assert whip != null
                        assert innings != null
                        assert average != null
                        assert obp != null
                        assert slg != null
                        assert gidpOpp != null
                        assert hr9 != null
                        assert babip != null
                        assert bb9 != null


                        PlayerPitchingStatline pitchingStatline = new PlayerPitchingStatline(
                                null,
                                (Integer) year,
                                (Integer) player.teamId,
                                (Integer) wins,
                                (Integer) losses,
                                (Double) era,
                                (Integer) games,
                                (Integer) gamesStarted,
                                (Integer) completeGames,
                                (Integer) shutouts,
                                (Integer) saves,
                                (Integer) saveOpps,
                                (Double) innings,
                                (Integer) hits,
                                (Integer) runs,
                                (Integer) earnedRuns,
                                (Integer) homeRuns,
                                (Integer) hitBatsmen,
                                (Integer) walks,
                                (Integer) intentionalWalks,
                                (Integer) strikeOuts,
                                (Double) average,
                                (Double) whip,
                                (Integer) player.playerId,
                                (Double) bb9,
                                (Double) babip,
                                (Double) hr9,
                                (Integer) gidpOpp,
                                (Double) obp,
                                (Double) ops,
                                (Double) slg
                        )

                        mlbTwitsService.addPlayerPitchingStatline(pitchingStatline)
                        println "${player.playerName} pitchingStats: ${pitchingStatline.toString()}"
                    } else {
                        row.each { statLine ->
                            def year = statLine?.season.toString().toInteger()
                            def wins = statLine?.w.toString().toInteger()
                            def losses = statLine?.l.toString().toInteger()
                            def games = statLine?.g.toString().toInteger()
                            def gamesStarted = statLine?.gs.toString().toInteger()
                            def completeGames = statLine?.cg.toString().toInteger()
                            def shutouts = statLine?.sho.toString().toInteger()
                            def saves = statLine?.sv.toString().toInteger()
                            def saveOpps = statLine?.svo.toString().toInteger()
                            def hits = statLine?.h.toString().toInteger()
                            def runs = statLine?.r.toString().toInteger()
                            def earnedRuns = statLine?.er.toString().toInteger()
                            def hitBatsmen = statLine?.hb.toString().toInteger()
                            def walks = statLine?.bb.toString().toInteger()
                            def homeRuns = statLine?.hr.toString().toInteger()
                            def intentionalWalks = statLine?.ibb.toString().toInteger()
                            def strikeOuts = statLine?.so.toString().toInteger()

                            def gidpOpp = statLine?.gidp_opp.toString().toInteger()

                            Double era = null
                            try {
                                era = row?.era.toString().toDouble()
                            } catch (NumberFormatException e) {
                                era = 0.0
                            }

                            Double innings = null
                            try {
                                innings = row?.ip.toString().toDouble()
                            } catch (NumberFormatException e) {
                                innings = 0.0
                            }

                            Double average = null
                            try {
                                average = row?.avg.toString().toDouble()
                            } catch (NumberFormatException e) {
                                average = 0.0
                            }

                            Double whip = null
                            try {
                                whip = row?.whip.toString().toDouble()
                            } catch (NumberFormatException e) {
                                whip = 0.0
                            }

                            Double babip = null
                            try {
                                babip = row?.babip.toString().toDouble()
                            } catch (NumberFormatException e) {
                                babip = 0.0
                            }

                            Double bb9 = null
                            try {
                                bb9 = row?.bb9.toString().toDouble()
                            } catch (NumberFormatException e) {
                                bb9 = 0.0
                            }

                            Double hr9 = null
                            try {
                                hr9 = row?.hr9.toString().toDouble()
                            } catch (NumberFormatException e) {
                                hr9 = 0.0
                            }

                            Double obp = null
                            try {
                                obp = row?.obp.toString().toDouble()
                            } catch (NumberFormatException e) {
                                obp = 0.0
                            }

                            Double ops = null
                            try {
                                ops = row?.ops.toString().toDouble()
                            } catch (NumberFormatException e) {
                                ops = 0.0
                            }

                            Double slg = null
                            try {
                                slg = row?.slg.toString().toDouble()
                            } catch (NumberFormatException e) {
                                slg = 0.0
                            }

                            assert year != null
                            assert wins != null
                            assert losses != null
                            assert games != null
                            assert gamesStarted != null
                            assert completeGames != null
                            assert shutouts != null
                            assert saves != null
                            assert saveOpps != null
                            assert hits != null
                            assert runs != null
                            assert earnedRuns != null
                            assert hitBatsmen != null
                            assert walks != null
                            assert homeRuns != null
                            assert intentionalWalks != null
                            assert strikeOuts != null
                            assert era != null
                            assert whip != null
                            assert innings != null
                            assert average != null
                            assert obp != null
                            assert slg != null
                            assert gidpOpp != null
                            assert hr9 != null
                            assert babip != null
                            assert bb9 != null

                            PlayerPitchingStatline pitchingStatline = new PlayerPitchingStatline(
                                    null,
                                    (Integer) year,
                                    (Integer) player.teamId,
                                    (Integer) wins,
                                    (Integer) losses,
                                    (Double) era,
                                    (Integer) games,
                                    (Integer) gamesStarted,
                                    (Integer) completeGames,
                                    (Integer) shutouts,
                                    (Integer) saves,
                                    (Integer) saveOpps,
                                    (Double) innings,
                                    (Integer) hits,
                                    (Integer) runs,
                                    (Integer) earnedRuns,
                                    (Integer) homeRuns,
                                    (Integer) hitBatsmen,
                                    (Integer) walks,
                                    (Integer) intentionalWalks,
                                    (Integer) strikeOuts,
                                    (Double) average,
                                    (Double) whip,
                                    (Integer) player.playerId,
                                    (Double) bb9,
                                    (Double) babip,
                                    (Double) hr9,
                                    (Integer) gidpOpp,
                                    (Double) obp,
                                    (Double) ops,
                                    (Double) slg
                            )

                            mlbTwitsService.addPlayerPitchingStatline(pitchingStatline)
                            println "${player.playerName} pitchingStats: ${pitchingStatline.toString()}"
                        }

                    }
                }
                catch (error) {
                    println error.message
                }
            }
            render 'test'
        }

        get('mlb/player/info') {
            def players = mlbTwitsService.getPlayers()
            players.each { player ->
                if (player.mlbPlayerId) {
                    def name = URLEncoder.encode("'${player.playerNamePlain.toLowerCase()}'", 'UTF-8')
                    def url = "http://mlb.mlb.com/lookup/json/named.search_player_all.bam?&name_part=${name}"
                    def result = new JsonSlurper().parseText(url.toURL().getText())

                    def row = result?.search_player_all?.queryResults?.row

                    def playerRows = []
                    if (row && row.playerId) {
                        playerRows.add(row[0])
                    } else {
                        //could be two or more players with the same name
                        println playerRows.class.toString()

                        if (row && row.size() > 0) {
                            playerRows.addAll(row)
                        }
                    }

                    println new JsonBuilder(playerRows).toPrettyString()

                    playerRows.each { playerRow ->
                        // 2nd part of if statement: some players have multiple player ids!
                        if (playerRow && !playerRow.playerId) {
                            println playerRow.player_id

                            def format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);
                            def parsedDate = format.parse(playerRow.birth_date.toString())
                            def birthDate = new java.sql.Timestamp(parsedDate.getTime())

                            Player updatedPlayer = new Player(player.playerId,
                                    player.playerName,
                                    player.playerNamePlain,
                                    player.teamId,
                                    playerRow.player_id,
                                    playerRow.bats,
                                    birthDate,
                                    Integer.parseInt(playerRow.height_feet.toString()),
                                    Integer.parseInt(playerRow.height_inches.toString()),
                                    playerRow.position,
                                    playerRow.throws,
                                    playerRow.weight,
                                    playerRow.birth_country)

                            println player.toString()
                            println birthDate.toString()
                            mlbTwitsService.updatePlayer(updatedPlayer)
                        } else {
                            log.info('STILL MISSING: ' + player.playerNamePlain)
                        }
                    }
                }
            }
            render 'finished'
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
                        // FIXME: some players are missing stats! eg. weight
                        def teamRoster = mlbTwitsService.getTeamRoster(teamId).findAll { it.weight != null }
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
                        if (!mlbPlayerId) {
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
                        render json(players.collect {
                            ['playerName': it.playerName, 'playerNamePlain': it.playerNamePlain, 'mlbPlayerId': it.mlbPlayerId]
                        })
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
