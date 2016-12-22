import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import jooq.generated.tables.pojos.Player
import jooq.generated.tables.pojos.Tweet
import org.ccil.cowan.tagsoup.Parser
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Result
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
import java.sql.Timestamp

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode
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
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

                        def players = context.selectCount().from(PLAYER).asField('players')

                        def trending = getTrending(context)

                        def result = context.select(players).fetchOneMap()
                        result.put('users', 1)
                        result.put('trending', trending)
                        render json(result)
                    }
                }
            }

            path('players') {
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
                        List<Player> players = context.selectFrom(PLAYER)
                                .fetch()
                                .into(Player.class)
                        render json(players)
                    }
                }
            }

            path('players/:playerId') {
                def playerId = pathTokens['playerId']
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

                        Player player = context.selectFrom(PLAYER)
                                .where(PLAYER.PLAYER_ID.equal(playerId))
                                .fetchOne()
                                .into(Player.class)

                        render json(player)
                    }
                }
            }

            path('players/:playerId/tweets') {
                def playerId = pathTokens['playerId']
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
                        List<Tweet> tweets = context.selectFrom(TWEET)
                                .where(TWEET.PLAYER_ID.equal(playerId))
                                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                                .fetch()
                                .into(Tweet.class)
                        render json(tweets)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            log.info(params.toString())
                            def message = params.get('message')?.textValue()
                            def createdTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())

                            assert message
                            assert createdTimestamp

                            DataSource dataSource = registry.get(DataSource.class)
                            DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

                            record = context
                                    .insertInto(TWEET)
                                    .set(TWEET.MESSAGE, message)
                                    .set(TWEET.CREATED_TIMESTAMP, createdTimestamp)
                                    .set(TWEET.PLAYER_ID, playerId)
                                    .returning()
                                    .fetchOne()
                                    .into(Tweet.class)
                        }.then { Tweet tweet ->
                            println "created tweet with id: " + tweet.getTweetId()
                            render json(tweet)
                        }
                    }
                }
            }

            path('tweets') {
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
                        List<Tweet> tweets = context.selectFrom(TWEET)
                                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                                .fetch()
                                .into(Tweet.class)
                        render json(tweets)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            log.info(params.toString())
                            def message = params.get('message')?.textValue()
                            def createdTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())

                            assert message
                            assert createdTimestamp

                            DataSource dataSource = registry.get(DataSource.class)
                            DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

                            record = context
                                    .insertInto(TWEET)
                                    .set(TWEET.MESSAGE, message)
                                    .set(TWEET.CREATED_TIMESTAMP, createdTimestamp)
                                    .returning()
                                    .fetchOne()
                                    .into(Tweet.class)
                        }.then { Tweet tweet ->
                            println "created tweet with id: " + tweet.getTweetId()
                            render json(tweet)
                        }
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

/**
 *  Currently getTrending just returns the 5 players with the most tweets.
 *  See: http://stackoverflow.com/questions/787496/what-is-the-best-way-to-compute-trending-topics-or-tags
 * @param context
 * @return
 */
def getTrending(context) {
    // get the tweets in last 7 days
    List<Tweet> tweets = context.selectFrom(TWEET)
            .where(TWEET.CREATED_TIMESTAMP.greaterThan(DSL.currentTimestamp().minus(7)))
//            .join(PLAYER)
//            .on(PLAYER.PLAYER_ID.equal(TWEET.PLAYER_ID))
            .fetch()
            .into(Tweet.class)

    // TODO: fix bad code
    // TODO: z-score = ([current trend] - [average historic trends]) / [standard deviation of historic trends]
    def playerIds = tweets.countBy {
        it.playerId
    }.sort {
        a, b -> b.value <=> a.value
    }.findAll {
        it.key != null
    }.keySet().toList()[0..4]

    println playerIds.toString()

    def trending = context.selectFrom(PLAYER)
            .where(PLAYER.PLAYER_ID.in(playerIds))
            .fetch()
            .into(Player.class)

    return trending
}