package com.radirius.merc.exc;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A utility for saving stack traces.
 * 
 * @author wessles
 */

public class StackTraceSaver {
    
    public static void save(Exception e) {
        save(getDate() + ".stacktrace", e);
    }
    
    public static void save(String location, Exception e) {
        PrintWriter pw = null;
        
        try {
            pw = new PrintWriter(new File(location));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        e.printStackTrace(pw);
        
        pw.close();
        
        e.printStackTrace();
    }
    
    private static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm-ss");
        Calendar cal = Calendar.getInstance();
        
        return dateFormat.format(cal.getTime());
    }
    
}
