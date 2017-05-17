package org.mlbtwits.services

import com.google.inject.Inject
import groovy.util.slurpersupport.GPathResult
import jooq.generated.tables.pojos.Player
import jooq.generated.tables.pojos.Team
import jooq.generated.tables.pojos.Tweet
import jooq.generated.tables.pojos.User
import org.ccil.cowan.tagsoup.Parser
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.exec.Blocking
import ratpack.http.client.HttpClient
import redis.clients.jedis.Jedis

import static jooq.generated.Tables.*;

import javax.sql.DataSource

import static ratpack.jackson.Jackson.json

class MLBTwitsService {
    final Logger log = LoggerFactory.getLogger(this.class)

    DSLContext context

    @Inject
    public MLBTwitsService(DataSource dataSource) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES)
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
                .fetch()
                .into(Player.class)
        return players
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

    public List<Tweet> getTweets() {
        List<Tweet> tweets = context.selectFrom(TWEET)
                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                .fetch()
                .into(Tweet.class)
        return tweets
    }

    public List<Tweet> getTweets(String playerId) {
        List<Tweet> tweets = context.selectFrom(TWEET)
                .where(TWEET.PLAYER_ID.equal(playerId))
                .orderBy(TWEET.CREATED_TIMESTAMP.desc())
                .fetch()
                .into(Tweet.class)
        return tweets
    }

    public List<Tweet> tweet(String message) {
        def createdTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())

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
                        .set(TWEET.MESSAGE, message)
                        .set(TWEET.PLAYER_ID, player.playerId)
                        .set(TWEET.CREATED_TIMESTAMP, createdTimestamp)
                        .returning()
                        .fetchOne()
                        .into(Tweet.class)

                insertedRecords.add(record)
            }
        }

        return insertedRecords
    }

    public Tweet tweet(String playerId, String message) {
        def createdTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())

        Tweet tweet = context
                .insertInto(TWEET)
                .set(TWEET.MESSAGE, message)
                .set(TWEET.CREATED_TIMESTAMP, createdTimestamp)
                .set(TWEET.PLAYER_ID, playerId)
                .returning()
                .fetchOne()
                .into(Tweet.class)
        return tweet
    }

    public Team addTeam(String name, String code) {
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
