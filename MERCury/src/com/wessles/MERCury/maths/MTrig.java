package com.wessles.MERCury.maths;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class MTrig {
	public static float sin(float angle) {
		return (float) Math.sin(Math.toRadians(angle));
	}

	public static float cos(float angle) {
		return (float) Math.cos(Math.toRadians(angle));
	}

	public static float atan2(float x, float y) {
		return (float) Math.toDegrees(Math.atan2(y, x));
	}
}
