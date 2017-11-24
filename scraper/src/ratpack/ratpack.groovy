import groovy.json.JsonSlurper
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.http.client.HttpClient
import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        // http://localhost:5050/stats?mlbPlayerId=592450
        get('stats') {
            def mlbPlayerId = request.queryParams['mlbPlayerId']
            def stats = []

            def url = "http://m.mlb.com/player/$mlbPlayerId"
            def doc = new XmlParser(new org.ccil.cowan.tagsoup.Parser()).parse(url)

            def careerStats = doc.depthFirst().findAll {
                it instanceof Node &&
//                it.name() == 'div' &&
                it.@class == 'player-stats-summary-large'
            }

            render new JsonSlurper().parseText(careerStats.toListString()).toString()
        }
        files { dir "public" }
    }
}
