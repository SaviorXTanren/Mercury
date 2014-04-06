package com.radirius.merc.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @from MERCury_git in com.radirius.merc.data
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Mar 27, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class TextFile
{
    private String lines;

    public TextFile(String lines)
    {
        this.lines = lines;
    }

    public static TextFile loadFile(InputStream in)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder lines = new StringBuilder();
        String currentLine;
        try
        {
            while ((currentLine = reader.readLine()) != null)
                lines.append(currentLine).append("\n");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new TextFile(lines.toString());
    }

    public String getContent()
    {
        return lines;
    }
}
