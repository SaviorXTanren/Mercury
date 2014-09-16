package radirius.merc.exceptions;

/**
 * A general exception for Mercury.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class MercuryException extends Exception {
	public MercuryException(String reason) {
		super(reason);
	}
}
