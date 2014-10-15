package radirius.merc.math.geometry;

import radirius.merc.math.MathUtil;

/**
 * A shape with any amount of sides.
 *
 * @author wessles
 */
public class Polygon extends Shape {
	private float radius;

	public Polygon(float centerx, float centery, float radius, int numberofsides) {
		this(centerx, centery, radius, radius, numberofsides);
	}

	public Polygon(float centerx, float centery, float radiusx, float radiusy, int numberofsides) {
		this(getTrigVerts(centerx, centery, radiusx, radiusy, numberofsides));
		// Average radius!
		radius = 0.5f * (radiusx + radiusy);
	}

	public Polygon(Vector2f... verts) {
		super(verts);
	}

	public Polygon(float... fs) {
		super(fs);
	}

	@Override
	public boolean contains(Vector2f v) {
		return v.distance(getCenter()) < radius;
	}

	/**
	 * @return Basically the vertices for a whole bunch of triangles 'slices'
	 *         that make up a 'pie.'
	 */
	protected static Vector2f[] getTrigVerts(float x, float y, float radiusx, float radiusy, int numberofsides) {
		if (numberofsides < 3)
			throw new IllegalArgumentException("Polygon must have at least 3 sides!");

		Vector2f[] verts = new Vector2f[numberofsides];

		// start at 270, so that we have an upwards-facing polygon. More fun
		// that way.
		float angle = 270, step = 360 / numberofsides;

		for (int a = 0; a < numberofsides; a++) {
			if (angle > 360)
				angle %= 360;
			verts[a] = new Vector2f(x + MathUtil.cos(angle) * radiusx, y + MathUtil.sin(angle) * radiusy);
			angle += step;
		}

		return verts;
	}
}
