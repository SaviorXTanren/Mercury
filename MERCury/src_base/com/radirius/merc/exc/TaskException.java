package com.radirius.merc.exc;

/**
 * An exception to be thrown for exceptions involving the task timing.
 * 
 * @from MERCury in com.radirius.merc.exc
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

@SuppressWarnings("serial")
public class TaskException extends Exception {
    public TaskException(String msg) {
        super(msg);
    }
}
