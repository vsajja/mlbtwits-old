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
public class Position implements Serializable {

	private static final long serialVersionUID = 890783014;

	private final Integer positionD;
	private final String  name;

	public Position(Position value) {
		this.positionD = value.positionD;
		this.name = value.name;
	}

	public Position(
		Integer positionD,
		String  name
	) {
		this.positionD = positionD;
		this.name = name;
	}

	public Integer getPositionD() {
		return this.positionD;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Position (");

		sb.append(positionD);
		sb.append(", ").append(name);

		sb.append(")");
		return sb.toString();
	}
}
