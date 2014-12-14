package com.radirius.mercury.exceptions;

/**
 * A general exception for Mercury.
 *
 * @author wessles
 * @author Jeviny
 */
public class MercuryException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new exception with a given reason.
	 *
	 * @param reason The reason for this exception.
	 */
	public MercuryException(String reason) {
		super(reason);
	}

	/**
	 * Create a new exception with a given reason.
	 *
	 * @param reason The reason for this exception.
	 * @param e The exception causing this exception to be thrown.
	 */
	public MercuryException(String reason, Throwable e) {
		super(reason, e);
	}
}
