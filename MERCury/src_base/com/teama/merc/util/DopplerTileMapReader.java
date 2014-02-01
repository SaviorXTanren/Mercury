package com.teama.merc.util;

import java.io.InputStream;
import java.util.Scanner;

/**
 * A utility class for reading tile maps from Doppler Indie Games. Credit to Doppler from {@link https://github.com/doppl3r}.
 * 
 * @from merc in com.teama.merc.util
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class DopplerTileMapReader
{
    public static final int DIGITS_PER_TILE = 4;
    
    public static int[][] getTiles(InputStream in)
    {
        
        // Read WIDTH and HEIGHT
        int[] dimensions = readDimensions(in);
        int WIDTH = dimensions[0], HEIGHT = dimensions[1];
        
        // Read data
        int[][] result = readData(WIDTH, HEIGHT, in);
        
        return result;
    }
    
    private static int[] readDimensions(InputStream in)
    {
        int WIDTH = 0, HEIGHT = 0;
        
        Scanner read = new Scanner(in);
        
        while (read.hasNextLine())
        {
            String line = read.nextLine();
            if (line.startsWith("texture"))
                break;
            
            WIDTH = line.length() / DIGITS_PER_TILE;
            HEIGHT++;
        }
        
        read.close();
        
        return new int[]
        {
                WIDTH, HEIGHT
        };
    }
    
    private static int[][] readData(int WIDTH, int HEIGHT, InputStream in)
    {
        int[][] data = new int[WIDTH][HEIGHT];
        
        Scanner read = new Scanner(in);
        
        int y = 0;
        while (read.hasNextLine())
        {
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
}
