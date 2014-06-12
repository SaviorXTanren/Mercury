package us.radiri.merc.exc;

/**
 * An exception to be thrown when something in the dev console goes wrong.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class ConsoleException extends Exception {
    public ConsoleException(String msg) {
        super(msg);
    }
}
