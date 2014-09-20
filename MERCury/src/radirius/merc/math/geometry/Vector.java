package radirius.merc.math.geometry;

/**
 * A base class for all vectors.
 *
 * @author wessles, Jeviny
 */

public abstract class Vector {
	/**
	 * @param vec
	 *            The vector to add the values of.
	 */
	public abstract Vector add(Vector vec);

	/**
	 * @param vec
	 *            The vector to subtract the values of.
	 */
	public abstract Vector sub(Vector vec);

	/**
	 * @param vec
	 *            The vector to multiply the values of.
	 */
	public abstract Vector mul(Vector vec);

	/**
	 * @param vec
	 *            The vector to divide the values of.
	 */
	public abstract Vector div(Vector vec);

	/**
	 * @param vec
	 *            The vector to derive the values of.
	 */
	public abstract Vector set(Vector vec);

	/**
	 * @param coord
	 *            The coordinates to convert and set to.
	 */
	public abstract Vector set(float... coord);

	/**
	 * @param a
	 *            The value by which to scale each value of the vector.
	 */
	public abstract Vector scale(float a);

	/** Scales the vector by -1. */
	public abstract Vector negate();

	/** @return The length of the vector. */
	public abstract float length();

	/** Normalizes the vector. */
	public abstract Vector normalize();

	/** @return The dot product of me and vec. */
	public abstract float dot(Vector vec);

	/** @return The distance of a vector */
	public abstract float distance(Vector vec);

	/** @return A copy of me. */
	public abstract Vector copy();

	@Override
	public abstract String toString();
}
