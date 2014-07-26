package radirius.merc.data;

/**
 * @author wessles
 */

public interface Data {
  /** Open, and load data. */
  public void open();
  
  /** Close, and save data. */
  public void close();
}
