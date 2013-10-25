package com.wessles.MERCury.geom;

import com.wessles.MERCury.maths.MTrig;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Ellipse extends Shape {
	public float radx, rady;
	public static final int MAX_VERTS = 60;

	public Ellipse(float x, float y, float radx, float rady) {
		super(getTrigVerts(x, y, radx, rady));
		this.radx = radx;
		this.rady = rady;
	}

	public static Vector2f[] getTrigVerts(float x, float y, float radx, float rady) {
		radx = Math.abs(radx);
		rady = Math.abs(rady);

		Vector2f[] verts = new Vector2f[MAX_VERTS];

		float angle = 0, step = 360 / MAX_VERTS;

		for (int a = 0; a < MAX_VERTS; a++) {
			verts[a] = new Vector2f(x + MTrig.cos(angle) * radx, y + MTrig.sin(angle) * rady);
			angle += step;
		}
		return verts;
	}
}
