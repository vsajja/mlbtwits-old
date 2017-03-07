/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooq.generated.Keys;
import jooq.generated.Public;
import jooq.generated.tables.records.PositionRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Position extends TableImpl<PositionRecord> {

	private static final long serialVersionUID = -1797877014;

	/**
	 * The reference instance of <code>public.position</code>
	 */
	public static final Position POSITION = new Position();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<PositionRecord> getRecordType() {
		return PositionRecord.class;
	}

	/**
	 * The column <code>public.position.position_d</code>.
	 */
	public final TableField<PositionRecord, Integer> POSITION_D = createField("position_d", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.position.name</code>.
	 */
	public final TableField<PositionRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * Create a <code>public.position</code> table reference
	 */
	public Position() {
		this("position", null);
	}

	/**
	 * Create an aliased <code>public.position</code> table reference
	 */
	public Position(String alias) {
		this(alias, POSITION);
	}

	private Position(String alias, Table<PositionRecord> aliased) {
		this(alias, aliased, null);
	}

	private Position(String alias, Table<PositionRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<PositionRecord, Integer> getIdentity() {
		return Keys.IDENTITY_POSITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<PositionRecord> getPrimaryKey() {
		return Keys.POSITION_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<PositionRecord>> getKeys() {
		return Arrays.<UniqueKey<PositionRecord>>asList(Keys.POSITION_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position as(String alias) {
		return new Position(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Position rename(String name) {
		return new Position(name, null);
	}
}