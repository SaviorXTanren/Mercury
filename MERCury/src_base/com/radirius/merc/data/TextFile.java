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
	private File file;
	private boolean updated;

	public TextFile(File file, String lines)
	{
		this.file = file;
		this.lines = lines;
		this.updated = true;
	}

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

	public void write(String data) 
	{
		write(data, true);
	}
	
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

	public String getContent()
	{
		if(!updated)
		{
			this.lines = TextFile.read(file);
			updated = true;
		}
		return lines;
	}
}
