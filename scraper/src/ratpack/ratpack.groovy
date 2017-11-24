import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.ccil.cowan.tagsoup.Parser
import ratpack.http.client.HttpClient
import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        // http://localhost:5050/stats?mlbPlayerId=592450
        get('stats') {
            def mlbPlayerId = request.queryParams['mlbPlayerId']

//            def url = "http://m.mlb.com/player/$mlbPlayerId"
//            def doc = new XmlParser(new org.ccil.cowan.tagsoup.Parser()).parse(url)
//            def result = doc.'**'.find {
//                it instanceof Node &&
////                it.name() == 'div' &&
//                        it.@id == 'careerPanel'
//            }

            def mlbHittersAPI = "http://m.mlb.com/lookup/json/named.sport_hitting_composed.bam?game_type=%27R%27&league_list_id=%27mlb_hist%27&sort_by=%27season_asc%27&player_id=$mlbPlayerId"
//            def pitchingStats = "http://m.mlb.com/lookup/json/named.sport_pitching_composed.bam?game_type=%27R%27&league_list_id=%27mlb_hist%27&sort_by=%27season_asc%27&player_id=$mlbPlayerId"

            def result = new JsonSlurper().parseText(mlbHittersAPI.toURL().getText())

            def hittingStats = result.sport_hitting_composed.sport_hitting_tm.queryResults.row
            hittingStats.each { statLine ->
                println "${statLine.season} ${statLine.team_abbrev } ${statLine.ab}ab ${statLine.r}runs ${statLine.hr}hr ${statLine.rbi}rbi ${statLine.sb}sb"
            }
            render new JsonBuilder(hittingStats).toPrettyString()
        }
        files { dir "public" }
    }
}



