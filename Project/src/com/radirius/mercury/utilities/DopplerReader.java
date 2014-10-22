package com.radirius.mercury.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * A utility class for reading tile maps from Doppler Indie
 * Games. Credit to Doppler from https://github.com/doppl3r.
 *
 * @author wessles, Jeviny
 */
public class DopplerReader {
	public static final int DIGITS_PER_TILE = 4;

	/**
	 * @return A 2D array representing what the map file
	 *         contains. Each integer is stored
	 *         result[x][y].
	 */
	public static int[][] getTiles(URL in) throws IOException {
		int[] dimensions = readDimensions(in.openStream());
		int width = dimensions[0], height = dimensions[1];

		int[][] result = readData(width, height, in.openStream());

		return result;
	}

	private static int[] readDimensions(InputStream in) {
		int width = 0, height = 0;

		Scanner scanner = new Scanner(in);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			if (line.startsWith("texture"))
				break;

			width = line.length() / DIGITS_PER_TILE;
			height++;
		}

		scanner.close();

		return new int[] { width, height };
	}

	private static int[][] readData(int width, int height, InputStream in) {
		int[][] data = new int[width][height];
		int y = 0;

		Scanner scanner = new Scanner(in);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			if (line.startsWith("texture"))
				break;

			for (int x = 0; x < width * DIGITS_PER_TILE; x += DIGITS_PER_TILE)
				data[x / DIGITS_PER_TILE][y] = Integer.valueOf(line.substring(x, x + DIGITS_PER_TILE));

			y++;
		}

		scanner.close();

		return data;
	}
}
