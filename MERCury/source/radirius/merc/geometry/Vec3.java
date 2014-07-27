package radirius.merc.geometry;

/**
 * A class for 3 dimensional vectors.
 * 
 * @author wessles
 */

public class Vec3 extends Vec {
    public float x = 0, y = 0, z = 0;
    
    public Vec3() {
        
    }
    
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public Vec add(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        x += vec3.x;
        y += vec3.y;
        z += vec3.z;
        
        return this;
    }
    
    @Override
    public Vec sub(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        x -= vec3.x;
        y -= vec3.y;
        z -= vec3.z;
        
        return this;
    }
    
    @Override
    public Vec mul(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        x *= vec3.x;
        y *= vec3.y;
        z *= vec3.z;
        
        return this;
    }
    
    @Override
    public Vec div(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        x = vec3.x;
        y /= vec3.y;
        z /= vec3.z;
        
        return this;
    }
    
    @Override
    public Vec set(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        x = vec3.x;
        y = vec3.y;
        z = vec3.z;
        
        return this;
    }
    
    @Override
    public Vec set(float... coord) {
        set(new Vec3(x, y, z));
        
        return this;
    }
    
    @Override
    public Vec scale(float a) {
        x *= a;
        y *= a;
        z *= a;
        
        return this;
    }
    
    @Override
    public Vec negate() {
        scale(-1);
        
        return this;
    }
    
    @Override
    public float length() {
        return (float) Math.sqrt(x * x + y * y + y * y);
    }
    
    @Override
    public Vec normalize() {
        float len = length();
        x = x / len;
        y = y / len;
        z = z / len;
        
        return this;
    }
    
    @Override
    public float dot(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        return x * vec3.x + y * vec3.y;
    }
    
    public Vec cross(Vec3 vec) {
        float x_ = y * vec.z - z * vec.y;
        float y_ = z * vec.x - x * vec.z;
        float z_ = x * vec.y - y * vec.x;
        
        set(x_, y_, z_);
        
        return this;
    }
    
    @Override
    public float distance(Vec vec) {
        Vec3 vec3 = (Vec3) vec;
        return new Vec3(vec3.x - x, vec3.y - y, vec3.z - z).length();
    }
    
    @Override
    public Vec copy() {
        return new Vec3(x, y, z);
    }
    
    @Override
    public String toString() {
        return "Vec3(" + x + ", " + y + ", " + z + ")";
    }
}
