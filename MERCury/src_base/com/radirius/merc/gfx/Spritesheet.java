package com.radirius.merc.gfx;

import java.io.InputStream;
import java.util.HashMap;

import com.radirius.merc.data.TextFile;

/**
 * @from MERCury_git in com.radirius.merc.gfx
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Mar 27, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class Spritesheet
{

    private HashMap<String, SubTexture> textures = new HashMap<String, SubTexture>();
    private Texture texture;
    private int numTextures;

    public Spritesheet(TextFile file, InputStream textureIn)
    {
        parseSheet(file, textureIn);
    }

    private void parseSheet(TextFile file, InputStream textureIn)
    {
        texture = Texture.loadTexture(textureIn);
        String tempData = file.getContent();

        String[] data = tempData.split("\\r?\\n");
        for (int i = 0; i < data.length; i++)
        {
            String[] lineData = data[i].split(" ");
            if (i == 0)
                numTextures = Integer.valueOf(lineData[1]);
            else
                textures.put(lineData[0], new SubTexture(this, Float.valueOf(lineData[1]), Float.valueOf(lineData[2]), Float.valueOf(lineData[3])));
        }
    }

    public int getNumTextures()
    {
        return numTextures;
    }

    public Texture getSheetTexture()
    {
        return texture;
    }

    public SubTexture getTexture(String value)
    {
        return textures.get(value);
    }

    public static Spritesheet loadSheet(InputStream in, InputStream textureIn)
    {
        return new Spritesheet(TextFile.loadFile(in), textureIn);
    }
}
