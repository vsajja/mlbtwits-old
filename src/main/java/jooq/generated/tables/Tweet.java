/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables;


import java.sql.Timestamp;

import javax.annotation.Generated;

import jooq.generated.Keys;
import jooq.generated.Public;
import jooq.generated.tables.records.TweetRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
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
public class Tweet extends TableImpl<TweetRecord> {

	private static final long serialVersionUID = -349069305;

	/**
	 * The reference instance of <code>public.tweet</code>
	 */
	public static final Tweet TWEET = new Tweet();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TweetRecord> getRecordType() {
		return TweetRecord.class;
	}

	/**
	 * The column <code>public.tweet.tweet_id</code>.
	 */
	public final TableField<TweetRecord, Integer> TWEET_ID = createField("tweet_id", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>public.tweet.message</code>.
	 */
	public final TableField<TweetRecord, String> MESSAGE = createField("message", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>public.tweet.created_timestamp</code>.
	 */
	public final TableField<TweetRecord, Timestamp> CREATED_TIMESTAMP = createField("created_timestamp", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * Create a <code>public.tweet</code> table reference
	 */
	public Tweet() {
		this("tweet", null);
	}

	/**
	 * Create an aliased <code>public.tweet</code> table reference
	 */
	public Tweet(String alias) {
		this(alias, TWEET);
	}

	private Tweet(String alias, Table<TweetRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tweet(String alias, Table<TweetRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TweetRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TWEET;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tweet as(String alias) {
		return new Tweet(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Tweet rename(String name) {
		return new Tweet(name, null);
	}
}
