package com.teama.merc.geo;

/**
 * A base class for all vectors.
 * 
 * @from MERCury in com.teama.merc.geo
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public abstract class Vec
{
    public abstract void add(Vec vec);
    
    public abstract void sub(Vec vec);
    
    public abstract void set(Vec vec);
    
    public abstract void set(float... coord);
    
    public abstract void scale(float a);
    
    public abstract void negate();
    
    public abstract float length();
    
    public abstract void normalize();
    
    public abstract float dot(Vec vec);
    
    public abstract float distance(Vec vec);
    
    @Override
    public abstract String toString();
}
