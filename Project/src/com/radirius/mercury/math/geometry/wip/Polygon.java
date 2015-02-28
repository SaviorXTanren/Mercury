package com.radirius.mercury.math.geometry.wip;

import java.util.ArrayList;

/**
 * A closed figure comprised of one or more line segments, each intersecting with exactly two other line segments.
 *
 * @author wessles
 */
public class Polygon extends Figure {

	public ArrayList<LineSegment> sides = new ArrayList<>();

	@Override
	public void regenerate() {
		meanCenter = new Point(0, 0);
		for (LineSegment side : sides) {
			meanCenter.x += meanCenter.x / sides.size();
			meanCenter.y += meanCenter.y / sides.size();
		}
	}

	@Override
	public void translate(float x, float y) {
		for (LineSegment side : sides)
			translate(x, y);
	}

	@Override
	public void rotate(float originX, float originY, float angle) {
		for (LineSegment side : sides)
			rotate(originX, originY, angle);

		rotation += angle;
	}

	@Override
	public void dilate(float originX, float originY, float scaleFactor) {
		for (LineSegment side : sides)
			dilate(originX, originY, scaleFactor);

		dilation += scaleFactor;
	}

	//TODO add in intersection
	@Override
	public boolean intersects(Figure figure) {
		return false;
	}

	//TODO add in containment
	@Override
	public boolean contains(Figure figure) {
		return false;
	}
}
