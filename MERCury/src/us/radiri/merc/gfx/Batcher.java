package us.radiri.merc.gfx;

/**
 * An abstraction for batchers.
 * 
 * @author wessles
 */
public interface Batcher {
    
    /** Make active. */
    public void begin();
    
    /** @return Whether or not I am active. */
    public boolean isActive();
    
    /** Make inactive, and push data to OGL. */
    public void end();
    
    /** Pushes data to OGL. */
    public void flush();
    
    /**
     * @param color
     *            Color to set to.
     */
    public void setColor(Color color);
    
    /** Clears current color. */
    public void clearColors();
    
    /**
     * @param shader
     *            Shader to set to.
     */
    public void setShader(Shader shader);
    
    /** Clears current shader. */
    public void clearShaders();
    
    /**
     * @param texture
     *            Texture to set to.
     */
    public void setTexture(Texture texture);
    
    /** Clears the current texture. */
    public void clearTextures();
    
    /**
     * Adds a vertex to the stack.
     * 
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     * @param u
     *            The x texture coordinate.
     * @param v
     *            The y texture coordinate.
     */
    public void vertex(float x, float y, float u, float v);
    
    /**
     * Adds a vertex to the stack.
     * 
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     * @param Color
     *            The color of the vertex.
     * @param u
     *            The x texture coordinate.
     * @param v
     *            The y texture coordinate.
     */
    public void vertex(float x, float y, Color color, float u, float v);
    
    /**
     * Adds a vertex to the stack.
     * 
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     * @param r
     *            The red value of color of the vertex.
     * @param g
     *            The green value of color of the vertex.
     * @param b
     *            The blue value of color of the vertex.
     * @param u
     *            The x texture coordinate.
     * @param v
     *            The y texture coordinate.
     */
    public void vertex(float x, float y, float r, float g, float b, float u, float v);
    
    /**
     * Adds a vertex to the stack.
     * 
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     * @param r
     *            The red value of color of the vertex.
     * @param g
     *            The green value of color of the vertex.
     * @param b
     *            The blue value of color of the vertex.
     * @param a
     *            The alpha value of color of the vertex.
     * @param u
     *            The x texture coordinate.
     * @param v
     *            The y texture coordinate.
     */
    public void vertex(float x, float y, float r, float g, float b, float a, float u, float v);
    
    /** Will flush the data to OGL if the vertex count is higher than the limit. */
    public void flushIfOverflow();
    
    /** @return The amount of vertices rendered last render frame. */
    public int getVerticesLastRendered();
}
