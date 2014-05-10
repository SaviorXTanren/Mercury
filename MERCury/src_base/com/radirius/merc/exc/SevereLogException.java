package com.radirius.merc.exc;

/**
 * An exception thrown when there is a severe log entry.
 * 
 * @from merc in com.radirius.merc.exc
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 9, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

@SuppressWarnings("serial")
public class SevereLogException extends MERCuryException {
    public SevereLogException(String sevmsg) {
        super("The log has reported SEVERE, and closed the program:\n" + sevmsg);
    }
}
