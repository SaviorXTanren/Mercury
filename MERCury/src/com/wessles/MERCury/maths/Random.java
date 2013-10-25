package com.wessles.MERCury.maths;

/**
 * A utilities class for getting a random value.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Random {

	public static boolean nextBoolean() {
		return ((int) getRandom(0, 20)) % 2 == 0;
	}
	
	public static boolean chance(int percent) {
		if(percent > 100)
			percent %= 100;
				
		if(getRandom(0, 100) < percent)
			return true;
		return false;
	}
	
	public static boolean chance(float percent) {
		if(percent > 1)
			percent %= 1;
		
		if(getRandom(0, 1000) < percent*1000)
			return true;
		return false;
	}

	public static int nextInt() {
		return (int) getRandom(0, 1000000000);
	}

	public static float nextFloat() {
		return getRandom(0, 1000000000);
	}

	public static final float getRandom(float minimum, float maximum) {
		return (float) (Math.random() * (maximum - minimum)) + minimum;
	}
}
