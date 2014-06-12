package us.radiri.merc.exc;

/**
 * An exception thrown when there is a severe log entry.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class SevereLogException extends MERCuryException {
    public SevereLogException(String sevmsg) {
        super("The log has reported SEVERE, and closed the program:\n" + sevmsg);
    }
}
