package com.radirius.merc.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import com.radirius.merc.exc.MERCuryException;

/**
 * An easy way to edit text files.
 * 
 * @from MERCury_git in com.radirius.merc.data
 * @by opiop65 (commented by wessles)
 * @website www.wessles.com
 * @license (C) Mar 27, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class TextFile
{
    /**
     * The lines of the file.
     */
    private String lines;

    /**
     * The file that we are modifying/reading.
     */
    private File file;

    /**
     * Whether or not the file is updated.
     */
    private boolean updated;

    /**
     * @param file
     *            The file that we are modifying/reading.
     * @param lines
     *            The lines of the file.
     */
    public TextFile(File file, String lines)
    {
        this.file = file;
        this.lines = lines;
        this.updated = true;
    }

    /**
     * Loads a file given url.
     * 
     * @param url
     *            The URL indicating the location of the file.
     */
    public static TextFile loadFile(URL url)
    {
        File file = new File(url.getFile());
        if (!file.exists())
        {
            try
            {
                throw new MERCuryException("File not found: " + url.getFile());
            } catch (MERCuryException e)
            {
                e.printStackTrace();
            }
        }
        return new TextFile(file, read(file));
    }

    /**
     * Reads a file.
     * 
     * @param file
     *            The file
     */
    public static String read(File file)
    {
        StringBuilder lines = new StringBuilder();
        String line;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null)
            {
                lines.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return lines.toString();
    }

    /**
     * Writes data to the file, appending.
     * 
     * @param data
     *            The data to write
     */
    public void write(String data)
    {
        write(data, true);
    }

    /**
     * Writes data to the file, appending depending on append.
     * 
     * @param data
     *            The data to write
     * @param append
     *            Whether or not to append
     */
    public void write(String data, boolean append)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.newLine();
            writer.append(data);
            writer.close();
            updated = false;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return The content of the updated file.
     */
    public String getContent()
    {
        if (!updated)
        {
            this.lines = TextFile.read(file);
            updated = true;
        }
        return lines;
    }
}