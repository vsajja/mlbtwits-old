package org.mlbtwits.jobs

import com.google.inject.Inject
import groovy.json.JsonSlurper
import org.apache.commons.lang3.StringEscapeUtils
import org.mlbtwits.services.MLBTwitsService
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis

public class MLBPlayerNewsFeed implements org.quartz.Job {
    final Logger log = LoggerFactory.getLogger(this.class)

    @Inject
    MLBTwitsService mlbTwitsService

    @Override
    void execute(JobExecutionContext context) throws JobExecutionException {
        def url = "http://mlb.mlb.com/fantasylookup/json/named.wsfb_news_browse.bam"
        def result = new JsonSlurper().parseText(url.toURL().getText())
        Map newsMap = [:]

        def playerNews = result?.wsfb_news_browse?.queryResults?.row

        def user = mlbTwitsService.getUser('BOT_MLBPlayerNews')

        // FIXME
        String REDIS_URL = "redis://h:pf26cae7217cfb68da5689a2e216e920aca515b310952a09e06d42a6a23f2668f@ec2-34-198-54-21.compute-1.amazonaws.com:29439"
        URI redisURI = new URI(REDIS_URL);
        Jedis jedis = new Jedis(redisURI);
        log.info(jedis.ping())
        log.info(jedis.smembers('MLBPlayerNewsFeedItemIds').toString())

        playerNews.each { newsItem ->
            def playerName = newsItem?.player_name
            def position = newsItem?.position
            def story = newsItem?.story
            def spin = newsItem?.spin
            def teaser = newsItem?.teaser
            def item_id = newsItem?.item_id
            def created = newsItem?.created

            if (!jedis.smembers('MLBPlayerNewsFeedItemIds').contains(item_id)) {
                String message = "[~$playerName] ${StringEscapeUtils.unescapeHtml4(story)}"
                mlbTwitsService.tweet(user.userId.toString(), message, created)
                jedis.sadd('MLBPlayerNewsFeedItemIds', item_id)
                log.info(message.toString())
            }
        }
        jedis.close()
    }
}
