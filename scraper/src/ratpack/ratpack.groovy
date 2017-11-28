import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    handlers {
        all RequestLogger.ncsa(log)

        get('mlb/player/news') {
            def url = "http://mlb.mlb.com/fantasylookup/json/named.wsfb_news_browse.bam"
            def result = new JsonSlurper().parseText(url.toURL().getText())

            def playerNews = result?.wsfb_news_browse?.queryResults?.row

            playerNews.each { newsItem ->
                def playerName = newsItem?.player_name
                def position = newsItem?.position
                def story = newsItem?.story
                def spin = newsItem?.spin
                def teaser = newsItem?.teaser
                def item_id = newsItem?.item_id

                println "BOT_MLBPLayerNews [~$playerName] $teaser"
            }
            render new JsonBuilder(playerNews).toPrettyString()
        }

        get('players/:mlbPlayerId/mugshot') {
            def mlbPlayerId = pathTokens['mlbPlayerId']

            if(!mlbPlayerId) {
                clientError(400)
            }

            def bytes = 'http://gdx.mlb.com/images/gameday/mugshots/mlb/${mlbPlayerId}@2x.jpg'.toURL().getBytes()
            response.send('image/jpg', bytes)
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}