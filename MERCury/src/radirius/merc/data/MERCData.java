package radirius.merc.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A way to store information outside of local data and store data in files.
 * Uses a system of properties and values.
 * 
 * @author wessles, Jeviny
 */

public class MERCData implements Data {
	/**
	 * Location of the data file.
	 */
	public String location;

	/**
	 * The values of the data file. This is temperary in-code storage.
	 */
	public HashMap<String, String> vals = new HashMap<String, String>();

	/**
	 * @param url
	 *            URL indicating the location of the file.
	 */
	public MERCData(URL url) {
		location = url.getFile();
	}

	/**
	 * @param prop
	 *            The property you are modifying (case sensitive).
	 * @param val
	 *            The value that you are changing the property to.
	 */
	public void setProperty(String prop, String val) {
		vals.put(prop, val);
	}

	/**
	 * @param prop
	 *            The property you want to see the value of.
	 * @return The property's value. If it does not exist, you get null.
	 */
	public String getProperty(String prop) {
		return vals.get(prop);
	}

	@Override
	public void open() {
		// Create and open a scan of the file
		Scanner scan = null;
		try {
			scan = new Scanner(new FileInputStream(location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// While there is still a line to be processed
		while (scan.hasNextLine()) {
			String ln = scan.nextLine();
			// Split the line into a key/value set (by space)
			String[] split = ln.split(" ", 2);
			// Throw IOException if there is no value for the key
			if (split.length <= 1)
				try {
					throw new IOException("Corrupted data file.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			String prop = split[0];
			String val = split[1];
			// Put the value to its key on the hashmap
			vals.put(prop, val);
		}

		// Close the scan
		scan.close();
	}

	@Override
	public void close() {
		// Create and open a writer for writing data to the file
		PrintWriter write = null;
		try {
			write = new PrintWriter(new FileOutputStream(new File(location)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Go through the set
		for (int i = 0; i < vals.size(); i++) {
			String prop = (String) vals.keySet().toArray()[i];
			String val = (String) vals.values().toArray()[i];

			// Write key/value to the file
			write.println(prop + " " + val);
		}

		// Close the writer
		write.close();

		// Clear temporary storage
		vals.clear();
	}
}
