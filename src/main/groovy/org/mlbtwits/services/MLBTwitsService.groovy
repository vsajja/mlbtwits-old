package org.mlbtwits.services

import com.google.inject.Inject
import com.google.inject.Singleton
import groovy.transform.CompileStatic
import groovy.util.slurpersupport.GPathResult
import jooq.generated.tables.daos.PlayerDao
import jooq.generated.tables.pojos.Player
import jooq.generated.tables.pojos.PlayerHittingStatline
import jooq.generated.tables.pojos.PlayerPitchingStatline
import jooq.generated.tables.pojos.Team
import jooq.generated.tables.pojos.Tweet
import jooq.generated.tables.pojos.User
import org.ccil.cowan.tagsoup.Parser
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.RecordMapperProvider
import org.jooq.RecordType
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultRecordMapper
import org.mindrot.jbcrypt.BCrypt
import org.mlbtwits.jooq.MLBTwitsRecordMapperProvider
import org.mlbtwits.pojos.UserTweet
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.DateFormat
import java.text.SimpleDateFormat

import static jooq.generated.Tables.*;

import javax.sql.DataSource

class MLBTwitsService {
    final Logger log = LoggerFactory.getLogger(this.class)

    DSLContext context

    PlayerDao playerDao

    @Inject
    public MLBTwitsService(DataSource dataSource) {
//        context = DSL.using(dataSource, SQLDialect.POSTGRES)

        context = DSL.using(new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.POSTGRES)
                .set(new MLBTwitsRecordMapperProvider())
//                .set(new RecordMapperProvider() {
//            @Override
//            def <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
//                if (type == UserTweet.class) {
//                    return new RecordMapper<R, E>() {
//                        @Override
//                        E map(R record) {
//                            return null
//                        }
//                    }
//                }
//
//                // Fall back to jOOQ's DefaultRecordMapper, which maps records onto
//                // POJOs using reflection.
//                return new DefaultRecordMapper(recordType, type);
//            }
//        })
        )

