package org.mlbtwits.jooq

import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.RecordMapperProvider
import org.jooq.RecordType
import org.jooq.impl.DefaultRecordMapper
import org.mlbtwits.pojos.UserTweet

import java.sql.Timestamp

class MLBTwitsRecordMapperProvider implements RecordMapperProvider {
    @Override
    def <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
        if (type.equals(UserTweet.class)) {
            return new UserTweetRecordMapper<R, E>()
        }
        // Fall back to jOOQ's DefaultRecordMapper, which maps records onto
        // POJOs using reflection.
        return new DefaultRecordMapper(recordType, type);
    }

    private class UserTweetRecordMapper<R extends Record, E> implements RecordMapper<R, E> {
        @Override
        E map(R record) {
            Integer tweetId = record.getValue('tweet_id', Integer)
            String message = record.getValue('message', String)
            Timestamp createdTimestamp = record.getValue('created_timestamp', Timestamp)
            Integer playerId = record.getValue('player_id', Integer)
            Integer userId = record.getValue('user_id', Integer)
            String username = record.getValue('username', String)
            UserTweet userTweet = new UserTweet(tweetId, message, createdTimestamp, playerId, userId, username)

            return (E) userTweet
        }
    }
}
