package org.mlbtwits.jobs

import com.google.inject.Inject
import org.apache.commons.lang3.StringEscapeUtils
import org.ccil.cowan.tagsoup.Parser
import org.mlbtwits.services.MLBTwitsService
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis

import javax.sql.DataSource
import javax.xml.crypto.Data

public class RotoworldFeed implements org.quartz.Job {
    final Logger log = LoggerFactory.getLogger(this.class)

    @Inject
    MLBTwitsService mlbTwitsService

    @Override
    void execute(JobExecutionContext context) throws JobExecutionException {
        def rssFeed = "http://www.rotoworld.com/rss/feed.aspx?sport=mlb&ftype=news&format=rss".toURL().text.trim()

        XmlSlurper slurper = new XmlSlurper(new Parser())
        def rss = slurper.parseText(rssFeed)

        Map newsMap = [:]

        def items = rss.depthFirst().findAll { it.name() == 'item' }
        items.each { item ->
            String title = item.title.toString()
            String player = title.substring(title.lastIndexOf('-')+1).toString().split('\\|')[0].trim()
            String shortNews = title.substring(0,title.lastIndexOf('-')).trim()
            String news = item.description

            String message = "[~$player] ${StringEscapeUtils.unescapeHtml4(news)}"

            newsMap.put(item.guid.toString(), message)
        }

        String REDIS_URL = "redis://h:p4863685e5dc061d0384fa0bdbd6090e5832fa679a226f76f35fd6d8730adc598@ec2-18-204-150-188.compute-1.amazonaws.com:10809"

        URI redisURI = new URI(REDIS_URL);
        Jedis jedis = new Jedis(redisURI);
        log.info(jedis.ping())
        log.debug(jedis.smembers("RotoworldFeedGuids").toString())

        def user = mlbTwitsService.getUser('BOT_Rotoworld')

        newsMap.each { key, value ->
            log.debug(value.toString())

            if (!jedis.smembers("RotoworldFeedGuids").contains(key)) {
                jedis.sadd("RotoworldFeedGuids", key)
                mlbTwitsService.tweet(user.getUserId().toString(), value)
            }
        }

        jedis.close()
    }
}
