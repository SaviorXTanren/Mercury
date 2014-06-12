package us.radiri.merc.data;

/**
 * @author wessles
 */

public interface Data {
    /** Open, and load data. */
    public void open();
    
    /** Close, and save data. */
    public void close();
}
