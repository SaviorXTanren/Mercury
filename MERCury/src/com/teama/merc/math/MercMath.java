package com.teama.merc.math;

/**
 * A utilities class for math.
 * 
 * @from merc in com.teama.merc.math
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class MercMath
{
    public static final float PI = 3.141592653589793f;
    
    /**
     * Base method for random number methods.
     * 
     * @return a random double value between {@code minimum} and {@code maximum} .
     */
    public static final double random(double minimum, double maximum)
    {
        return (float) minimum + (int) (Math.random() * (maximum - minimum + 1));
    }
    
    /**
     * @return A random boolean. 50-50 chance of true or false.
     */
    public static boolean nextBoolean()
    {
        return (int) random(0, 20) % 2 == 0;
    }
    
    /**
     * @param percent
     *            Percent chance of true
     * @return A boolean that has a {@code percent}% chance of being true.
     */
    public static boolean chance(int percent)
    {
        if (percent > 100)
            percent %= 100;
        
        if (random(0, 100) < percent)
            return true;
        return false;
    }
    
    /**
     * @param percent
     *            Percent chance of true
     * @return A boolean that has a {@code percent}% chance of being true.
     */
    public static boolean chance(float percent)
    {
        if (percent > 1)
            percent %= 1;
        
        if (random(0, 1000) < percent * 1000)
            return true;
        return false;
    }
    
    /**
     * @return An integer value between {@code Integer.MIN_VALUE} and {@code Integer.MAX_VALUE}.
     */
    public static int nextInt()
    {
        return (int) random(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * @return Either 1, or -1.
     */
    public static int negpos()
    {
        return nextBoolean() ? 1 : -1;
    }
    
    /**
     * Negates a value {@code x} by 50% chance.
     */
    public static void negpos(double x)
    {
        x *= negpos();
    }
    
    /**
     * @return A floating point value between 0.0 and 1.0
     */
    public static float nextFloat()
    {
        return (float) random(0, 100) / 100;
    }
    
    /**
     * @return A double value between {@code Double.MIN_VALUE} and {@code Double.MAX_VALUE}.
     */
    public static double nextDouble()
    {
        return random(Double.MIN_VALUE, Double.MAX_VALUE);
    }
    
    /**
     * @return The sine of {@code angle}.
     */
    public static float sin(float angle)
    {
        return (float) Math.sin(Math.toRadians(angle));
    }
    
    /**
     * @return The cosine of {@code angle}.
     */
    public static float cos(float angle)
    {
        return (float) Math.cos(Math.toRadians(angle));
    }
    
    /**
     * @return The a-tangeant of {@code x}, and {@code y}.
     */
    public static float atan2(float x, float y)
    {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }
    
    /**
     * 
     * @return The equivalent of {@code angle} in radians.
     */
    public static double toRadians(double angle)
    {
    	return angle * (Math.PI / 180);
    }
    
    /**
     * 
     * @return The equivalent of {@code angle} in degrees.
     */
    public static double toDegrees(double angle)
    {
    	return angle * (180 / Math.PI);
    }
    
    /**
     * @return If number is negative, -1, otherwise, 1.
     */
    public static float negpos(float mult)
    {
        if (mult == 0)
            return 0;
        return mult / Math.abs(mult);
    }
}
