package com.radirius.merc.cmd;

/**
 * A Command that will do a specific task when asked in the Dev Console.
 * 
 * @from MERCury in com.radirius.merc.cmd
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public abstract class Command
{
    public final String name, manual;

    public Command(String name, String manual)
    {
        this.name = name.toLowerCase();
        this.manual = manual;
    }

    public Command(String name)
    {
        this(name, "Command List Developer did not provide a manual for Command.");
    }

    public abstract void run(String... args);
}
