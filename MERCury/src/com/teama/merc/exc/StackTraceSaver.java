package com.teama.merc.exc;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @from merc in com.teama.merc.exc
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 7, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class StackTraceSaver
{
    
    public static void save(Exception e)
    {
        save(getDate() + ".stacktrace", e);
    }
    
    public static void save(String location, Exception e)
    {
        PrintWriter pw = null;
        
        try
        {
            pw = new PrintWriter(new File(location));
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        
        e.printStackTrace(pw);
        
        pw.close();
        
        e.printStackTrace();
    }
    
    private static String getDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm-ss");
        Calendar cal = Calendar.getInstance();
        
        return dateFormat.format(cal.getTime());
    }
    
}
