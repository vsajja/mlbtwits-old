package org.mlbtwits.services

import com.google.inject.Inject
import jooq.generated.tables.pojos.Player
import jooq.generated.tables.pojos.Team
import jooq.generated.tables.pojos.Tweet
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SQLDialect
import org.jooq.impl.DSL

import static jooq.generated.Tables.*;

import javax.sql.DataSource

class MLBTwitsService {
    DSLContext context

    @Inject
    public MLBTwitsService(DataSource dataSource) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES)
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

    public List<Player> getPlayers() {
        List<Player> players = context.selectFrom(PLAYER)
                .fetch()
                .into(Player.class)
        return players
    }

    public List<Player> getPlayersByTerm(String term) {
        term = term.toLowerCase().trim()

        List<Player> players = context.selectFrom(PLAYER)
                .where(DSL.lower(PLAYER.NAME).like("%$term%"))
                .or(DSL.lower(PLAYER.NAME_PLAIN).like("%$term%"))
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
                    .where(PLAYER.NAME.eq(playerName).or(PLAYER.NAME_PLAIN.eq(playerName)))
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
            .set(TEAM.NAME, name)
            .set(TEAM.TEAM_CODE, code)
            .returning()
            .fetchOne()
            .into(Team.class)
        return team
    }

    def getMLBTwits() {
        def playerCount = context.selectCount().from(PLAYER).fetchOne(0, int.class)
        def teamCount = context.selectCount().from(TEAM).fetchOne(0, int.class)

        def result = [:]
        result.put('players', playerCount)
        result.put('teams', teamCount)
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
        println playerIds.toString()

        def trending = context.selectFrom(PLAYER)
                .where(PLAYER.PLAYER_ID.in(playerIds))
                .fetch()
                .into(Player.class)

        return trending
    }
}
