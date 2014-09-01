package radirius.merc.math.geometry;

import radirius.merc.math.MERCMath;

/**
 * @author wessles
 */

public class Star extends Polygon {
	public Star(float centerx, float centery, float inner_radius, float outer_radius, int numberofsides) {
		this(centerx, centery, inner_radius, inner_radius, outer_radius, outer_radius, numberofsides);
	}

	public Star(float centerx, float centery, float inner_radiusx, float inner_radiusy, float outer_radiusx, float outer_radiusy, int numberofsides) {
		super(centerx, centery, inner_radiusx, inner_radiusy, numberofsides);
		if (numberofsides % 2 != 0)
			throw new IllegalArgumentException("Number of sides needs to be even for a star.");
		extendPoints(outer_radiusx - inner_radiusx, outer_radiusy - inner_radiusy);
	}

	/**
	 * Just goes through every second vertex and shoots it outward to the outer
	 * radius, forming points.
	 */
	protected void extendPoints(float push_radiusx, float push_radiusy) {
		for (int _v = 0; _v < vertices.length; _v += 2) {
			Vec2 v = vertices[_v];
			float angletocenter = MERCMath.atan2(center.x - v.x, center.y - v.y);
			v.add(new Vec2(-MERCMath.cos(angletocenter) * push_radiusx, -MERCMath.sin(angletocenter) * push_radiusy));
		}
	}
}
