package com.radirius.merc.font;

import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;

/**
 * A font type for .ttf's
 * 
 * @from merc in com.radirius.merc.fnt
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class TrueTypeFont implements com.radirius.merc.font.Font
{
    
    public final IntObject[] chars = new IntObject[256];
    
    private boolean antialias;
    
    private int font_size = 0;
    private int font_height = 0;
    
    public Texture font_tex;
    
    private int texw = 512;
    private int texh = 512;
    
    private java.awt.Font font;
    private FontMetrics fmetrics;
    
    private TrueTypeFont(java.awt.Font font, boolean antialias)
    {
        this.font = font;
        font_size = font.getSize();
        this.antialias = antialias;
        
        createSet();
    }
    
    private void createSet()
    {
        // Make a graphics object for the buffered image.
        BufferedImage imgTemp = new BufferedImage(texw, texh, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = (Graphics2D) imgTemp.getGraphics();
        
        // Set the color to transparent
        g.setColor(new java.awt.Color(255, 255, 255, 1));
        g.fillRect(0, 0, texw, texh);
        
        // Initialize temporary vars
        int rowHeight = 0;
        int positionX = 0;
        int positionY = 0;
        
        // Loop through all standard characters (256 of em')
        for (int i = 0; i < 256; i++)
        {
            char ch = (char) i;
            
            // BufferedImage for the character
            BufferedImage fontImage = getFontImage(ch);
            
            // New IntObject with width and height of fontImage.
            IntObject newIntObject = new IntObject();
            newIntObject.w = fontImage.getWidth();
            newIntObject.h = fontImage.getHeight();
            
            // Go to next row if there is no room on x axis.
            if (positionX + newIntObject.w >= texw)
            {
                positionX = 0;
                positionY += rowHeight;
                rowHeight = 0;
            }
            
            // Set the positions
            newIntObject.x = positionX;
            newIntObject.y = positionY;
            
            // Set the highest height of the font.
            if (newIntObject.h > font_height)
                font_height = newIntObject.h;
            
            // Set the row_height to the highest one in the row.
            if (newIntObject.h > rowHeight)
                rowHeight = newIntObject.h;
            
            // Draw the character onto the font image.
            g.drawImage(fontImage, positionX, positionY, null);
            
            // Next position on x axis.
            positionX += newIntObject.w;
            
            // Set the IntObject of the character
            chars[i] = newIntObject;
            
            fontImage = null;
        }
        
        // Load texture!
        font_tex = Texture.loadTexture(imgTemp);
    }
    
    private BufferedImage getFontImage(char ch)
    {
        // Make and init graphics for character image.
        BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
        
        if (antialias)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setFont(font);
        // Font preperation
        
        fmetrics = g.getFontMetrics();
        int charwidth = fmetrics.charWidth(ch);
        
        // Safety guards just in case.
        if (charwidth <= 0)
            charwidth = 1;
        int charheight = fmetrics.getHeight();
        if (charheight <= 0)
            charheight = font_size;
        
        // Now to the actual image!
        BufferedImage fontImage;
        fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gt = (Graphics2D) fontImage.getGraphics();
        if (antialias == true)
            gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gt.setFont(font);
        
        // Set the text color to white, set x and y, and return to font
        // image!
        gt.setColor(java.awt.Color.WHITE);
        int charx = 0;
        int chary = 0;
        gt.drawString(String.valueOf(ch), charx, chary + fmetrics.getAscent());
        
        return fontImage;
    }
    
    @Override
    public int getWidth(char[] what)
    {
        int totalwidth = 0;
        IntObject intObject = null;
        int currentChar = 0;
        for (char element : what)
        {
            currentChar = element;
            if (currentChar < 256)
                intObject = chars[currentChar];
            
            if (intObject != null)
                totalwidth += intObject.w;
        }
        return totalwidth;
    }
    
    @Override
    public Font deriveFont(float size)
    {
        return new TrueTypeFont(font.deriveFont(size), antialias);
    }
    
    @Override
    public Font deriveFont(int style)
    {
        return new TrueTypeFont(font.deriveFont(style), antialias);
    }
    
    @Override
    public int getHeight()
    {
        return font_height;
    }
    
    @Override
    public int getLineHeight()
    {
        return font_height;
    }
    
    @Override
    public Texture getFontTexture()
    {
        return font_tex;
    }
    
    public static class IntObject
    {
        public int w;
        public int h;
        public int x;
        public int y;
    }
    
    @Override
    public void clean()
    {
        font_tex.clean();
    }
    
    public static TrueTypeFont loadTrueTypeFont(String location, float size, int style, boolean antialias) throws FileNotFoundException, FontFormatException, IOException
    {
        java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Loader.streamFromClasspath(location));
        
        font = font.deriveFont(size);
        
        return loadTrueTypeFont(font, antialias);
    }
    
    public static TrueTypeFont loadTrueTypeFont(java.awt.Font font, boolean antialias)
    {
        return new TrueTypeFont(font, antialias);
    }
}