package radirius.merc.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * A utility class for reading tile maps from Doppler Indie Games. Credit to
 * Doppler from https://github.com/doppl3r.
 * 
 * @author wessles
 */

public class DopplerTileMapReader {
    public static final int DIGITS_PER_TILE = 4;
    
    /**
     * @return An integer array representing what the doppler tile map file
     *         contained. Each integer is stored result[x][y].
     */
    public static int[][] getTiles(URL in) throws IOException {
        // Read WIDTH and HEIGHT
        int[] dimensions = readDimensions(in.openStream());
        int WIDTH = dimensions[0], HEIGHT = dimensions[1];
        
        // Read data
        int[][] result = readData(WIDTH, HEIGHT, in.openStream());
        
        return result;
    }
    
    private static int[] readDimensions(InputStream in) {
        int WIDTH = 0, HEIGHT = 0;
        
        Scanner read = new Scanner(in);
        
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
    
    private static int[][] readData(int WIDTH, int HEIGHT, InputStream in) {
        int[][] data = new int[WIDTH][HEIGHT];
        
        Scanner read = new Scanner(in);
        
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
}
