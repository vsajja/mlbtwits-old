/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.records;


import javax.annotation.Generated;

import jooq.generated.tables.User;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record7<Integer, String, String, String, String, Boolean, String> {

	private static final long serialVersionUID = -1942155085;

	/**
	 * Setter for <code>public.user.user_id</code>.
	 */
	public UserRecord setUserId(Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.user_id</code>.
	 */
	public Integer getUserId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.user.username</code>.
	 */
	public UserRecord setUsername(String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.username</code>.
	 */
	public String getUsername() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.user.email_address</code>.
	 */
	public UserRecord setEmailAddress(String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.email_address</code>.
	 */
	public String getEmailAddress() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.user.first_name</code>.
	 */
	public UserRecord setFirstName(String value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.first_name</code>.
	 */
	public String getFirstName() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>public.user.last_name</code>.
	 */
	public UserRecord setLastName(String value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.last_name</code>.
	 */
	public String getLastName() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>public.user.account_locked</code>.
	 */
	public UserRecord setAccountLocked(Boolean value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.account_locked</code>.
	 */
	public Boolean getAccountLocked() {
		return (Boolean) getValue(5);
	}

	/**
	 * Setter for <code>public.user.password</code>.
	 */
	public UserRecord setPassword(String value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>public.user.password</code>.
	 */
	public String getPassword() {
		return (String) getValue(6);
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
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, String, String, String, String, Boolean, String> fieldsRow() {
		return (Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, String, String, String, String, Boolean, String> valuesRow() {
		return (Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return User.USER.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return User.USER.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return User.USER.EMAIL_ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return User.USER.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return User.USER.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field6() {
		return User.USER.ACCOUNT_LOCKED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return User.USER.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getEmailAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value6() {
		return getAccountLocked();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value1(Integer value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value2(String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value3(String value) {
		setEmailAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value4(String value) {
		setFirstName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value5(String value) {
		setLastName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value6(Boolean value) {
		setAccountLocked(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value7(String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord values(Integer value1, String value2, String value3, String value4, String value5, Boolean value6, String value7) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserRecord
	 */
	public UserRecord() {
		super(User.USER);
	}

	/**
	 * Create a detached, initialised UserRecord
	 */
	public UserRecord(Integer userId, String username, String emailAddress, String firstName, String lastName, Boolean accountLocked, String password) {
		super(User.USER);

		setValue(0, userId);
		setValue(1, username);
		setValue(2, emailAddress);
		setValue(3, firstName);
		setValue(4, lastName);
		setValue(5, accountLocked);
		setValue(6, password);
	}
}