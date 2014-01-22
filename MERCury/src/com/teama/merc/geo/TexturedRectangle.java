package com.teama.merc.geo;

import com.teama.merc.gfx.Texture;
import com.teama.merc.gfx.Textured;

/**
 * A textured version of a rectangle.
 * 
 * @from MERCury in com.wessles.MERCury.geom
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class TexturedRectangle extends Rectangle implements Textured
{
    private Texture texture;
    
    public TexturedRectangle(Rectangle rect, Texture texture)
    {
        super(rect.nx, rect.ny, rect.fx, rect.ny, rect.fx, rect.fy, rect.nx, rect.fy);
        this.texture = texture;
    }
    
    @Override
    public Texture getTexture()
    {
        return texture;
    }
}
