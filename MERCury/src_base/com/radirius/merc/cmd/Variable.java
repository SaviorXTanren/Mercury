package com.radirius.merc.cmd;

/**
 * A Variable that can be referenced within the Dev Console.
 * 
 * @from MERCury in com.radirius.merc.cmd
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public abstract class Variable
{
    public final String name, manual;

    public Variable(String name, String manual)
    {
        this.name = name.toLowerCase();
        this.manual = manual;
    }

    public Variable(String name)
    {
        this(name, "Command List Developer did not provide a manual for Variable.");
    }

    public abstract String get(String... args);
}
