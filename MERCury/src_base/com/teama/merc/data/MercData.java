package com.teama.merc.data;

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
 * 
 * @from merc in com.teama.merc.dat
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Jan 5, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class MercData
{
    public static final int PARSER_VERSION = 1;
    
    public String location;
    
    public HashMap<String, String> vals = new HashMap<String, String>();
    
    public MercData(URL url)
    {
        location = url.getFile();
        
        if (location.contains("."))
            location = location.substring(0, location.indexOf('.')) + ".MERC.dat";
        else
            location += ".MERC.dat";
        
        if (new File(location).exists())
            load();
    }
    
    public void setProperty(String prop, String val) throws FileNotFoundException
    {
        vals.put(prop, val);
    }
    
    public String getProperty(String prop)
    {
        return vals.get(prop);
    }
    
    public void close()
    {
        save();
        vals.clear();
    }
    
    /**
     * To be used to load all of our properties
     */
    private void load()
    {
        Scanner scan = null;
        try
        {
            scan = new Scanner(new FileInputStream(location));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        String parser = scan.nextLine();
        parser = parser.substring(parser.indexOf(" ") + 1, parser.lastIndexOf(" "));
        if (Integer.parseInt(parser) != PARSER_VERSION)
            System.out.println("RAR!");
        
        while (scan.hasNext())
        {
            String prop = scan.next();
            String val = scan.next();
            System.out.println(prop + " " + val);
            vals.put(prop, val);
        }
        
        scan.close();
    }
    
    /**
     * To be used to SAVE your changes!
     */
    private void save()
    {
        PrintWriter write = null;
        
        try
        {
            write = new PrintWriter(new FileOutputStream(new File(location)));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        write.println("<! " + PARSER_VERSION + " >");
        
        for (int i = 0; i < vals.size(); i++)
        {
            String prop = (String) vals.keySet().toArray()[i];
            String val = (String) vals.values().toArray()[i];
            
            write.println(prop + " " + val);
        }
        
        write.close();
    }
}
