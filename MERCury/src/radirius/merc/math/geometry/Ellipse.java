package radirius.merc.math.geometry;

/**
 * An ellipse that can have a length and width.
 * 
 * @author wessles
 */
public class Ellipse extends Polygon {
	/** The radius in the respective axis. */
	public float radx, rady;
	/**
	 * Maximum amount of sides that can be rendered when rendering an ellipse
	 */
	public static int MAX_SIDES = 40;

	/**
	 * @param x
	 *            The x position of the center.
	 * @param y
	 *            The y position of the center.
	 * @param radx
	 *            The radius of the circle in the x axis.
	 * @param rady
	 *            The radius of the circle in the y axis.
	 */
	public Ellipse(float x, float y, float radx, float rady) {
		super(x, y, radx, rady, MAX_SIDES);
		this.radx = radx;
		this.rady = rady;
	}

	// They are round, with a lot of vertices. This isn't pixel-perfect, but it
	// is good enough.
	@Override
	public boolean intersects(Shape s) {
		if (s instanceof Ellipse)
			for (Vec2 v : s.vertices)
				if (contains(v))
					return true;
		return false;
	}

	@Override
	public boolean contains(Vec2 v) {
		// Source
		// http://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse
		float test = (v.x - getCenter().x) * (v.x - getCenter().x) / (radx * radx) + (v.y - getCenter().y) * (v.y - getCenter().y) / (rady * rady);
		return test <= 1;
	}

	@Override
	public float getArea() {
		return 3.14f * radx * rady;
	}
}
