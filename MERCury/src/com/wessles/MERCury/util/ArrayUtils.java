package com.wessles.MERCury.util;

import java.util.ArrayList;

import com.wessles.MERCury.geo.Vector2f;

/**
 * A utilities class for arrays.
 * 
 * @from MERCury in com.wessles.MERCury.utils
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class ArrayUtils
{
    public static Vector2f[] getVector2fs(float... coords)
    {
        if (coords.length % 2 != 0)
            throw new IllegalArgumentException("Vertex coords must be even!");
        
        Vector2f[] vectors = new Vector2f[coords.length / 2];
        
        for (int v = 0; v < coords.length; v += 2)
            vectors[v / 2] = new Vector2f(coords[v], coords[v + 1]);
        
        return vectors;
    }
    
    public static ArrayList<Vector2f> getListFromVectors(Vector2f[] vecs)
    {
        ArrayList<Vector2f> result = new ArrayList<Vector2f>();
        for (Vector2f v : vecs)
            result.add(v);
        return result;
    }
}
