package radirius.merc.utilities.logging;

import radirius.merc.exceptions.MercuryException;

/**
 * A class that will log in different levels of impact, from NULL to SEVERE.
 * SEVERE being the program killer.
 * 
 * @author wessles, Jeviny
 */

public class Logger {
	/** Whether or not information can be logged */
	private static boolean logging = true;

	/** Cases describing the nature of the log. */
	public static enum Case {
		NULL(""),
		INFO("INFO: "),
		DEBUG("DEBUG: "),
		WARNING("WARNING: "),
		SEVERE("SEVERE: "),
		CONSOLE("CONSOLE: "),
		CONSOLEPROBLEM("CONSOLE PROBLEM: ");

		public String caseMessage;

		private Case(String caseMessage) {
			this.caseMessage = caseMessage;
		}
	}

	/** Logs a message in a case, with an optional severe message sevmsg. */
	public static void log(Case selectedCase, String severeMessage, Object... object) throws MercuryException {
		if (!logging)
			return;

		String message = "";
		
		for (Object obj : object)
			message += obj + " ";
		
		if (selectedCase != Case.SEVERE && selectedCase != Case.WARNING && selectedCase != Case.CONSOLEPROBLEM) {
			System.out.println(selectedCase.caseMessage + message);
		} else {
			System.err.println(selectedCase.caseMessage + message);
			
			if (selectedCase == Case.SEVERE)
				throw new MercuryException(severeMessage);
		}
	}

	/** Logs a message in a case. */
	public static void log(Case selectedCase, Object... object) {
		try {
			log(selectedCase, "No information given.", object);
		} catch (MercuryException e) {
			e.printStackTrace();
		}
	}

	/** Logs a message in case NULL. */
	public static void log(Object... object) {
		log(Case.NULL, object);
	}

	/** Logs a message in case INFO. */
	public static void info(Object... object) {
		log(Case.INFO, object);
	}

	/** Logs a message in case DEBUG. */
	public static void debug(Object... object) {
		log(Case.DEBUG, object);
	}

	/** Logs a message in case WARNING. */
	public static void warn(Object... object) {
		log(Case.WARNING, object);
	}

	/** Logs a message in case SEVERE. */
	public static void severe(Object... object) throws MercuryException {
		log(Case.SEVERE, object);
	}

	/** Logs a message in case CONSOLE. */
	public static void console(Object... object) {
		log(Case.CONSOLE, object);
	}

	/** Logs a message in case CONSOLEPROBLEM. */
	public static void consoleProblem(Object... object) {
		log(Case.CONSOLEPROBLEM, object);
	}

	/** Prints a new line. */
	public static void newLine() {
		System.out.println();
	}

	/** Defines whether or not information can be logged. */
	public static void setLogging(boolean logging) {
		Logger.logging = logging;
	}
}
