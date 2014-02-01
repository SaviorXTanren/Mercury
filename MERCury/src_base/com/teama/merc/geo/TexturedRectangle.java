package com.teama.merc.geo;

import com.teama.merc.gfx.Texture;
import com.teama.merc.gfx.Textured;

/**
 * A textured version of a rectangle.
 * 
 * @from merc in com.teama.merc.geo
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class TexturedRectangle extends Rectangle implements Textured
{
    private Texture texture;
    
    public TexturedRectangle(Rectangle rect, Texture texture)
    {
        super(rect.vertices[0].x, rect.vertices[0].y, rect.vertices[1].x, rect.vertices[1].y, rect.vertices[2].x, rect.vertices[2].y, rect.vertices[3].x, rect.vertices[3].y);
        this.texture = texture;
    }
    
    @Override
    public Texture getTexture()
    {
        return texture;
    }
}
