package com.radirius.mercury.utilities.logging;

import java.io.PrintStream;

/**
 * A utility for shortening logging. This logger takes in objects to log, as opposed to a single string.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 */
public class Logger {
	/**
	 * Whether or not information can be logged
	 */
	private static boolean logging = true;

	/**
	 * Defines whether or not information can be logged.
	 */
	public static void setLogging(boolean logging) {
		Logger.logging = logging;
	}

	/**
	 * Logs a message.
	 */
	public static void log(boolean warn, Object... object) {
		if (!logging)
			return;

		String message = "";

		for (Object obj : object)
			message += obj.toString() + " ";

		PrintStream printStream = warn ? System.err : System.out;
		printStream.println(message + "");
	}

	/**
	 * Logs a message.
	 */
	public static void log(Object... object) {
		log(false, object);
	}

	/**
	 * Prints a new line.
	 */
	public static void newLine() {
		System.out.println();
	}
}
