package com.teama.merc.exc;

/**
 * A general exception for MERCury, which will print its stacktrace to a file using StackTraceSaver.
 * 
 * @from MERCury in com.wessles.MERCury
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

@SuppressWarnings("serial")
public class MERCuryException extends Exception
{
    private static boolean save = true;
    private static String saveto;
    
    public MERCuryException(String reason)
    {
        super(reason);
        if (save)
            if (saveto == null)
                StackTraceSaver.save(this);
            else
                StackTraceSaver.save(saveto, this);
    }
    
    public static void setSaveStackTrace(boolean save)
    {
        MERCuryException.save = save;
    }
    
    public static void setSaveTo(String saveto)
    {
        MERCuryException.saveto = saveto;
    }
}
