/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlayerPitchingStatline implements Serializable {

	private static final long serialVersionUID = -345651405;

	private final Integer pitchingStatlineId;
	private final Integer year;
	private final Integer teamId;
	private final Integer wins;
	private final Integer losses;
	private final Double  era;
	private final Integer games;
	private final Integer gamesStarted;
	private final Integer completeGames;
	private final Integer shutouts;
	private final Integer saves;
	private final Integer saveOpps;
	private final Double  innings;
	private final Integer hits;
	private final Integer runs;
	private final Integer earnedRuns;
	private final Integer homeRuns;
	private final Integer hitBatsmen;
	private final Integer walks;
	private final Integer intentionalWalks;
	private final Integer strikeOuts;
	private final Double  average;
	private final Double  whip;
	private final Integer playerId;
	private final Double  bb9;
	private final Double  babip;
	private final Double  hr9;
	private final Integer gidpOpp;
	private final Double  obp;
	private final Double  ops;
	private final Double  slg;

	public PlayerPitchingStatline(PlayerPitchingStatline value) {
		this.pitchingStatlineId = value.pitchingStatlineId;
		this.year = value.year;
		this.teamId = value.teamId;
		this.wins = value.wins;
		this.losses = value.losses;
		this.era = value.era;
		this.games = value.games;
		this.gamesStarted = value.gamesStarted;
		this.completeGames = value.completeGames;
		this.shutouts = value.shutouts;
		this.saves = value.saves;
		this.saveOpps = value.saveOpps;
		this.innings = value.innings;
		this.hits = value.hits;
		this.runs = value.runs;
		this.earnedRuns = value.earnedRuns;
		this.homeRuns = value.homeRuns;
		this.hitBatsmen = value.hitBatsmen;
		this.walks = value.walks;
		this.intentionalWalks = value.intentionalWalks;
		this.strikeOuts = value.strikeOuts;
		this.average = value.average;
		this.whip = value.whip;
		this.playerId = value.playerId;
		this.bb9 = value.bb9;
		this.babip = value.babip;
		this.hr9 = value.hr9;
		this.gidpOpp = value.gidpOpp;
		this.obp = value.obp;
		this.ops = value.ops;
		this.slg = value.slg;
	}

	public PlayerPitchingStatline(
		Integer pitchingStatlineId,
		Integer year,
		Integer teamId,
		Integer wins,
		Integer losses,
		Double  era,
		Integer games,
		Integer gamesStarted,
		Integer completeGames,
		Integer shutouts,
		Integer saves,
		Integer saveOpps,
		Double  innings,
		Integer hits,
		Integer runs,
		Integer earnedRuns,
		Integer homeRuns,
		Integer hitBatsmen,
		Integer walks,
		Integer intentionalWalks,
		Integer strikeOuts,
		Double  average,
		Double  whip,
		Integer playerId,
		Double  bb9,
		Double  babip,
		Double  hr9,
		Integer gidpOpp,
		Double  obp,
		Double  ops,
		Double  slg
	) {
		this.pitchingStatlineId = pitchingStatlineId;
		this.year = year;
		this.teamId = teamId;
		this.wins = wins;
		this.losses = losses;
		this.era = era;
		this.games = games;
		this.gamesStarted = gamesStarted;
		this.completeGames = completeGames;
		this.shutouts = shutouts;
		this.saves = saves;
		this.saveOpps = saveOpps;
		this.innings = innings;
		this.hits = hits;
		this.runs = runs;
		this.earnedRuns = earnedRuns;
		this.homeRuns = homeRuns;
		this.hitBatsmen = hitBatsmen;
		this.walks = walks;
		this.intentionalWalks = intentionalWalks;
		this.strikeOuts = strikeOuts;
		this.average = average;
		this.whip = whip;
		this.playerId = playerId;
		this.bb9 = bb9;
		this.babip = babip;
		this.hr9 = hr9;
		this.gidpOpp = gidpOpp;
		this.obp = obp;
		this.ops = ops;
		this.slg = slg;
	}

	public Integer getPitchingStatlineId() {
		return this.pitchingStatlineId;
	}

	public Integer getYear() {
		return this.year;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public Integer getWins() {
		return this.wins;
	}

	public Integer getLosses() {
		return this.losses;
	}

	public Double getEra() {
		return this.era;
	}

	public Integer getGames() {
		return this.games;
	}

	public Integer getGamesStarted() {
		return this.gamesStarted;
	}

	public Integer getCompleteGames() {
		return this.completeGames;
	}

	public Integer getShutouts() {
		return this.shutouts;
	}

	public Integer getSaves() {
		return this.saves;
	}

	public Integer getSaveOpps() {
		return this.saveOpps;
	}

	public Double getInnings() {
		return this.innings;
	}

	public Integer getHits() {
		return this.hits;
	}

	public Integer getRuns() {
		return this.runs;
	}

	public Integer getEarnedRuns() {
		return this.earnedRuns;
	}

	public Integer getHomeRuns() {
		return this.homeRuns;
	}

	public Integer getHitBatsmen() {
		return this.hitBatsmen;
	}

	public Integer getWalks() {
		return this.walks;
	}

	public Integer getIntentionalWalks() {
		return this.intentionalWalks;
	}

	public Integer getStrikeOuts() {
		return this.strikeOuts;
	}

	public Double getAverage() {
		return this.average;
	}

	public Double getWhip() {
		return this.whip;
	}

	public Integer getPlayerId() {
		return this.playerId;
	}

	public Double getBb9() {
		return this.bb9;
	}

	public Double getBabip() {
		return this.babip;
	}

	public Double getHr9() {
		return this.hr9;
	}

	public Integer getGidpOpp() {
		return this.gidpOpp;
	}

	public Double getObp() {
		return this.obp;
	}

	public Double getOps() {
		return this.ops;
	}

	public Double getSlg() {
		return this.slg;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("PlayerPitchingStatline (");

		sb.append(pitchingStatlineId);
		sb.append(", ").append(year);
		sb.append(", ").append(teamId);
		sb.append(", ").append(wins);
		sb.append(", ").append(losses);
		sb.append(", ").append(era);
		sb.append(", ").append(games);
		sb.append(", ").append(gamesStarted);
		sb.append(", ").append(completeGames);
		sb.append(", ").append(shutouts);
		sb.append(", ").append(saves);
		sb.append(", ").append(saveOpps);
		sb.append(", ").append(innings);
		sb.append(", ").append(hits);
		sb.append(", ").append(runs);
		sb.append(", ").append(earnedRuns);
		sb.append(", ").append(homeRuns);
		sb.append(", ").append(hitBatsmen);
		sb.append(", ").append(walks);
		sb.append(", ").append(intentionalWalks);
		sb.append(", ").append(strikeOuts);
		sb.append(", ").append(average);
		sb.append(", ").append(whip);
		sb.append(", ").append(playerId);
		sb.append(", ").append(bb9);
		sb.append(", ").append(babip);
		sb.append(", ").append(hr9);
		sb.append(", ").append(gidpOpp);
		sb.append(", ").append(obp);
		sb.append(", ").append(ops);
		sb.append(", ").append(slg);

		sb.append(")");
		return sb.toString();
	}
}
