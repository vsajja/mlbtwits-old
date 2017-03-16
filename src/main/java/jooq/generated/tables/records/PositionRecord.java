/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.records;


import javax.annotation.Generated;

import jooq.generated.tables.Position;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
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
public class PositionRecord extends UpdatableRecordImpl<PositionRecord> implements Record2<Integer, String> {

	private static final long serialVersionUID = -643646204;

	/**
	 * Setter for <code>public.position.position_d</code>.
	 */
	public PositionRecord setPositionD(Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.position.position_d</code>.
	 */
	public Integer getPositionD() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.position.position_name</code>.
	 */
	public PositionRecord setPositionName(String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.position.position_name</code>.
	 */
	public String getPositionName() {
		return (String) getValue(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Position.POSITION.POSITION_D;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Position.POSITION.POSITION_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getPositionD();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getPositionName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PositionRecord value1(Integer value) {
		setPositionD(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PositionRecord value2(String value) {
		setPositionName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PositionRecord values(Integer value1, String value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PositionRecord
	 */
	public PositionRecord() {
		super(Position.POSITION);
	}

	/**
	 * Create a detached, initialised PositionRecord
	 */
	public PositionRecord(Integer positionD, String positionName) {
		super(Position.POSITION);

		setValue(0, positionD);
		setValue(1, positionName);
	}
}
