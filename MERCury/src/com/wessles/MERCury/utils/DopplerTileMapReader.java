package com.wessles.MERCury.utils;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * A utility class for reading tile maps from Doppler Indie Games. Credit to
 * Doppler from {@link https://github.com/doppl3r}.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class DopplerTileMapReader {
	public static int[][] getTiles(int readwidth, int readheight, BufferedInputStream bis) {
		Scanner scan = new Scanner(bis);
		int[][] tiles = new int[readwidth][readheight];

		int x = 0, y = 0;
		while (scan.hasNextLine()) {
			while (scan.hasNextInt()) {
				tiles[x][y] = scan.nextInt();
				x++;
			}
			y++;
		}

		scan.close();
		return null;
	}
}