        playerDao = new PlayerDao(context.configuration())
    }

    public List<User> getUsers() {
        List<User> users = context.selectFrom(USER)
                .fetch()
                .into(User.class)
        return users
    }

    public User getUser(String username) {
        User user = null
        def userData = context.selectFrom(USER)
                .where(USER.USERNAME.equal(username))
                .fetchOne()
        if(userData) {
            user = userData.into(User.class)
        }
        return user
    }

    public User registerUser(String username, String password, String emailAddress) {
//        def createdTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())
        int BCRYPT_LOG_ROUNDS = 6
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS))
        def user = context.insertInto(USER)
                .set(USER.USERNAME, username)
                .set(USER.PASSWORD, hashedPassword)
                .set(USER.EMAIL_ADDRESS, emailAddress)
                .set(USER.ACCOUNT_LOCKED, false)
                .returning()
                .fetchOne()
                .into(User.class)
        return user
    }

    public List<Team> getTeams() {
        List<Team> teams = context.selectFrom(TEAM)
                .fetch()
                .into(Team.class)
        return teams
    }

    public Team getTeam(String teamId) {
        Team team = context.selectFrom(TEAM)
                .where(TEAM.TEAM_ID.equal(teamId))
                .fetchOne()
                .into(Team.class)
        return team
    }

    public List<Player> getTeamRoster(String teamId) {
        List<Player> players = context.selectFrom(PLAYER)
                .where(PLAYER.TEAM_ID.equal(teamId))
                .fetch()
                .into(Player.class)
        return players
    }

    public List<Player> getPlayers() {
        List<Player> players = context.selectFrom(PLAYER)
                .orderBy(PLAYER.WEIGHT.desc())
                .fetch()
                .into(Player.class)
        return players
    }

    def getPlayerStats(String playerId) {
        List<PlayerHittingStatline> hittingStats = context.selectFrom(PLAYER_HITTING_STATLINE)
                .where(PLAYER_HITTING_STATLINE.PLAYER_ID.eq(playerId))
                .fetch()
                .into(PlayerHittingStatline.class)

        List<PlayerPitchingStatline> pitchingStats = context.selectFrom(PLAYER_PITCHING_STATLINE)
                .where(PLAYER_PITCHING_STATLINE.PLAYER_ID.eq(playerId))
                .fetch()
                .into(PlayerPitchingStatline.class)

        return [ 'hittingStats' : hittingStats, 'pitchingStats' : pitchingStats]
    }

    def getPlayersWithTeams() {
        def sql = context.select()
                .from(PLAYER.join(TEAM).on(PLAYER.TEAM_ID.equal(TEAM.TEAM_ID)))
//                .fetch()
//                .into(Team.class)

//        Player player = PLAYER.as('p')
//        Team team = TEAM.as('t')

//        context.select()
//                .from(player)
//                .join(team)
//                .on(player.)
//                .fetch()
//                .into(Team.class)

//        Box b = BOX.as("b");
//        Support s = SUPPORT.as("s");
//
//        SelectSeekStep1<Integer, Integer> sql = query.select(b.ID, s.ID /* other columns */)
//                .from(b)
//                .join(s)
//                .on(s.ID.eq(b.SUPPORT_ID))
//                .where(s.ID.eq("XXXX"))
//                .orderBy(b.ID)
//        ;

        List<Team> teams = []
        return teams
    }

    public List<Player> getPlayersByTerm(String term) {
        term = term.toLowerCase().trim()

        List<Player> players = context.selectFrom(PLAYER)
                .where(DSL.lower(PLAYER.PLAYER_NAME).like("%$term%"))
                .or(DSL.lower(PLAYER.PLAYER_NAME_PLAIN).like("%$term%"))
                .fetch()
                .into(Player.class)
        return players
    }

    public Player getPlayer(String playerId) {
        Player player = context.selectFrom(PLAYER)
                .where(PLAYER.PLAYER_ID.equal(playerId))
                .fetchOne()
                .into(Player.class)
        return player
    }

    def updatePlayer(Player values) {
        playerDao.update(values)
    }

    public List<UserTweet> getTweets() {
        def tweets = context.select()
                .from(TWEET)
                .join(USER)
                .on(USER.USER_ID.eq(TWEET.USER_ID))
                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                .limit(50)
                .fetch()
                .into(UserTweet.class)
        return tweets
    }

    public List<UserTweet> getTweets(String playerId) {
        List<UserTweet> tweets = context.select()
                .from(TWEET)
                .join(USER)
                .on(USER.USER_ID.eq(TWEET.USER_ID))
                .where(TWEET.PLAYER_ID.equal(playerId))
                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                .fetch()
                .into(UserTweet.class)
        return tweets
    }

    public List<UserTweet> getTweetsByUser(User user) {
        String userId = user.getUserId().toString()
        List<UserTweet> tweets = context.select()
                .from(TWEET)
                .join(USER)
                .on(USER.USER_ID.eq(TWEET.USER_ID))
                .where(TWEET.USER_ID.equal(userId))
                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                .fetch()
                .into(UserTweet.class)
        return tweets
    }

    def tweet(String userId, String message) {
        def createdTimestamp = new java.sql.Timestamp(new Date().getTime())

        List<Tweet> insertedRecords = []

        def result = (message =~ /\[~(.*?)\]/)

        def playerNames = result.collect { it.getAt(1) }
        playerNames.unique().each { String playerName ->
            Record playerRecord = context.selectFrom(PLAYER)
                    .where(PLAYER.PLAYER_NAME.eq(playerName).or(PLAYER.PLAYER_NAME_PLAIN.eq(playerName)))
                    .fetchOne()

            if (playerRecord) {
                Player player = playerRecord.into(Player.class)

                def record = context
                        .insertInto(TWEET)
                        .set(TWEET.MESSAGE, (String) message)
                        .set(TWEET.PLAYER_ID, (String) player.playerId)
                        .set(TWEET.USER_ID, userId)
                        .set(TWEET.CREATED_TIMESTAMP, createdTimestamp)
                        .returning()
                        .fetchOne()
                        .into(Tweet.class)

                insertedRecords.add(record)
            }
        }

        return insertedRecords
    }

    def tweet(String playerId, String userId, String message) {
        def createdTimestamp = new java.sql.Timestamp(new Date().getTime())

        log.info(playerId)
        log.info(message)

        Tweet tweet = context
                .insertInto(TWEET)
                .set(TWEET.MESSAGE, (String) message)
                .set(TWEET.CREATED_TIMESTAMP, (String) createdTimestamp)
                .set(TWEET.PLAYER_ID, playerId)
                .set(TWEET.USER_ID, userId)
                .returning()
                .fetchOne()
                .into(Tweet.class)
        return tweet
    }

    def addTeam(String name, String code) {
        Team team = context.insertInto(TEAM)
                .set(TEAM.TEAM_NAME, name)
                .set(TEAM.TEAM_CODE_MLB, code)
                .returning()
                .fetchOne()
                .into(Team.class)
        return team
    }

    def getMLBTwits() {
        def playerCount = context.selectCount().from(PLAYER).fetchOne(0, int.class)
        def teamCount = context.selectCount().from(TEAM).fetchOne(0, int.class)
        def userCount = context.selectCount().from(USER).fetchOne(0, int.class)

        def result = [:]
        result.put('players', playerCount)
        result.put('teams', teamCount)
        result.put('users', userCount)
        result.put('trending', getTrending())
        return result
    }

    def getTrending() {
        // get the tweets in last 2 days
        List<Tweet> tweets = context.selectFrom(TWEET)
                .where(TWEET.CREATED_TIMESTAMP.greaterThan(DSL.currentTimestamp().minus(1)))
//            .join(PLAYER)
//            .on(PLAYER.PLAYER_ID.equal(TWEET.PLAYER_ID))
                .fetch()
                .into(Tweet.class)

        // TODO: z-score = ([current trend] - [average historic trends]) / [standard deviation of historic trends]
        def playerIds = tweets.countBy {
            it.playerId
        }.sort {
            a, b -> b.value <=> a.value
        }.findAll {
            it.key != null
        }.keySet().toList()
                .take(5)
//        println playerIds.toString()

        def trending = context.selectFrom(PLAYER)
                .where(PLAYER.PLAYER_ID.in(playerIds))
                .fetch()
                .into(Player.class)

        return trending
    }


    /*
    def getTrendingYahoo() {
        List trendingPlayers = []

        def trendingContentStr = "https://baseball.fantasysports.yahoo.com/b1/buzzindex?bimtab=ALL&pos=ALL".toURL().text.trim()
        XmlSlurper slurper = new XmlSlurper(new Parser())

        String name
        String transactions

        GPathResult trendingContent = slurper.parseText(trendingContentStr)
        trendingContent.depthFirst().findAll {
            if (it.@class.toString().contains('ysf-player-name')) {
                name = it.a.text()
            }

            if (it.@class.toString() == 'Alt Last Ta-end Selected') {
                transactions = it.div.text()
                def trendingPlayer = ['playerName': name, 'transactions': transactions]

//                    println trendingPlayer.toString()
                trendingPlayers.add(trendingPlayer)
            }
        }
        return trendingPlayers.take(10)
    }
    */
}
