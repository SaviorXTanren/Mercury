package com.wessles.MERCury.utils;

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
