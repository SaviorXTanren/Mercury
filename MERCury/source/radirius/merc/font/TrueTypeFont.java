package radirius.merc.font;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import radirius.merc.graphics.Texture;
import radirius.merc.logging.Logger;
import radirius.merc.resource.Loader;

/**
 * A font type for .ttf's
 * 
 * @author wessles
 */

public class TrueTypeFont implements radirius.merc.font.Font {
    public static int STANDARD_CHARACTERS = 256;
    
    /** A default opensans bold font! */
    public static TrueTypeFont OPENSANS_BOLD;
    /** A default opensans regular font! */
    public static TrueTypeFont OPENSANS_REGULAR;
    /** A default opensans almost-bold font! */
    public static TrueTypeFont OPENSANS_SEMIBOLD;
    
    static {
        try {
            OPENSANS_BOLD = TrueTypeFont.loadTrueTypeFont(
                    Loader.streamFromClasspath("radirius/merc/graphics/OpenSans-Semibold.ttf"), 22f, 1, true);
            OPENSANS_REGULAR = TrueTypeFont.loadTrueTypeFont(
                    Loader.streamFromClasspath("radirius/merc/graphics/OpenSans-Semibold.ttf"), 22f, 1, true);
            OPENSANS_SEMIBOLD = TrueTypeFont.loadTrueTypeFont(
                    Loader.streamFromClasspath("radirius/merc/graphics/OpenSans-Semibold.ttf"), 22f, 1, true);
        } catch (IOException e) {
            Logger.warn("Problems loading default opensans fonts.");
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    /** All data for all characters. */
    public final IntObject[] chars = new IntObject[STANDARD_CHARACTERS];
    
    /** Shall we antialias? */
    private boolean antialias;
    
    /** The size of the font */
    private float font_size = 0;
    /** The height of the font */
    private float font_height = 0;
    /** The maximum number/letter character width */
    private float font_max_width = 0;
    /** The average number/letter character width */
    private float font_average_width = 0;
    
    /** The overall texture used for rendering the font. */
    private Texture font_tex;
    
    private int texw = 0;
    private int texh = 0;
    
    /** Some awt jargon for fonts. */
    private java.awt.Font font;
    /** Some awt jargon for fonts. */
    private FontMetrics fmetrics;
    
    private TrueTypeFont(java.awt.Font font, boolean antialias) {
        this.font = font;
        font_size = font.getSize();
        this.antialias = antialias;
        
        createSet();
    }
    
    private void createSet() {
        for (int i = 0; i < STANDARD_CHARACTERS; i++) {
            char ch = (char) i;
            BufferedImage fontimg = getFontImage(ch);
            texw += fontimg.getWidth();
            texh = Math.max(fontimg.getHeight(), texh);
        }
        
        texw /= 8;
        texh *= 8;
        
        // Make a graphics object for the buffered image.
        BufferedImage imgTemp = new BufferedImage(texw, texh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) imgTemp.getGraphics();
        
        // Set the color to transparent
        g.setColor(new java.awt.Color(255, 255, 255, 1));
        g.fillRect(0, 0, texw, texh);
        
        // Initialize temporary vars
        float positionX = 0;
        float positionY = 0;
        
        // Loop through all standard characters (256 of em')
        for (int i = 0; i < STANDARD_CHARACTERS; i++) {
            char ch = (char) i;
            
            // BufferedImage for the character
            BufferedImage fontImage = getFontImage(ch);
            
            // New IntObject with width and height of fontImage.
            IntObject newIntObject = new IntObject();
            newIntObject.w = fontImage.getWidth();
            newIntObject.h = fontImage.getHeight();
            
            // Go to next row if there is no room on x axis.
            if (positionX + newIntObject.w >= texw) {
                positionX = 0;
                positionY += getHeight();
            }
            
            // Set the positions
            newIntObject.x = positionX;
            newIntObject.y = positionY;
            
            // Draw the character onto the font image.
            g.drawImage(fontImage, (int) positionX, (int) positionY, null);
            
            // Next position on x axis.
            positionX += newIntObject.w;
            
            // Set the IntObject of the character
            chars[i] = newIntObject;
            
            fontImage = null;
        }
        
        // Turn black, for coloring reasons.
        for (int x = 0; x < imgTemp.getWidth(); x++) {
            for (int y = 0; y < imgTemp.getHeight(); y++) {
                int rgba = imgTemp.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue(), col.getAlpha());
                imgTemp.setRGB(x, y, col.getRGB());
            }
        }
        
        // Load texture!
        font_tex = Texture.loadTexture(imgTemp, false, false);
    }
    
    private BufferedImage getFontImage(char ch) {
        // Make and init graphics for character image.
        BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
        
        if (antialias)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setFont(font);
        
        // Font preperation
        fmetrics = g.getFontMetrics();
        
        float charwidth = fmetrics.charWidth(ch);
        // Safety guards just in case.
        if (charwidth <= 0)
            charwidth = 1;
        
        if (Character.isLetterOrDigit(ch)) {
            font_max_width = Math.max(font_max_width, charwidth);
            font_average_width += charwidth / STANDARD_CHARACTERS;
        }
        
        // Height!
        font_height = fmetrics.getHeight();
        
        // Now to the actual image!
        BufferedImage fontImage = new BufferedImage((int) charwidth, (int) getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gt = (Graphics2D) fontImage.getGraphics();
        if (antialias == true)
            gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gt.setFont(font);
        
        // Set the text color to white, set x and y, and return to font
        // image!
        gt.setColor(java.awt.Color.WHITE);
        gt.drawString(String.valueOf(ch), 0, fmetrics.getAscent());
        
        return fontImage;
    }
    
    @Override
    public float getWidth(String what) {
        float totalwidth = 0;
        IntObject intObject = null;
        int currentChar = 0;
        for (char element : what.toCharArray()) {
            currentChar = element;
            if (currentChar < STANDARD_CHARACTERS)
                intObject = chars[currentChar];
            
            if (intObject != null)
                totalwidth += intObject.w;
        }
        return totalwidth;
    }
    
    @Override
    public float getMaxWidth(int len) {
        return len * font_max_width;
    }
    
    @Override
    public float getAverageWidth(int len) {
        return len * font_average_width;
    }
    
    @Override
    public Font deriveFont(float size) {
        return new TrueTypeFont(font.deriveFont(size), antialias);
    }
    
    @Override
    public Font deriveFont(int style) {
        return new TrueTypeFont(font.deriveFont(style), antialias);
    }
    
    @Override
    public float getSize() {
        return font_size;
    }
    
    @Override
    public float getHeight() {
        return font_height;
    }
    
    @Override
    public Texture getFontTexture() {
        return font_tex;
    }
    
    /** An object type for storing data for each character. */
    public static class IntObject {
        public float w;
        public float h;
        public float x;
        public float y;
    }
    
    @Override
    public void clean() {
        font_tex.clean();
    }
    
    /**
     * Let's load a font!
     * 
     * @param is
     *            The stream for the font.
     * @param size
     *            The size of the font.
     * @param style
     *            The style of the font.
     * @param antialias
     *            Shall we antialias?
     */
    public static TrueTypeFont loadTrueTypeFont(InputStream is, float size, int style, boolean antialias)
            throws FileNotFoundException, FontFormatException, IOException {
        java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        
        font = font.deriveFont(size);
        
        return loadTrueTypeFont(font, antialias);
    }
    
    /**
     * Let's load a font!
     * 
     * @param font
     *            The base awt font.
     * @param antialias
     *            Shall we antialias?
     */
    
    public static TrueTypeFont loadTrueTypeFont(java.awt.Font font, boolean antialias) {
        return new TrueTypeFont(font, antialias);
    }
}