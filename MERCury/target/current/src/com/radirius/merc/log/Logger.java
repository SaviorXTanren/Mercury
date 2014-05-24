package com.radirius.merc.log;

import com.radirius.merc.exc.SevereLogException;

/**
 * A class that will log in different levels of impact, from NULL to SEVERE,
 * SEVERE being the program killer.
 * 
 * @author wessles
 */

public class Logger {
    
    /** Cases describing the nature of the log. */
    public static enum Case {
        NULL(""), INFO("INFO: "), DEBUG("DEBUG: "), WARNING("WARNING: "), SEVERE("SEVERE: "), CONSOLE("CONSOLE: "), CONSOLEPROBLEM("CONSOLE PROBLEM: ");
        
        public String casemsg;
        
        private Case(String casemsg) {
            this.casemsg = casemsg;
        }
    }
    
    /** Logs a message in a case, with an optional severe message sevmsg. */
    public static void log(String msg, Case cse, String sevmsg) throws SevereLogException {
        if (cse != Case.SEVERE && cse != Case.WARNING && cse != Case.CONSOLEPROBLEM)
            System.out.println(cse.casemsg + msg);
        else {
            System.err.println(cse.casemsg + msg);
            if (cse == Case.SEVERE)
                throw new SevereLogException(sevmsg);
        }
    }
    
    /** Logs a message in a case. */
    public static void log(String msg, Case cse) {
        try {
            log(msg, cse, "No information given.");
        } catch (SevereLogException e) {
            e.printStackTrace();
        }
    }
    
    /** Logs a message in case NULL. */
    public static void log(String msg) {
        log(msg, Case.NULL);
    }
    
    /** Logs a message in case INFO. */
    public static void info(String msg) {
        log(msg, Case.INFO);
    }
    
    /** Logs a message in case DEBUG. */
    public static void debug(String msg) {
        log(msg, Case.DEBUG);
    }
    
    /** Logs a message in case WARNING. */
    public static void warn(String msg) {
        log(msg, Case.WARNING);
    }
    
    /** Logs a message in case SEVERE. */
    public static void severe(String msg) throws SevereLogException {
        log(msg, Case.SEVERE, msg);
    }
    
    /** Logs a message in case CONSOLE. */
    public static void console(String msg) {
        log(msg, Case.CONSOLE);
    }
    
    /** Logs a message in case CONSOLE. */
    public static void consoleproblem(String msg) {
        log(msg, Case.CONSOLEPROBLEM);
    }
    
    /** Logs a message in case NULL. */
    public static void line() {
        System.out.println();
    }
}
