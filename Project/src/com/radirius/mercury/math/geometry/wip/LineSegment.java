package com.radirius.mercury.math.geometry.wip;

/**
 * A figure of exactly 2 vertices.
 *
 * @author wessles
 */
public class LineSegment extends Figure {
	public Point beginningPoint;
	public Point endingPoint;

	public LineSegment(Point beginningPoint, Point endingPoint) {
		this.beginningPoint = beginningPoint;
		this.endingPoint = endingPoint;
	}

	@Override
	public void regenerate() {
		meanCenter = new Point((beginningPoint.x + endingPoint.x) / 2, (beginningPoint.y + endingPoint.y) / 2);
	}

	@Override
	public void translate(float x, float y) {
		beginningPoint.translate(x, y);
		endingPoint.translate(x, y);
	}

	@Override
	public void rotate(float originX, float originY, float angle) {
		beginningPoint.rotate(originX, originY, angle);
		endingPoint.rotate(originX, originY, angle);

		rotation += angle;
	}

	@Override
	public void dilate(float originX, float originY, float scaleFactor) {
		beginningPoint.dilate(originX, originY, scaleFactor);
		endingPoint.dilate(originX, originY, scaleFactor);

		dilation += scaleFactor;
	}

	// TODO add in intersection
	@Override
	public boolean intersects(Figure figure) {
		return false;
	}

	// TODO add in containment
	@Override
	public boolean contains(Figure figure) {
		return false;
	}
}
