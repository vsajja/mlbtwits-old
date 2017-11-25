/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.records;


import javax.annotation.Generated;

import jooq.generated.tables.PlayerHittingStatline;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record21;
import org.jooq.Row21;
import org.jooq.impl.UpdatableRecordImpl;


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
public class PlayerHittingStatlineRecord extends UpdatableRecordImpl<PlayerHittingStatlineRecord> implements Record21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Double, Double, Double, Double> {

	private static final long serialVersionUID = 401811990;

	/**
	 * Setter for <code>public.player_hitting_statline.hitting_statline_id</code>.
	 */
	public PlayerHittingStatlineRecord setHittingStatlineId(Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.hitting_statline_id</code>.
	 */
	public Integer getHittingStatlineId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.year</code>.
	 */
	public PlayerHittingStatlineRecord setYear(Integer value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.year</code>.
	 */
	public Integer getYear() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.team_id</code>.
	 */
	public PlayerHittingStatlineRecord setTeamId(Integer value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.team_id</code>.
	 */
	public Integer getTeamId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.games</code>.
	 */
	public PlayerHittingStatlineRecord setGames(Integer value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.games</code>.
	 */
	public Integer getGames() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.atbats</code>.
	 */
	public PlayerHittingStatlineRecord setAtbats(Integer value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.atbats</code>.
	 */
	public Integer getAtbats() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.runs</code>.
	 */
	public PlayerHittingStatlineRecord setRuns(Integer value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.runs</code>.
	 */
	public Integer getRuns() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.hits</code>.
	 */
	public PlayerHittingStatlineRecord setHits(Integer value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.hits</code>.
	 */
	public Integer getHits() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.total_bases</code>.
	 */
	public PlayerHittingStatlineRecord setTotalBases(Integer value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.total_bases</code>.
	 */
	public Integer getTotalBases() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.doubles</code>.
	 */
	public PlayerHittingStatlineRecord setDoubles(Integer value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.doubles</code>.
	 */
	public Integer getDoubles() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.triples</code>.
	 */
	public PlayerHittingStatlineRecord setTriples(Integer value) {
		setValue(9, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.triples</code>.
	 */
	public Integer getTriples() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.home_runs</code>.
	 */
	public PlayerHittingStatlineRecord setHomeRuns(Integer value) {
		setValue(10, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.home_runs</code>.
	 */
	public Integer getHomeRuns() {
		return (Integer) getValue(10);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.rbis</code>.
	 */
	public PlayerHittingStatlineRecord setRbis(Integer value) {
		setValue(11, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.rbis</code>.
	 */
	public Integer getRbis() {
		return (Integer) getValue(11);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.walks</code>.
	 */
	public PlayerHittingStatlineRecord setWalks(Integer value) {
		setValue(12, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.walks</code>.
	 */
	public Integer getWalks() {
		return (Integer) getValue(12);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.intentional_walks</code>.
	 */
	public PlayerHittingStatlineRecord setIntentionalWalks(Integer value) {
		setValue(13, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.intentional_walks</code>.
	 */
	public Integer getIntentionalWalks() {
		return (Integer) getValue(13);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.strike_outs</code>.
	 */
	public PlayerHittingStatlineRecord setStrikeOuts(Integer value) {
		setValue(14, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.strike_outs</code>.
	 */
	public Integer getStrikeOuts() {
		return (Integer) getValue(14);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.stolen_bases</code>.
	 */
	public PlayerHittingStatlineRecord setStolenBases(Integer value) {
		setValue(15, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.stolen_bases</code>.
	 */
	public Integer getStolenBases() {
		return (Integer) getValue(15);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.caught_stealing</code>.
	 */
	public PlayerHittingStatlineRecord setCaughtStealing(Integer value) {
		setValue(16, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.caught_stealing</code>.
	 */
	public Integer getCaughtStealing() {
		return (Integer) getValue(16);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.average</code>.
	 */
	public PlayerHittingStatlineRecord setAverage(Double value) {
		setValue(17, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.average</code>.
	 */
	public Double getAverage() {
		return (Double) getValue(17);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.obp</code>.
	 */
	public PlayerHittingStatlineRecord setObp(Double value) {
		setValue(18, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.obp</code>.
	 */
	public Double getObp() {
		return (Double) getValue(18);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.slg</code>.
	 */
	public PlayerHittingStatlineRecord setSlg(Double value) {
		setValue(19, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.slg</code>.
	 */
	public Double getSlg() {
		return (Double) getValue(19);
	}

	/**
	 * Setter for <code>public.player_hitting_statline.ops</code>.
	 */
	public PlayerHittingStatlineRecord setOps(Double value) {
		setValue(20, value);
		return this;
	}

	/**
	 * Getter for <code>public.player_hitting_statline.ops</code>.
	 */
	public Double getOps() {
		return (Double) getValue(20);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record21 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Double, Double, Double, Double> fieldsRow() {
		return (Row21) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Double, Double, Double, Double> valuesRow() {
		return (Row21) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.HITTING_STATLINE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.YEAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.TEAM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.GAMES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.ATBATS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.RUNS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.HITS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.TOTAL_BASES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.DOUBLES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.TRIPLES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.HOME_RUNS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field12() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.RBIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field13() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.WALKS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field14() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.INTENTIONAL_WALKS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field15() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.STRIKE_OUTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field16() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.STOLEN_BASES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field17() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.CAUGHT_STEALING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field18() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.AVERAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field19() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.OBP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field20() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.SLG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field21() {
		return PlayerHittingStatline.PLAYER_HITTING_STATLINE.OPS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getHittingStatlineId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getYear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getTeamId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getGames();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getAtbats();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getRuns();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getHits();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getTotalBases();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getDoubles();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getTriples();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getHomeRuns();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value12() {
		return getRbis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value13() {
		return getWalks();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value14() {
		return getIntentionalWalks();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value15() {
		return getStrikeOuts();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value16() {
		return getStolenBases();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value17() {
		return getCaughtStealing();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value18() {
		return getAverage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value19() {
		return getObp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value20() {
		return getSlg();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value21() {
		return getOps();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value1(Integer value) {
		setHittingStatlineId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value2(Integer value) {
		setYear(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value3(Integer value) {
		setTeamId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value4(Integer value) {
		setGames(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value5(Integer value) {
		setAtbats(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value6(Integer value) {
		setRuns(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value7(Integer value) {
		setHits(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value8(Integer value) {
		setTotalBases(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value9(Integer value) {
		setDoubles(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value10(Integer value) {
		setTriples(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value11(Integer value) {
		setHomeRuns(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value12(Integer value) {
		setRbis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value13(Integer value) {
		setWalks(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value14(Integer value) {
		setIntentionalWalks(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value15(Integer value) {
		setStrikeOuts(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value16(Integer value) {
		setStolenBases(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value17(Integer value) {
		setCaughtStealing(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value18(Double value) {
		setAverage(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value19(Double value) {
		setObp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value20(Double value) {
		setSlg(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord value21(Double value) {
		setOps(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerHittingStatlineRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6, Integer value7, Integer value8, Integer value9, Integer value10, Integer value11, Integer value12, Integer value13, Integer value14, Integer value15, Integer value16, Integer value17, Double value18, Double value19, Double value20, Double value21) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		value13(value13);
		value14(value14);
		value15(value15);
		value16(value16);
		value17(value17);
		value18(value18);
		value19(value19);
		value20(value20);
		value21(value21);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PlayerHittingStatlineRecord
	 */
	public PlayerHittingStatlineRecord() {
		super(PlayerHittingStatline.PLAYER_HITTING_STATLINE);
	}

	/**
	 * Create a detached, initialised PlayerHittingStatlineRecord
	 */
	public PlayerHittingStatlineRecord(Integer hittingStatlineId, Integer year, Integer teamId, Integer games, Integer atbats, Integer runs, Integer hits, Integer totalBases, Integer doubles, Integer triples, Integer homeRuns, Integer rbis, Integer walks, Integer intentionalWalks, Integer strikeOuts, Integer stolenBases, Integer caughtStealing, Double average, Double obp, Double slg, Double ops) {
		super(PlayerHittingStatline.PLAYER_HITTING_STATLINE);

		setValue(0, hittingStatlineId);
		setValue(1, year);
		setValue(2, teamId);
		setValue(3, games);
		setValue(4, atbats);
		setValue(5, runs);
		setValue(6, hits);
		setValue(7, totalBases);
		setValue(8, doubles);
		setValue(9, triples);
		setValue(10, homeRuns);
		setValue(11, rbis);
		setValue(12, walks);
		setValue(13, intentionalWalks);
		setValue(14, strikeOuts);
		setValue(15, stolenBases);
		setValue(16, caughtStealing);
		setValue(17, average);
		setValue(18, obp);
		setValue(19, slg);
		setValue(20, ops);
	}
}
