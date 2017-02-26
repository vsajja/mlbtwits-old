package org.mlbtwits.services

import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent
import twitter4j.FilterQuery
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

class TwitterStreamService implements Service {
    final Logger log = LoggerFactory.getLogger(this.class)

    TwitterStream twitterStream

    @Inject
    MLBTwitsService mlbTwitsService

    public void onStart(StartEvent event) {
        log.info('STARTED')

        ConfigurationBuilder configBuilder = new ConfigurationBuilder()
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey('FKSh2PZxUP4C5XZaIWG1KGPeb')
                .setOAuthConsumerSecret('NnRzoLwDgiPXJVJ6cTBRitYodpYH1gKo3pIQxotrIMEJzdeOo7')
                .setOAuthAccessToken('35972850-c7V4j270BlomMvk8QndUR9dkNOdBxvADIorGQq0S7')
                .setOAuthAccessTokenSecret('SiTc7PktT6tSq82S14kjRlinNyDATZ20wEsoPHS472tTB');

        Configuration twitterConfig = configBuilder.build()

        StatusListener listener = new StatusListener() {
            @Override
            void onStatus(Status status) {
//                log.info(status.getText())
                String tweet = status.getText()
                tweet = tweet.replaceAll('@MikeTrout', '[~Mike Trout]')
                tweet = tweet.replaceAll('@Bharper3407', '[~Bryce Harper]')

                tweet = "(${status.getUser().getScreenName()}) " + tweet

                if(!tweet.contains('RT')) {
                    log.info(tweet)
                    mlbTwitsService.tweet(tweet)
                }
            }

            @Override
            void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                    log.info('STATUS deleted' + statusDeletionNotice.toString())

            }

            @Override
            void onTrackLimitationNotice(int numberOfLimitedStatuses) {

            }

            @Override
            void onScrubGeo(long userId, long upToStatusId) {

            }

            @Override
            void onStallWarning(StallWarning warning) {

            }

            @Override
            void onException(Exception ex) {

            }
        }

        FilterQuery filter = new FilterQuery()
        String[] query = ['@MikeTrout', '@Bharper3407']
        filter.track(query)

        twitterStream = new TwitterStreamFactory(twitterConfig).getInstance()
        twitterStream.addListener(listener)
        twitterStream.filter(filter)
//        twitterStream.sample()
    }

    public void onStop(StopEvent event) {
        log.info('STOPPED')
        twitterStream.shutdown()
    }
}
