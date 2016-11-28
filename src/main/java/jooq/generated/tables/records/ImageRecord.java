/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.records;


import javax.annotation.Generated;

import jooq.generated.tables.Image;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class ImageRecord extends UpdatableRecordImpl<ImageRecord> implements Record5<Long, String, String, String, byte[]> {

	private static final long serialVersionUID = -698611830;

	/**
	 * Setter for <code>public.image.image_id</code>.
	 */
	public ImageRecord setImageId(Long value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.image.image_id</code>.
	 */
	public Long getImageId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>public.image.name</code>.
	 */
	public ImageRecord setName(String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.image.name</code>.
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.image.src</code>.
	 */
	public ImageRecord setSrc(String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.image.src</code>.
	 */
	public String getSrc() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.image.url</code>.
	 */
	public ImageRecord setUrl(String value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.image.url</code>.
	 */
	public String getUrl() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>public.image.data</code>.
	 */
	public ImageRecord setData(byte[] value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.image.data</code>.
	 */
	public byte[] getData() {
		return (byte[]) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Long> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, String, String, String, byte[]> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, String, String, String, byte[]> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return Image.IMAGE.IMAGE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Image.IMAGE.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Image.IMAGE.SRC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Image.IMAGE.URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<byte[]> field5() {
		return Image.IMAGE.DATA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getImageId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getSrc();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value5() {
		return getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageRecord value1(Long value) {
		setImageId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageRecord value3(String value) {
		setSrc(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageRecord value4(String value) {
		setUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageRecord value5(byte[] value) {
		setData(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageRecord values(Long value1, String value2, String value3, String value4, byte[] value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ImageRecord
	 */
	public ImageRecord() {
		super(Image.IMAGE);
	}

	/**
	 * Create a detached, initialised ImageRecord
	 */
	public ImageRecord(Long imageId, String name, String src, String url, byte[] data) {
		super(Image.IMAGE);

		setValue(0, imageId);
		setValue(1, name);
		setValue(2, src);
		setValue(3, url);
		setValue(4, data);
	}
}
