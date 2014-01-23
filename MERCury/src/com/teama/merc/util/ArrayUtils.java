package com.teama.merc.util;

import java.util.ArrayList;

import com.teama.merc.geo.Vec2;

/**
 * A utilities class for arrays.
 * 
 * @from merc in com.teama.merc.util
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class ArrayUtils
{
    public static Vec2[] getVector2fs(float... coords)
    {
        if (coords.length % 2 != 0)
            throw new IllegalArgumentException("Vertex coords must be even!");
        
        Vec2[] vectors = new Vec2[coords.length / 2];
        
        for (int v = 0; v < coords.length; v += 2)
            vectors[v / 2] = new Vec2(coords[v], coords[v + 1]);
        
        return vectors;
    }
    
    public static ArrayList<Vec2> getListFromVectors(Vec2[] vecs)
    {
        ArrayList<Vec2> result = new ArrayList<Vec2>();
        for (Vec2 v : vecs)
            result.add(v);
        return result;
    }
}
