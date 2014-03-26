package com.radirius.merc.gui;

import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;

/**
 * @from merc in package com.radirius.merc.gui;
 * @authors Jeviny
 * @website www.wessles.com
 * @license (C) Mar 3, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class TextBox extends Component
{
    public float margin;
    
    public Texture border_hor, border_vert, background_img;
    
    public Color textCol;
    
    private boolean hasBackground = false;
    
    public TextBox(String txt, float x, float y, float w, float h, float margin, Color textCol)
    {
        this(txt, Texture.getEmptyTexture(), Texture.getEmptyTexture(), x, y, w, h, margin, textCol);
        
        hasBackground = false;
    }
    
    public TextBox(String txt, Texture border_hor, Texture border_vert, float x, float y, float w, float h, float margin, Color textCol)
    {
        super(fitStringToBounds(txt, w, margin), x, y, w, h, false, false);
        
        this.margin = margin;
        this.border_hor = border_hor;
        this.border_vert = border_vert;
        this.textCol = textCol;
        
        hasBackground = false;
    }
    
    public TextBox(String txt, Texture background_img, float x, float y, float w, float h, float margin, Color textCol)
    {
    	super(fitStringToBounds(txt, w, margin), x, y, w, h, false, false);
    	
    	this.margin = margin;
    	this.background_img = background_img;
    	this.textCol = textCol;
    	
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
    	g.setColor(textCol);
        g.drawString(x + margin, y + margin, txt);
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
