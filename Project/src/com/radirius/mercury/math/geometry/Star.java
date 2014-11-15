package com.radirius.mercury.math.geometry;

import com.radirius.mercury.math.MathUtil;

/**
 * @author wessles
 */
public class Star extends Polygon {
    /**
     * Creates a new star taking in the center x/y
     * positions, the inner/outer radius and number of
     * sides.
     *
     * @param xCenter     The center x position.
     * @param yCenter     The center y position.
     * @param innerRadius The inner radius of the star.
     * @param outerRadius The outer radius of the star.
     * @param numSides    The number of sides on the star.
     */
    public Star(float xCenter, float yCenter, float innerRadius, float outerRadius, int numSides) {
        this(xCenter, yCenter, innerRadius, innerRadius, outerRadius, outerRadius, numSides);
    }

    /**
     * Creates a new star taking in the center x/y
     * positions, the x/y inner/outer radius and number of
     * sides.
     *
     * @param xCenter      The center x position.
     * @param yCenter      The center y position.
     * @param innerXRadius The inner x radius of the star.
     * @param innerYRadius The inner y radius of the star.
     * @param outerXRadius The outer x radius of the star.
     * @param outerYRadius The outer y radius of the star.
     * @param numSides     The number of sides on the star.
     */
    public Star(float xCenter, float yCenter, float innerXRadius, float innerYRadius, float outerXRadius, float outerYRadius, int numSides) {
        super(xCenter, yCenter, innerXRadius, innerYRadius, numSides);

        if (numSides % 2 != 0)
            throw new IllegalArgumentException("Number of sides needs to be even for a star.");

        extendPoints(outerXRadius - innerXRadius, outerYRadius - innerYRadius);
    }

    /**
     * Just goes through every second vertex and shoots it
     * outward to the outer radius, forming points.
     */
    protected void extendPoints(float push_radiusx, float push_radiusy) {
        for (int v0 = 0; v0 < vertices.length; v0 += 2) {
            Vector2f v1 = vertices[v0];

            float angletocenter = MathUtil.atan2(center.x - v1.x, center.y - v1.y);

            v1.add(new Vector2f(-MathUtil.cos(angletocenter) * push_radiusx, -MathUtil.sin(angletocenter) * push_radiusy));
        }
    }
}
