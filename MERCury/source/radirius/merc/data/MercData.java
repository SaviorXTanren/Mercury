package radirius.merc.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A way to store information outside of local data and store data in files.
 * Uses a system of properties and values.
 * 
 * @author wessles, Jeviny
 */

public class MercData implements Data {
  /**
   * Location of the data file.
   */
  public String location;
  
  /**
   * The values of the data file. This is temperary in-code storage.
   */
  public HashMap<String, String> vals = new HashMap<String, String>();
  
  /**
   * @param url
   *          URL indicating the location of the file.
   */
  public MercData(URL url) {
    location = url.getFile();
  }
  
  /**
   * @param prop
   *          The property you are modifying (case sensitive).
   * @param val
   *          The value that you are changing the property to.
   */
  public void setProperty(String prop, String val) {
    vals.put(prop, val);
  }
  
  /**
   * @param prop
   *          The property you want to see the value of.
   * @return The property's value. If it does not exist, you get null.
   */
  public String getProperty(String prop) {
    return vals.get(prop);
  }
  
  @Override
  public void open() {
    Scanner scan = null;
    try {
      scan = new Scanner(new FileInputStream(location));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    while (scan.hasNextLine()) {
      String ln = scan.nextLine();
      String[] split = ln.split(" ", 2);
      String prop = split[0];
      String val = split[1];
      vals.put(prop, val);
    }
    
    scan.close();
  }
  
  @Override
  public void close() {
    PrintWriter write = null;
    
    try {
      write = new PrintWriter(new FileOutputStream(new File(location)));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    for (int i = 0; i < vals.size(); i++) {
      String prop = (String) vals.keySet().toArray()[i];
      String val = (String) vals.values().toArray()[i];
      
      write.println(prop + " " + val);
    }
    
    write.close();
    
    vals.clear();
  }
}
