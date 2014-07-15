package radirius.merc.exceptions;

/**
 * A general exception for MERCury, which will print its stacktrace to a file
 * using StackTraceSaver.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class MERCuryException extends Exception {
    
    public MERCuryException(String reason) {
        super(reason);
    }
}
