package com.wessles.MERCury.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * A utility class for reading tile maps from Doppler Indie Games. Credit to Doppler from {@link https://github.com/doppl3r}.
 * 
 * @from MERCury in com.wessles.MERCury.utils
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class DopplerTileMapReader {
  public static final int DIGITS_PER_TILE = 4;
  
  public static int[][] getTiles(String readlocation) throws CorruptMapException, IOException {
    
    // Read WIDTH and HEIGHT
    int[] dimensions = readDimensions(readlocation);
    int WIDTH = dimensions[0], HEIGHT = dimensions[1];
    
    // Read data
    int[][] result = readData(WIDTH, HEIGHT, readlocation);
    
    return result;
  }
  
  private static int[] readDimensions(String readlocation) throws FileNotFoundException {
    int WIDTH = 0, HEIGHT = 0;
    
    Scanner read = new Scanner(new FileInputStream(readlocation));
    
    while (read.hasNextLine()) {
      String line = read.nextLine();
      if (line.startsWith("texture"))
        break;
      
      WIDTH = line.length() / DIGITS_PER_TILE;
      HEIGHT++;
    }
    
    read.close();
    
    return new int[] { WIDTH, HEIGHT };
  }
  
  private static int[][] readData(int WIDTH, int HEIGHT, String readlocation) throws FileNotFoundException {
    int[][] data = new int[WIDTH][HEIGHT];
    
    Scanner read = new Scanner(new FileInputStream(readlocation));
    
    int y = 0;
    while (read.hasNextLine()) {
      String line = read.nextLine();
      if (line.startsWith("texture"))
        break;
      
      for (int x = 0; x < WIDTH * DIGITS_PER_TILE; x += DIGITS_PER_TILE)
        data[x / DIGITS_PER_TILE][y] = Integer.valueOf(line.substring(x, x + DIGITS_PER_TILE));
      
      y++;
    }
    
    read.close();
    
    return data;
  }
  
  @SuppressWarnings("serial")
  public static class CorruptMapException extends Exception {
    public CorruptMapException(String message) {
      super(message);
    }
  }
}
