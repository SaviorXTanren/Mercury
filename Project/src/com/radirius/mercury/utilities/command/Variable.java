package com.radirius.mercury.utilities.command;

/**
 * A Variable that can be referenced within the given
 * inputstream (the console by default).
 *
 * @author wessles
 */
public abstract class Variable {
	/**
	 * The name of the variable. This will be
	 * case-insensitive.
	 */
	public final String name;
	/**
	 * The manual that will be shown when requested by the
	 * console user. Used for general instruction of using
	 * the variable.
	 */
	public final String manual;

	/**
	 * @param name
	 *            The name of the variable. This will be
	 *            case-insensitive.
	 * @param manual
	 *            The manual that will be shown when
	 *            requested by the console user. Used for
	 *            general instruction of using the variable.
	 */
	public Variable(String name, String manual) {
		this.name = name.toLowerCase();
		this.manual = manual;
	}

	/**
	 * @param name
	 *            The name of the variable. This will be
	 *            case-insensitive.
	 */
	public Variable(String name) {
		this(name, "Command List Developer did not provide a manual for Variable.");
	}

	/**
	 * @param args
	 *            The given arguments of the variable. Use
	 *            this info to return info!
	 * @return The value of the variable given args
	 */
	public abstract String get(String... args);
}
