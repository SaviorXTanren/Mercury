package radirius.merc.data;

/**
 * An interface for an object that can open and close. This should be used for
 * file reading and such.
 * 
 * @author wessles
 */

public interface Data {
    /** Open, and load data. */
    public void open();
    
    /** Close, and save data. */
    public void close();
}
