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
		super(shape.getBounds().getX(), shape.getBounds().getY(), shape.getBounds().getWidth(), shape.getBounds().getHeight());

		this.shape = shape;
	}

	/**
	 * Returns The base shape.
	 */
	public Polygon getBase() {
		return shape;
	}

	/**
	 * Renders the shaped entity.
	 */
	@Override
	public void render(Graphics g) {
		g.traceShape((Polygon) shape.setRotation(getBounds().getRotation()));
	}
}
