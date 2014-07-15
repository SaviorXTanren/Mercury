package radirius.merc.exceptions;

/**
 * An exception to be thrown for exceptions involving the task timing.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class TaskException extends Exception {
    public TaskException(String msg) {
        super(msg);
    }
}
