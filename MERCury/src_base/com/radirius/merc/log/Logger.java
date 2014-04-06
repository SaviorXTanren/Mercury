package com.radirius.merc.log;

import com.radirius.merc.exc.SevereLogException;

/**
 * A class that will log in different levels of impact, from NULL to SEVERE,
 * SEVERE being the program killer.
 * 
 * @from merc in com.radirius.merc.log
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 9, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class Logger
{

    public static enum Case
    {
        NULL(""), INFO("INFO: "), DEBUG("DEBUG: "), WARNING("WARNING: "), SEVERE("SEVERE: "), CONSOLE("CONSOLE: "), CONSOLEPROBLEM("CONSOLE PROBLEM: ");

        public String casemsg;

        private Case(String casemsg)
        {
            this.casemsg = casemsg;
        }
    }

    public static void log(String msg, Case cse, String sevmsg) throws SevereLogException
    {
        if (cse != Case.SEVERE && cse != Case.WARNING && cse != Case.CONSOLEPROBLEM)
            System.out.println(cse.casemsg + msg);
        else
        {
            System.err.println(cse.casemsg + msg);
            if (cse == Case.SEVERE)
                throw new SevereLogException(sevmsg);
        }
    }

    public static void log(String msg, Case cse)
    {
        try
        {
            log(msg, cse, "No information given.");
        } catch (SevereLogException e)
        {
            e.printStackTrace();
        }
    }

    public static void log(String msg)
    {
        log(msg, Case.NULL);
    }

    public static void info(String msg)
    {
        log(msg, Case.INFO);
    }

    public static void debug(String msg)
    {
        log(msg, Case.DEBUG);
    }

    public static void warn(String msg)
    {
        log(msg, Case.WARNING);
    }

    public static void severe(String msg) throws SevereLogException
    {
        log(msg, Case.SEVERE, msg);
    }

    public static void console(String msg)
    {
        log(msg, Case.CONSOLE);
    }

    public static void consoleproblem(String msg)
    {
        log(msg, Case.CONSOLEPROBLEM);
    }

    public static void line()
    {
        System.out.println();
    }
}
