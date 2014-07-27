package radirius.merc.logging;

import radirius.merc.exceptions.MERCuryException;

/**
 * A class that will log in different levels of impact, from NULL to SEVERE,
 * SEVERE being the program killer.
 * 
 * @author wessles
 */

public class Logger {
    public static boolean log = true;
    
    /** Cases describing the nature of the log. */
    public static enum Case {
        NULL(""), INFO("INFO: "), DEBUG("DEBUG: "), WARNING("WARNING: "), SEVERE("SEVERE: "), CONSOLE("CONSOLE: "), CONSOLEPROBLEM("CONSOLE PROBLEM: ");
        
        public String casemsg;
        
        private Case(String casemsg) {
            this.casemsg = casemsg;
        }
    }
    
    /** Logs a message in a case, with an optional severe message sevmsg. */
    public static void log(Case cse, String sevmsg, Object... obj) throws MERCuryException {
        if (!log)
            return;
        
        String msg = "";
        for (Object o : obj)
            msg += o + " ";
        if (cse != Case.SEVERE && cse != Case.WARNING && cse != Case.CONSOLEPROBLEM)
            System.out.println(cse.casemsg + msg);
        else {
            System.err.println(cse.casemsg + msg);
            if (cse == Case.SEVERE)
                throw new MERCuryException(sevmsg);
        }
    }
    
    /** Logs a message in a case. */
    public static void log(Case cse, Object... obj) {
        try {
            log(cse, "No information given.", obj);
        } catch (MERCuryException e) {
            e.printStackTrace();
        }
    }
    
    /** Logs a message in case NULL. */
    public static void log(Object... obj) {
        log(Case.NULL, obj);
    }
    
    /** Logs a message in case INFO. */
    public static void info(Object... obj) {
        log(Case.INFO, obj);
    }
    
    /** Logs a message in case DEBUG. */
    public static void debug(Object... obj) {
        log(Case.DEBUG, obj);
    }
    
    /** Logs a message in case WARNING. */
    public static void warn(Object... obj) {
        log(Case.WARNING, obj);
    }
    
    /** Logs a message in case SEVERE. */
    public static void severe(Object... obj) throws MERCuryException {
        log(Case.SEVERE, obj);
    }
    
    /** Logs a message in case CONSOLE. */
    public static void console(Object... obj) {
        log(Case.CONSOLE, obj);
    }
    
    /** Logs a message in case CONSOLE. */
    public static void consoleproblem(Object... obj) {
        log(Case.CONSOLEPROBLEM, obj);
    }
    
    public static void line() {
        System.out.println();
    }
    
    public static void setLog(boolean log) {
        Logger.log = log;
    }
}
