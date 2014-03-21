package com.teama.merc.gui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.teama.merc.fmwk.Runner;
import com.teama.merc.font.Font;
import com.teama.merc.font.TrueTypeFont;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;

/**
 * @from MERCury in package com.teama.merc.gui;
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Mar 3, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class TextPanel extends Component
{
    public float margin;
    
    public Texture border_hor, border_vert, background_img;
    
    public Color textCol;
    
    private boolean hasBackground = false;
    
    public String title;
    
	Font titleFont = null;
	Font txtFont = null;
    
    public TextPanel(String title, String txt, float x, float y, float w, float h, float margin, Color textCol)
    {
        this(title, txt, Texture.getEmptyTexture(), x, y, w, h, margin, textCol);
        
        hasBackground = false;
    }
    
    public TextPanel(String title, String txt, Texture background_img, float x, float y, float w, float h, float margin, Color textCol)
    {
    	super(fitStringToBounds(txt, w, margin), x, y, w, h, false, false);
    	
    	this.margin = margin;
    	this.background_img = background_img;
    	this.textCol = textCol;
    	this.title = title;
    	
    	try
    	{
    		titleFont = TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Bold.ttf", 32, 1, true);
        	txtFont = TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Semibold.ttf", 20, 1, true);
    	} 
    	catch (FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	catch (FontFormatException e)
    	{
    		e.printStackTrace();
    	} 
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    	
    	hasBackground = true;
    }
    
    public void render(Graphics g)
    {
    	if (!hasBackground)
    	{
            float borderwidth = Math.min(border_hor.getTextureHeight(), border_hor.getTextureWidth());
            
            g.drawTexture(border_vert, 0, 0, w, borderwidth, x, y+h-borderwidth+margin);
            g.drawTexture(border_hor, 0, 0, borderwidth, h+margin*2, x+w-borderwidth, y-margin);
            
            borderwidth = Math.min(border_vert.getTextureHeight(), border_vert.getTextureWidth());
            
            g.drawTexture(border_hor, 0, 0, borderwidth, h+margin*2, x, y-margin);
            g.drawTexture(border_vert, 0, 0, w, borderwidth, x, y-margin);	
    	}
    	else
    	{
        	g.drawTexture(background_img, x, y, w, h);
    	}
        
        renderContent(g);
    }
    
    public void renderContent(Graphics g)
    {

    	g.setFont(titleFont);
    	g.setColor(textCol);
        g.drawString(x + margin, y + margin, title);
        g.setColor(Color.white);
        
        g.setFont(txtFont);
    	g.setColor(textCol);
        g.drawString(x + margin, (y + titleFont.getHeight()) + margin, txt);
        g.setColor(Color.white);
    }
    
    private static String fitStringToBounds(String txt, float w, float margin)
    {
        // A line of text
        String linetxt = "";
        
        // The index character in the original string
        int idx = 0;
        int lidx = 0;
        
        // The result
        String finaltxt = "";
        
        // Begin fitting!
        while (idx < txt.length())
        {
            // Write character to line, and add to index
            linetxt += txt.charAt(idx);
            idx++;
            
            // If we are pushing the width limit or we are at the end of the text, new line (or not)!
            if (Runner.getInstance().getGraphics().getFont().getWidth(txt.substring(lidx, idx).toCharArray()) >= w - margin*2 || idx >= txt.length())
            {
                // Making sure no words get cut off, or split in half.
                int lastspace = linetxt.lastIndexOf(' ');
                
                if (lastspace > 0)
                {
                    idx -= linetxt.length() - lastspace;
                    linetxt = linetxt.substring(0, lastspace);
                }

                // Trim whitespace
                linetxt = linetxt.trim();
                
                // Add line to text, and reset variables
                finaltxt += linetxt;
                finaltxt += "\n";
                linetxt = "";
                lidx = idx;
            }
        }
        
        return finaltxt;
    }
}
