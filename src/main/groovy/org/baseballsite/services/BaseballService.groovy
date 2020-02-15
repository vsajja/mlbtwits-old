package org.baseballsite.services

import com.google.inject.Inject

import javax.sql.DataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import jooq.generated.tables.pojos.*
import static jooq.generated.Tables.*

class BaseballService {
    final Logger log = LoggerFactory.getLogger(this.class)

    DSLContext database

    @Inject
    BaseballService(DataSource dataSource) {
        database = DSL.using(new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.POSTGRES)
        )
    }

    List<MlbTeam> getMlbTeams() {
        List<MlbTeam> mlbTeams = database.selectFrom(MLB_TEAM)
                .fetch()
                .into(MlbTeam.class)
        return mlbTeams
    }

    MlbTeam upsertMlbTeam(MlbTeam mlbTeam) {
        return database.insertInto(MLB_TEAM)
                .set(MLB_TEAM.MLB_TEAM_ID, mlbTeam.mlbTeamId)
                .set(MLB_TEAM.NAME, mlbTeam.name)
                .set(MLB_TEAM.CODE, mlbTeam.code)
                .set(MLB_TEAM.LEAGUE, mlbTeam.league)
                .onDuplicateKeyUpdate()
                .set(MLB_TEAM.MLB_TEAM_ID, mlbTeam.mlbTeamId)
                .set(MLB_TEAM.NAME, mlbTeam.name)
                .set(MLB_TEAM.CODE, mlbTeam.code)
                .set(MLB_TEAM.LEAGUE, mlbTeam.league)
                .returning()
                .fetchOne()
                .into(MlbTeam.class)
    }
}
