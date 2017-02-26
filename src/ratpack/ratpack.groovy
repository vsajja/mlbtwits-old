import com.zaxxer.hikari.HikariConfig
import jooq.generated.tables.pojos.Player
import jooq.generated.tables.pojos.Tweet
import org.apache.commons.lang3.StringUtils
import org.ccil.cowan.tagsoup.Parser
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.mlbtwits.services.MLBTwitsService
import org.mlbtwits.services.MLBTwitsSchedulingService
import org.mlbtwits.services.TwitterStreamService
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
import redis.clients.jedis.Jedis
import twitter4j.FilterQuery
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

import javax.sql.DataSource

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
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }
        module SqlModule

        bind MLBTwitsSchedulingService
        bind MLBTwitsService
        bind TwitterStreamService
    }

    handlers { MLBTwitsService mlbTwitsService ->
        all RequestLogger.ncsa(log)

        get {
            redirect('index.html')
        }

        get('twitter') {
            def players = mlbTwitsService.getPlayers()
            String [] playersArray = players.collect { StringUtils.stripAccents(it.name) }.toArray()
            render playersArray.toString()
        }

        get('redis') {
            String REDIS_URL = "redis://h:pf26cae7217cfb68da5689a2e216e920aca515b310952a09e06d42a6a23f2668f@ec2-34-198-54-21.compute-1.amazonaws.com:29439"

            URI redisURI = new URI(REDIS_URL);
            Jedis jedis = new Jedis(redisURI);
            log.info(jedis.ping())
            jedis.del("RotoworldFeedGuids")
            assert jedis.smembers("RotoworldFeedGuids").isEmpty()
            jedis.close()
            render 'redis'
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
                        def result = mlbTwitsService.getMLBTwits()
                        render json(result)
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
                            log.info(params.toString())
                            def message = params.get('message')?.textValue()

                            assert message

                            mlbTwitsService.tweet(playerId, message)

                        }.then { Tweet tweet ->
                            println "created tweet with id: " + tweet.getTweetId()
                            render json(tweet)
                        }
                    }
                }
            }

            path('labels') {
                byMethod {
                    get {
                        List<Player> players = []
                        render json(players)
                    }
                }
            }
            path('labels/:term') {
                def term = pathTokens['term']
                byMethod {
                    get {
                        List<Player> players = mlbTwitsService.getPlayersByTerm(term)
                        render json(players.collect { ['label' : it.name]})
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
                            assert message

                            List<Tweet> insertedRecords = mlbTwitsService.tweet(message)
                        }.then { List<Tweet> insertedRecords ->
                            render json(insertedRecords)
                        }
                    }
                }
            }
        }

        prefix('test/data') {
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