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
    /** The name of the command. This will be case-insensitive. */
    public final String name;
    /**
     * The manual that will be shown when requested by the console user. Used
     * for general instruction of using the command.
     */
    public final String manual;
    
    /**
     * @param name
     *            The name of the command. This will be case-insensitive.
     * @param manual
     *            The manual that will be shown when requested by the console
     *            user. Used for general instruction of using the command.
     */
    public Command(String name, String manual)
    {
        this.name = name.toLowerCase();
        this.manual = manual;
    }
    
    /**
     * @param name
     *            The name of the command. This will be case-insensitive.
     */
    public Command(String name)
    {
        this(name, "Command List Developer did not provide a manual for Command.");
    }
    
    /**
     * The action to do when the command is run given args.
     * 
     * @param args
     *            The arguments given for the command. You do not need to use
     *            these.
     */
    public abstract void run(String... args);
}
