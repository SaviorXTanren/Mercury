package com.wessles.MERCury.utils;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Random {
	private static java.util.Random rand = new java.util.Random();

	public static boolean nextBoolean() {
		refreshSeed();
		return rand.nextBoolean();
	}

	public static int nextInt() {
		refreshSeed();
		return rand.nextInt();
	}

	public static long nextLong() {
		refreshSeed();
		return rand.nextLong();
	}

	public static float nextFloat() {
		refreshSeed();
		return rand.nextFloat();
	}

	public static double nextDouble() {
		refreshSeed();
		return rand.nextDouble();
	}

	public static double nextGaussian() {
		refreshSeed();
		return rand.nextGaussian();
	}

	private static void refreshSeed() {
		rand.setSeed((long) (System.currentTimeMillis() / 1000 + System.currentTimeMillis() * 7.4));
	}
}
