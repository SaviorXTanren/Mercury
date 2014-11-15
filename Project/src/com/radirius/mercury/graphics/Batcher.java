package com.radirius.mercury.graphics;

import com.radirius.mercury.math.geometry.Shape;

/**
 * An abstraction for batchers.
 *
 * @author wessles, Sri Harsha Chilakapati
 */
public interface Batcher {

    /**
     * Make active.
     */
    public void begin();

    /**
     * @return Whether or not I am active.
     */
    public boolean isActive();

    /**
     * Make inactive, and push data to OpenGL.
     */
    public void end();

    /**
     * Pushes data to OpenGL.
     */
    public void flush();

    /**
     * Clears current color.
     */
    public void clearColors();

    /**
     * @return The current color.
     */
    public Color getColor();

    /**
     * @param color Color to set to.
     */
    public void setColor(Color color);

    /**
     * Clears current shader.
     */
    public void clearShaders();

    /**
     * @return The current shader.
     */
    public Shader getShader();

    /**
     * @param shader Shader to set to.
     */
    public void setShader(Shader shader);

    /**
     * Setting and activating a texture index.
     *
     * @param texture     Texture to set to.
     * @param activeIndex The index of the active texture to set
     *                    (i.e. 'GL13.GL_TEXTURE0').
     */
    public void setTexture(Texture texture, int activeIndex);

    /**
     * Activating a texture index.
     *
     * @param activateIndex The index of the active texture (i.e.
     *                      'GL13.GL_TEXTURE0').
     */
    public void activateTexture(int activateIndex);

    /**
     * Clears the current texture.
     */
    public void clearTextures();

    /**
     * @return The current texture.
     */
    public Texture getTexture();

    /**
     * Set a texture.
     *
     * @param texture Texture to set to.
     */
    public void setTexture(Texture texture);

    /**
     * Draws a Texture texture at x and y.
     */
    public void drawTexture(Texture texture, float x, float y);

    /**
     * Draws a Texture texture at x and y at w and h size.
     */
    public void drawTexture(Texture texture, float x, float y, float w, float h);

    /**
     * Draws a Texture texture at x and y at w and h size,
     * rotated rot degrees by the local origin (0, 0).
     */
    public void drawTexture(Texture texture, float x, float y, float w, float h, float rot);

    /**
     * Draws a Texture texture at x and y at w and h size,
     * rotated rot degrees by the local origin
     * (local_origin_x, local_origin_y).
     */
    public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, float localOriginX, float localOriginY);

    /**
     * Draws a portion of the texture at x and y.
     */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y);

    /**
     * Draws a portion of the texture at x1 and y1, to x2
     * and y2.
     */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h);

    /**
     * Draws a Texture texture to the Shape region.
     */
    public void drawTexture(Texture texture, Shape region);

    /**
     * Draws a portion of a texture to the Shape region.
     */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Shape region);

    /**
     * Draws the portion sourceregion of a texture to the
     * Shape region
     */
    public void drawTexture(Texture texture, Shape sourceRegion, Shape region);

    /**
     * Draws a Texture texture at x and y with a tint
     */
    public void drawTexture(Texture texture, float x, float y, Color tint);

    /**
     * Draws a Texture texture at x and y at w and h size
     * with a tint
     */
    public void drawTexture(Texture texture, float x, float y, float w, float h, Color tint);

    /**
     * Draws a Texture texture at x and y at w and h size,
     * rotated rot degrees by the local origin (0, 0) with a
     * tint
     */
    public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, Color tint);

    /**
     * Draws a Texture texture at x and y at w and h size,
     * rotated rot degrees by the local origin
     * (local_origin_x, local_origin_y) with a tint
     */
    public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, float localOriginX, float localOriginY, Color tint);

    /**
     * Draws a portion of the texture at x and y with a tint
     */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, Color tint);

    /**
     * Draws a portion of the texture at x1 and y1, to x2
     * and y2 with a tint
     */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h, Color tint);

    /**
     * Draws a Texture texture to the Shape region with a
     * tint
     */
    public void drawTexture(Texture texture, Shape region, Color tint);

    /**
     * Draws a portion of a texture to the Shape region with
     * a tint
     */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Shape region, Color tint);

    /**
     * Draws the portion sourceRegion of a texture to the
     * Shape region with a tint
     */
    public void drawTexture(Texture texture, Shape sourceRegion, Shape region, Color tint);

    /**
     * Adds a vertex to the stack.
     *
     * @param x The x position.
     * @param y The y position.
     * @param u The x texture coordinate.
     * @param v The y texture coordinate.
     */
    public void vertex(float x, float y, float u, float v);

    /**
     * Adds a vertex to the stack.
     *
     * @param x     The x position.
     * @param y     The y position.
     * @param color The color of the vertex.
     * @param u     The x texture coordinate.
     * @param v     The y texture coordinate.
     */
    public void vertex(float x, float y, Color color, float u, float v);

    /**
     * Adds a vertex to the stack.
     *
     * @param x The x position.
     * @param y The y position.
     * @param r The red value of color of the vertex.
     * @param g The green value of color of the vertex.
     * @param b The blue value of color of the vertex.
     * @param u The x texture coordinate.
     * @param v The y texture coordinate.
     */
    public void vertex(float x, float y, float r, float g, float b, float u, float v);

    /**
     * Adds a vertex to the stack.
     *
     * @param x The x position.
     * @param y The y position.
     * @param r The red value of color of the vertex.
     * @param g The green value of color of the vertex.
     * @param b The blue value of color of the vertex.
     * @param a The alpha value of color of the vertex.
     * @param u The x texture coordinate.
     * @param v The y texture coordinate.
     */
    public void vertex(float x, float y, float r, float g, float b, float a, float u, float v);

    /**
     * Will flush the data to OpenGL if the sum of the
     * vertex count and the allocation is higher than the
     * limit.
     *
     * @param allocate The amount of vertices that will be
     *                 rendered in the next vertex-group.
     */
    public void flushOnOverflow(int allocate);

    /**
     * @return The amount of vertices rendered last render
     * frame.
     */
    public int getVerticesLastRendered();

    /**
     * Cleanup any used resources like OpenGL buffers
     */
    public void cleanup();
}
