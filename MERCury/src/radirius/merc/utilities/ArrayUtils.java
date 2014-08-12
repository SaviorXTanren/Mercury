package radirius.merc.utilities;

import java.util.ArrayList;

import radirius.merc.math.geometry.Vec2;

/**
 * A utilities class for arrays.
 * 
 * @author wessles
 */
public class ArrayUtils {
    public static Vec2[] getVector2fs(float... coords) {
        if (coords.length % 2 != 0)
            throw new IllegalArgumentException("Vertex coords must be even!");
        
        Vec2[] vectors = new Vec2[coords.length / 2];
        
        for (int v = 0; v < coords.length; v += 2)
            vectors[v / 2] = new Vec2(coords[v], coords[v + 1]);
        
        return vectors;
    }
    
    public static ArrayList<Vec2> getListFromVectors(Vec2[] vecs) {
        ArrayList<Vec2> result = new ArrayList<Vec2>();
        for (Vec2 v : vecs)
            result.add(v);
        return result;
    }
}
