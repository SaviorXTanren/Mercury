package com.wessles.MERCury.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import com.wessles.MERCury.log.Logger;

/**
 * @from MERCury in com.wessles.MERCury.data
 * @by wessles kpars
 * @website www.wessles.com
 * @license (C) Jan 5, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class MercData {
	public static final int PARSER_VERSION = 1;

	public String location;

	public HashMap<String, String> vals = new HashMap<String, String>();

	public MercData(String location) {
		this.location = location;

		if (location.contains("."))
			this.location = this.location.substring(0,
					this.location.indexOf('.'))
					+ ".MERC.dat";
		else
			this.location += ".MERC.dat";

		if (new File(this.location).exists())
			load();
	}

	public void setProperty(String prop, String val)
			throws FileNotFoundException {
		vals.put(prop, val);
	}

	public String getProperty(String prop) {
		return vals.get(prop);
	}

	public void close() {
		save();
		vals.clear();
	}

	/**
	 * To be used to load all of our properties
	 */
	private void load() {
		Scanner scan = null;
		
		try {
			scan = new Scanner(new FileInputStream(location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String parser = scan.nextLine();
		parser = parser.substring(parser.indexOf(" ") + 1,
				parser.lastIndexOf(" "));
		if (Integer.parseInt(parser) != PARSER_VERSION)
			Logger.print("Error: Parser Version From File Does Not Match The Current Parser Version.");
		
		while (scan.hasNext()) {
			String prop = scan.next();
			String val = scan.next();
			System.out.println(prop + " " + val);
			vals.put(prop, val);
		}

		scan.close();
	}

	/**
	 * To be used to SAVE your changes!
	 */
	private void save() {
		PrintWriter write = null;

		try {
			write = new PrintWriter(new FileOutputStream(new File(location)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		write.println("<! " + PARSER_VERSION + " >");

		for (int i = 0; i < vals.size(); i++) {
			String prop = (String) vals.keySet().toArray()[i];
			String val = (String) vals.values().toArray()[i];

			write.println(prop + " " + val);
		}

		write.close();
	}
}
