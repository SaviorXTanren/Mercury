package com.radirius.mercury.scene;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.math.geometry.Polygon;

/**
 * An entity that's wrapped around a shape.
 *
 * @author Jeviny
 */
public class ShapedEntity extends BasicEntity {
	/**
	 * The shape the entity is wrapped around.
	 */
	private Polygon shape;

	/**
	 * Creates the shaped entity.
	 *
	 * @param shape The base shape.
	 */
	public ShapedEntity(Polygon shape) {
		super((float) shape.getBounds().getX(),
				(float) shape.getBounds().getY(),
				(float) shape.getBounds().getWidth(),
				(float) shape.getBounds().getHeight());

		this.shape = shape;
	}

	/**
	 * @return The base shape.
	 */
	public Polygon getBase() {
		return shape;
	}

	/**
	 * Renders the shaped entity.
	 */
	@Override
	public void render(Graphics g) {
		g.traceShape((Polygon) shape.rotateTo(getBounds().getRotation()));
	}
}
