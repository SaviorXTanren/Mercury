package radirius.merc.utilities.command;

/**
 * A Command that will do a specific task when asked in the given InputStream
 * (by default, the console).
 *
 * @author wessles
 */
public abstract class Command {
	/** The name of the command. This will be case-insensitive. */
	public final String name;
	/**
	 * The manual that will be shown when requested by the console user. Used
	 * for general instruction of using the command.
	 */
	public final String manual;

	/**
	 * @param name
	 *            The name of the command. This will be case-insensitive.
	 * @param manual
	 *            The manual that will be shown when requested by the console
	 *            user. Used for general instruction of using the command.
	 */
	public Command(String name, String manual) {
		this.name = name.toLowerCase();
		this.manual = manual;
	}

	/**
	 * @param name
	 *            The name of the command. This will be case-insensitive.
	 */
	public Command(String name) {
		this(name, "Command List Developer did not provide a manual for Command.");
	}

	/**
	 * The action to do when the command is run given args.
	 *
	 * @param args
	 *            The arguments given for the command. You do not need to use
	 *            these.
	 */
	public abstract void run(String... args);
}
