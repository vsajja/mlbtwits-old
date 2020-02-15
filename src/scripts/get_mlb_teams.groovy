import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def response = 'https://statsapi.mlb.com/api/v1/teams'.toURL().text

def allTeams = new JsonSlurper().parseText(response).teams

mlbTeams = allTeams.findAll { team ->
    def leagueName = team.league.name
    def active = team.active

    if (active && leagueName in ['American League', 'National League']) {
        return true
    }
}.collect { team ->
    return [
            'mlb_team_id': team.id,
            'name'       : team.name,
            'code'       : team.abbreviation,
            'league'     : team.league.name
    ]
}

println new JsonBuilder(mlbTeams).toPrettyString()
