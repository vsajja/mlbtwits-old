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
            def mlbPitchingAPI = "http://m.mlb.com/lookup/json/named.sport_pitching_composed.bam?game_type=%27R%27&league_list_id=%27mlb_hist%27&sort_by=%27season_asc%27&player_id=$mlbPlayerId"

            def hittingResult = new JsonSlurper().parseText(mlbHittersAPI.toURL().getText())
            def pitchingResult = new JsonSlurper().parseText(mlbPitchingAPI.toURL().getText())

            def hittingStats = hittingResult.sport_hitting_composed.sport_hitting_tm.queryResults.row
            def pitchingStats = pitchingResult.sport_pitching_composed.sport_pitching_agg.queryResults.row

            println 'HITTING'
            hittingStats.each { statLine ->
                println "${statLine.season} ${statLine.team_abbrev} ${statLine.ab}ab ${statLine.r}runs ${statLine.hr}hr ${statLine.rbi}rbi ${statLine.sb}sb"
            }

            println 'PITCHING'
            pitchingStats.each { statLine ->
                println "${statLine.season} ${statLine.ip}ip ${statLine.w}ip ${statLine.era}era ${statLine.whip}whip ${statLine.so}k ${statLine.sv}sv"
            }

            render 'testing!'
        }
        files { dir "public" }
    }
}



