package com.teama.merc.gfx;

/**
 * An abstraction for batchers.
 * 
 * @from merc in com.teama.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public interface Batcher
{
    
    public void begin();
    
    public void end();
    
    public void render();
   
    public void render(boolean hasColor, boolean hasTexture);
    
    public void setColor(Color color);
    
    public void clearColors();
    
    public void setShader(Shader shader);
    
    public void clearShaders();
    
    public void setTexture(Texture texture);
    
    public void clearTextures();
    
    public void vertex(float x, float y, float u, float v);
    
    public void vertex(float x, float y, Color color, float u, float v);
    
    public void vertex(float x, float y, float r, float g, float b, float u, float v);
    
    public void vertex(float x, float y, float r, float g, float b, float a, float u, float v);
}
