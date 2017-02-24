package org.mlbtwits.services

import com.google.inject.Inject
import jooq.generated.tables.pojos.Player
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
                .fetch()
                .into(Player.class)
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
                    .where(PLAYER.NAME.eq(playerName))
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

    def getMLBTwits() {
        def players = context.selectCount().from(PLAYER).asField('players')

        def result = context.select(players).fetchOneMap()
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
//                .take(10)
        println playerIds.toString()

        def trending = context.selectFrom(PLAYER)
                .where(PLAYER.PLAYER_ID.in(playerIds))
                .fetch()
                .into(Player.class)

        return trending
    }
}
