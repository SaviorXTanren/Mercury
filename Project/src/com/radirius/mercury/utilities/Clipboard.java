package com.radirius.mercury.utilities;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * A utility to fetch clipboard data.
 *
 * @author wessles
 */
public class Clipboard {
	public static String fetch() {
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
