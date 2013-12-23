package com.wessles.MERCury.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A class that you use in place of System.out, which can also print all things to a file
 * 
 * @from MERCury in com.wessles.MERCury.log
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under GPLv2.0 license. You can find the license itself at bit.ly/1eyRQJ7.
 */

public class Logger {
  private static PrintWriter log;
  
  public static void setLog(File f) {
    try {
      log = new PrintWriter(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public static void printDateAndTime() {
    DateFormat dfd = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat dft = new SimpleDateFormat("HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    print("#DATE: ");
    println(dfd.format(cal.getTime()));
    print("#TIME: ");
    println(dft.format(cal.getTime()));
  }
  
  public static void print(Object... nums) {
    for (int n = 0; n < nums.length; n++) {
      String line = nums[n] + (n != nums.length - 1 ? ", " : "");
      System.out.print(line);
      if (log != null)
        log.print(line);
    }
  }
  
  public static void println(Object... nums) {
    print(nums);
    println();
  }
  
  public static void println() {
    printlns(1);
  }
  
  public static void printlns(int lines) {
    for (int l = 0; l < lines; l++) {
      System.out.println();
      if (log != null)
        log.println();
    }
  }
  
  public static void cleanup() {
    if (log == null)
      return;
    log.close();
  }
}
