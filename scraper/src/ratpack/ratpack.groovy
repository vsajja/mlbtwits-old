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

            render new JsonBuilder(playerNews).toPrettyString()
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}