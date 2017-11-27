package org.mlbtwits.pojos

import jooq.generated.tables.pojos.Tweet

import java.sql.Timestamp

class UserTweet extends Tweet {
    String username

    UserTweet(Integer tweetId, String message, Timestamp createdTimestamp, Integer playerId, Integer userId, String username) {
        super(tweetId, message, createdTimestamp, playerId, userId)
        this.username = username
    }
}
