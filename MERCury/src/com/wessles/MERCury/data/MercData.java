package com.wessles.MERCury.data;

import java.io.File;
import java.io.IOException;

/**
 * @from MERCury in com.wessles.MERCury.data
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 5, 2014 www.wessles.com
 * This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class MercData {
  public File file;
  
  public MercData(String location) throws IOException {
    if(!location.contains("."))
      location += ".MERC.dat";
    
    File _file = new File(location);
    if(!_file.exists())
      try {
        _file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    
    if(_file.canWrite()) {
      
    } else {
      throw new IOException("Cannot write to file!");
    }
  }
}
