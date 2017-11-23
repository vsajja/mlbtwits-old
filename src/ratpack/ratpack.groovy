import com.zaxxer.hikari.HikariConfig
import jooq.generated.tables.pojos.Tweet
import jooq.generated.tables.pojos.User
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
import ratpack.session.SessionModule
import org.mlbtwits.postgres.PostgresConfig
import org.mlbtwits.postgres.PostgresModule
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode



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
            config.setMaximumPoolSize(5)
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }
        module SqlModule
        module SessionModule

        bind MLBTwitsSchedulingService
        bind MLBTwitsService
        bind TextTemplateModule
        bind DatabaseUsernamePasswordAuthenticator
        //        bind TwitterStreamService
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

        get("/") {
            render('index.html')
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
                            if(user) {

                                boolean passwordMatches = BCrypt.checkpw(password, user.getPassword())
                                if(passwordMatches) {
                                    return user
                                }
                                else {
                                    throw new IllegalArgumentException()
                                }
                            }
                        }.onError { Throwable e ->
                            if(e instanceof IllegalArgumentException) {
                                clientError(401)
                            }
                        }.then { User user ->
                            if(user) {
                                render json(user)
                            }
                            else {
                                clientError(404)
                            }
                        }
                    }
                }
            }

            post('register') {
                parse(jsonNode()).map { params ->
//                            log.info(params.toString())
                    def username = params.get('username')?.textValue()
                    def password = params.get('password')?.textValue()
//                            def firstName = params.get('firstName')?.textValue()
//                            def lastName = params.get('lastName')?.textValue()
                    def emailAddress = params.get('emailAddress')?.textValue()

                    assert username
                    assert password
                    assert emailAddress
//                            assert firstName
//                            assert lastName

                    mlbTwitsService.registerUser(username, password, emailAddress)
                }.onError { Throwable e ->
                    if(e.message.contains('unique constraint')) {
                        clientError(409)
                    }
                    throw e
                }.then { User user ->
//                            log.info("Registered user with id: " + user.getUserId())
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

            all {
                // TODO: Find a better way, preflight CORS OPTIONS calls do not supply an Authentication header
                if(request.method.isOptions()) {
                    response.send()
                }
                else {
                    next()
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
                            def userId = params.get('userId')?.intValue()\

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

            path('playerLabels') {
                byMethod {
                    get {
                        def players = mlbTwitsService.getPlayers()
                        render json(players.collect { ['playerName' : it.playerName, 'label' : it.playerNamePlain]})
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

//        prefix('test') {
//            get('redis') {
//                String REDIS_URL = "redis://h:pf26cae7217cfb68da5689a2e216e920aca515b310952a09e06d42a6a23f2668f@ec2-34-198-54-21.compute-1.amazonaws.com:29439"
//
//                URI redisURI = new URI(REDIS_URL);
//                Jedis jedis = new Jedis(redisURI);
//                log.info(jedis.ping())
//
//                /*
//                jedis.del("RotoworldFeedGuids")
//                assert jedis.smembers("RotoworldFeedGuids").isEmpty()
//                */
//                jedis.srem("RotoworldFeedGuids", "524674")
//                jedis.close()
//
//                render 'redis'
//            }
//
//            get('trending/yahoo') {
//                String trendingUrl = "https://baseball.fantasysports.yahoo.com/b1/buzzindex?bimtab=ALL&pos=ALL"
//                HttpClient httpClient = registry.get(HttpClient.class)
//
//                XmlSlurper slurper = new XmlSlurper(new Parser())
//                httpClient.get(new URI(trendingUrl)).then {
//                    GPathResult trendingContent = slurper.parseText(it.body.text)
//
//                    List trendingPlayers = []
//
//                    String name
//                    String transactions
//
//                    def topFiveTrending = trendingContent.depthFirst().findAll {
//                        if(it.@class.toString().contains('ysf-player-name')) {
////                            log.info(it.a.text())
//                            name = it.a.text()
//                        }
//
//                        if(it.@class.toString() == 'Alt Last Ta-end Selected') {
//                            transactions = it.div.text()
//
//                            def tPlayer = ['name' : name, 'transactions' : transactions]
//
//                            trendingPlayers.add(tPlayer)
//                        }
//                    }
//
//                    trendingPlayers.take(10).each { tPlayer ->
//                        log.info(tPlayer.name)
//                        log.info(tPlayer.transactions.toString())
//                    }
//
//                    render json(trendingPlayers)
//                }
//            }
//        }
//
//        prefix('test/data') {
//
//            path('mlb/players') {
//                DataSource dataSource = registry.get(DataSource.class)
//                DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
//
//                def teams = context.selectFrom(TEAM)
//                        .fetchInto(Team.class)
//
//                def teamCodes = teams.collect { it.teamCodeMlb.toLowerCase() }
//
//                HttpClient httpClient = registry.get(HttpClient.class)
//
//                teamCodes.each { String teamCode ->
//                    String strAngelsUrl = "http://m.mlb.com/${teamCode}/roster/40-man"
//
//                    httpClient.get(new URI(strAngelsUrl)).then {
//                        log.info("$teamCode " + it.body.text.length().toString())
//
////                        File teamFile = new File("C:\\mlb/rosters/2017/${teamCode}.html")
////                        teamFile.createNewFile()
////                        teamFile << it.body.text
//                    }
//                }
//                render 'mlb players from 40 man rosters'
//            }
//
//            path('mlb/players/info') {
//                DataSource dataSource = registry.get(DataSource.class)
//                DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
//
//                def teams = context.selectFrom(TEAM)
//                        .fetchInto(Team.class)
//
//                def teamCodes = teams.collect { it.mlbTeamCode.toLowerCase() }
//
//                File playerMissingFile = new File("C:\\\\mlb\\missing.txt")
//                File playerFoundFile = new File("C:\\\\mlb\\found.txt")
//
//                XmlSlurper slurper = new XmlSlurper(new Parser())
//
//                teamCodes.each { String teamCode ->
//                    // get the team
//                    Team team = context.selectFrom(TEAM)
//                            .where(TEAM.TEAM_CODE_MLB.equal(teamCode.toUpperCase()))
//                            .fetchInto(Team.class)
//
//                    log.info(team.toString())
//
//                    File teamRosterFile = new File("src/ratpack/data/mlb/rosters/2017/preseason/${teamCode}.html")
//
//                    String teamText = teamRosterFile.text
//
//                    def teamRoster = slurper.parseText(teamText)
//
//                    teamRoster.depthFirst().find {
//    //                    it.@class == 'page page-40-man'
////                        if(it.@class == 'dg-player_headshot') {
////                            log.info(it.img.@src.toString())
////                        }
//                        if(it.@class == 'dg-name_display_first_last' && it.name() == 'td') {
////                            log.info(it.a.@href.toString())
////                            log.info(it.a.@href.toString().split("/")[2])
////                            log.info(it.text().trim())
//
//                            String mlbPlayerId = it.a.@href.toString().split("/")[2]
//                            String mlbPlayerName = (it.text() - '(60-day DL)').trim()
//
//                            log.info(mlbPlayerName)
//
//                            try {
//                                Player player = context.selectFrom(PLAYER)
//                                        .where(PLAYER.PLAYER_NAME_PLAIN.equal(mlbPlayerName))
//                                        .fetchInto(Player.class)
//
////                                context.update(PLAYER)
////                                    .set(PLAYER.TEAM_ID, team.getTeamId())
////                                    .set(PLAYER.MLB_PLAYER_ID, mlbPlayerId)
////                                    .where(PLAYER.PLAYER_ID.eq(player.getPlayerId()))
////                                    .execute()
////
////                                player = context.selectFrom(PLAYER)
////                                        .where(PLAYER.PLAYER_NAME_PLAIN.equal(mlbPlayerName))
////                                        .fetchInto(Player.class)
//
//                                log.info(player.toString())
////                                playerFoundFile << player.toString() + '\n'
//                            } catch (Exception e) {
////                                playerMissingFile << "$mlbPlayerName $mlbPlayerId ${team.getTeamId()} ${team.getTeamCodeMlb()} \n"
//
////                                context.insertInto(PLAYER)
////                                    .set(PLAYER.PLAYER_NAME, mlbPlayerName)
////                                    .set(PLAYER.PLAYER_NAME_PLAIN, mlbPlayerName)
////                                    .set(PLAYER.TEAM_ID, team.getTeamId())
////                                    .set(PLAYER.MLB_PLAYER_ID, mlbPlayerId)
////                                    .execute()
//
//                                log.info("${mlbPlayerName} not found")
//                            }
//                        }
//                    }
//                }
//
////                String teamText = new File('src/ratpack/data/angels.html').text
////                XmlSlurper slurper = new XmlSlurper(new Parser())
////                def angels = slurper.parseText(teamText)
////
////                angels.depthFirst().find {
//////                    it.@class == 'page page-40-man'
////                    if(it.@class == 'dg-player_headshot') {
////                        log.info(it.img.@src.toString())
////                    }
////                    if(it.@class == 'dg-name_display_first_last' && it.name() == 'td') {
////                        log.info(it.a.@href.toString())
////                        log.info(it.a.@href.toString().split("/")[2])
////                        log.info(it.text().trim())
////
////                        try {
////                            Player player = context.selectFrom(PLAYER)
////                                    .where(PLAYER.NAME_PLAIN.equal(it.text().trim()))
////                                    .fetchInto(Player.class)
////                            log.info(player.toString())
////                        } catch (Exception e) {
////                            log.info("${it.text().trim()} not found")
////                        }
////                    }
////                }
//
//                render 'teamText'
//            }
//
//            path('br/teams/2016') {
//                byMethod {
//                    get {
//
//                        File mlb2016Teams = new File('src/ratpack/data/baseball-reference-mlb-teams-2016.html')
//
//                        XmlSlurper slurper = new XmlSlurper()
//
//                        def teams = slurper.parse(mlb2016Teams)
//
//                        teams.children().children().each {
//                            assert it.a.@title.toString()
//                            assert it.a.@href.toString()
//
//                            String bTeamName = it.a.@title.toString()
//                            String bTeamCode = it.a.toString()
//                            String bTeamUrl = 'http://www.baseball-reference.com' + it.a.@href.toString()
//
//                            log.info(bTeamCode)
//                            log.info(bTeamName)
//                            log.info(bTeamUrl)
//
////                            mlbTwitsService.addTeam(bTeamName, bTeamCode)
//                        }
//
//                        render mlb2016Teams.text
//                    }
//                }
//            }
//
//            path('br/players/2016') {
//                byMethod {
//                    get {
//                        DataSource dataSource = registry.get(DataSource.class)
//                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
//
//                        File mlb2016Teams = new File('src/ratpack/data/baseball-reference-mlb-teams-2016.html')
//
//                        XmlSlurper slurper = new XmlSlurper()
//
//                        def teams = slurper.parse(mlb2016Teams)
//
//                        teams.children().children().each {
//                            assert it.a.@title.toString()
//                            assert it.a.@href.toString()
//
//                            String brUrl = 'http://www.baseball-reference.com'
//
//                            String bTeamName = it.a.@title.toString()
//                            String bTeamCode = it.a.toString()
//                            String bTeamUrl = brUrl + it.a.@href.toString()
//
//                            log.info(bTeamCode)
//                            log.info(bTeamName)
//                            log.info(bTeamUrl)
//
//                            slurper = new XmlSlurper(new Parser())
//
//                            HttpClient httpClient = registry.get(HttpClient.class)
//
//                            httpClient.get(new URI(bTeamUrl)).then {
//                                def teamPage = slurper.parseText(it.body.text)
//
//                                def players = teamPage.depthFirst().findAll {
//                                    it.name() == 'a' &&
//                                            it.@href.toString().contains('/players/') &&
//                                            it.@href.toString() != '/players/'
//                                }
//
//                                playerLinks = players.collect { player ->
//                                    player.@href.toString().trim()
//                                }
//
//                                playerLinks.unique(false).sort().each { playerLink ->
//                                    String playerUrl = brUrl + playerLink.toString()
//
//                                    httpClient.get(new URI(playerUrl)).then {
//                                        log.info(playerUrl)
//
//                                        def playerPage = slurper.parseText(it.body.text)
//
//                                        String name = playerPage.depthFirst().find {
//                                            it.name() == 'span' &&
//                                                    it.@id == 'player_name'
//                                        }.toString()
//
//                                        log.info(name)
//
//                                        log.info("Inserting player: $name")
//
////                                        context.insertInto(PLAYER)
////                                            .set(PLAYER.PLAYER_NAME, name.trim())
////                                            .execute()
//                                    }
//                                }
//                            }
//                        }
//
//                        render mlb2016Teams.text
//                    }
//                }
//            }
//
//            path('br/player') {
//                byMethod {
//                    get {
//                        def playerUrl = 'http://www.baseball-reference.com/players/m/martiru01.shtml'
//
//                        XmlSlurper slurper = new XmlSlurper(new Parser())
//
//                        HttpClient httpClient = registry.get(HttpClient.class)
//
//                        httpClient.get(new URI(playerUrl)).then {
//                            def playerPage = slurper.parseText(it.body.text)
//
//                            String name = playerPage.depthFirst().find {
//                                it.name() == 'span' &&
//                                        it.@id == 'player_name'
//                            }.toString()
//
//                            log.info(name)
//
//                            /*
//                            def playerInfo = playerPage.depthFirst().find {
//                                            it.name() == 'div' &&
//                                                    it.@id == 'info_box'
//                                        }
//
//
//                            String headshotUrl  = playerPage.depthFirst().find {
//                                it.name().contains('img') &&
//                                it.@src.toString().contains('/headshots/')
//                            }.toString()
//
//                            log.info(name)
//                            log.info(headshotUrl)
//                            */
//                        }
//
//                        render 'hello'
//                    }
//                }
//            }
//        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
//        files('dist', 'index.html')
    }
}