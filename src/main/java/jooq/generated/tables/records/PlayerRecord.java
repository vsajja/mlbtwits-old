/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import jooq.generated.tables.Player;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
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
public class PlayerRecord extends UpdatableRecordImpl<PlayerRecord> implements Record14<Integer, String, String, Integer, String, String, Timestamp, Integer, Integer, String, String, String, String, String> {

	private static final long serialVersionUID = -1673326298;

	/**
	 * Setter for <code>public.player.player_id</code>.
	 */
	public PlayerRecord setPlayerId(Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.player_id</code>.
	 */
	public Integer getPlayerId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.player.player_name</code>.
	 */
	public PlayerRecord setPlayerName(String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.player_name</code>.
	 */
	public String getPlayerName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.player.player_name_plain</code>.
	 */
	public PlayerRecord setPlayerNamePlain(String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.player_name_plain</code>.
	 */
	public String getPlayerNamePlain() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.player.team_id</code>.
	 */
	public PlayerRecord setTeamId(Integer value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.team_id</code>.
	 */
	public Integer getTeamId() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>public.player.mlb_player_id</code>.
	 */
	public PlayerRecord setMlbPlayerId(String value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.mlb_player_id</code>.
	 */
	public String getMlbPlayerId() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>public.player.bats</code>.
	 */
	public PlayerRecord setBats(String value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.bats</code>.
	 */
	public String getBats() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>public.player.birth_date</code>.
	 */
	public PlayerRecord setBirthDate(Timestamp value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.birth_date</code>.
	 */
	public Timestamp getBirthDate() {
		return (Timestamp) getValue(6);
	}

	/**
	 * Setter for <code>public.player.height_feet</code>.
	 */
	public PlayerRecord setHeightFeet(Integer value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.height_feet</code>.
	 */
	public Integer getHeightFeet() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>public.player.height_inches</code>.
	 */
	public PlayerRecord setHeightInches(Integer value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.height_inches</code>.
	 */
	public Integer getHeightInches() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>public.player.position</code>.
	 */
	public PlayerRecord setPosition(String value) {
		setValue(9, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.position</code>.
	 */
	public String getPosition() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>public.player.throws</code>.
	 */
	public PlayerRecord setThrows(String value) {
		setValue(10, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.throws</code>.
	 */
	public String getThrows() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>public.player.weight</code>.
	 */
	public PlayerRecord setWeight(String value) {
		setValue(11, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.weight</code>.
	 */
	public String getWeight() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>public.player.birth_country</code>.
	 */
	public PlayerRecord setBirthCountry(String value) {
		setValue(12, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.birth_country</code>.
	 */
	public String getBirthCountry() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>public.player.team_code</code>.
	 */
	public PlayerRecord setTeamCode(String value) {
		setValue(13, value);
		return this;
	}

	/**
	 * Getter for <code>public.player.team_code</code>.
	 */
	public String getTeamCode() {
		return (String) getValue(13);
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
	// Record14 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row14<Integer, String, String, Integer, String, String, Timestamp, Integer, Integer, String, String, String, String, String> fieldsRow() {
		return (Row14) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row14<Integer, String, String, Integer, String, String, Timestamp, Integer, Integer, String, String, String, String, String> valuesRow() {
		return (Row14) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Player.PLAYER.PLAYER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Player.PLAYER.PLAYER_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Player.PLAYER.PLAYER_NAME_PLAIN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Player.PLAYER.TEAM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Player.PLAYER.MLB_PLAYER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return Player.PLAYER.BATS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field7() {
		return Player.PLAYER.BIRTH_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return Player.PLAYER.HEIGHT_FEET;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return Player.PLAYER.HEIGHT_INCHES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return Player.PLAYER.POSITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return Player.PLAYER.THROWS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return Player.PLAYER.WEIGHT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field13() {
		return Player.PLAYER.BIRTH_COUNTRY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field14() {
		return Player.PLAYER.TEAM_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getPlayerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getPlayerName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getPlayerNamePlain();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getTeamId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getMlbPlayerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getBats();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value7() {
		return getBirthDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getHeightFeet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getHeightInches();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getThrows();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getWeight();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value13() {
		return getBirthCountry();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value14() {
		return getTeamCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value1(Integer value) {
		setPlayerId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value2(String value) {
		setPlayerName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value3(String value) {
		setPlayerNamePlain(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value4(Integer value) {
		setTeamId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value5(String value) {
		setMlbPlayerId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value6(String value) {
		setBats(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value7(Timestamp value) {
		setBirthDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value8(Integer value) {
		setHeightFeet(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value9(Integer value) {
		setHeightInches(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value10(String value) {
		setPosition(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value11(String value) {
		setThrows(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value12(String value) {
		setWeight(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value13(String value) {
		setBirthCountry(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord value14(String value) {
		setTeamCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerRecord values(Integer value1, String value2, String value3, Integer value4, String value5, String value6, Timestamp value7, Integer value8, Integer value9, String value10, String value11, String value12, String value13, String value14) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PlayerRecord
	 */
	public PlayerRecord() {
		super(Player.PLAYER);
	}

	/**
	 * Create a detached, initialised PlayerRecord
	 */
	public PlayerRecord(Integer playerId, String playerName, String playerNamePlain, Integer teamId, String mlbPlayerId, String bats, Timestamp birthDate, Integer heightFeet, Integer heightInches, String position, String throws_, String weight, String birthCountry, String teamCode) {
		super(Player.PLAYER);

		setValue(0, playerId);
		setValue(1, playerName);
		setValue(2, playerNamePlain);
		setValue(3, teamId);
		setValue(4, mlbPlayerId);
		setValue(5, bats);
		setValue(6, birthDate);
		setValue(7, heightFeet);
		setValue(8, heightInches);
		setValue(9, position);
		setValue(10, throws_);
		setValue(11, weight);
		setValue(12, birthCountry);
		setValue(13, teamCode);
	}
}
