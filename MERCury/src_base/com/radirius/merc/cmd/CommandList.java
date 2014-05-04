package com.radirius.merc.cmd;

import java.util.HashMap;

import com.radirius.merc.exc.ConsoleException;

/**
 * A collection of Commands and Variables to be accessed in the Dev Console.
 * 
 * @from MERCury in com.radirius.merc.cmd
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class CommandList
{
    /**
     * The general manual that will show up when requested in the dev console.
     */
    public static final String general_usage_manual = "\n[ MERCury Developer Console General Usage Manual ]\n\tFormatting of Commands:\n\t\tIf you wish to use a Command with no arguments, simply:\n\t\t\t[Command List] [Command]\n\t\tIf you wish to use a Command with an argument, simply:\n\t\t\t[Command List] [Command] [Argument]\n\t\tIf you wish to use a Command with multiple arguments, simply:\n\t\t\t[Command List] [Command] [Argument 1] [Argument 2]\n\tVariables\n\t\tIf you wish to use a Variable, simply:\n\t\t\t[Command List] [Command] {[Command List] [Variable]}\n\t\tIf the Variable requires an argument, simply:\n\t\t\t[Command List] [Command] {[Command List] [Variable] [Argument 1] [Argument 2]}\n\tEscaping\n\t\tIf your argument uses a curly brace or space, in Variable or not, you will use quotation marks, like so:\n\t\t\t[Command List] [Command] \"This argument can contain spaces without being seperated into different arguments\" \"And this is another argument\"\n\t\tIf your argument wishes to use a quotation mark, simply escape the character:\n\t\t\t[Command List] [Command] \"This argument will have a quotation mark here \\\" and here \\\" and still not be seperated\"\n\tManuals\n\t\tIf you wish to acquire the manual provided from MERCury, simply type '?' :\n\t\t\t?\n\t\tIf you wish to acquire the manual provided by a specific Command List, simply:\n\t\t\t[Command List].?\n\t\tIf you wish to acquire the manual provided by a specific Command, simply:\n\t\t\t[Command List] [Command].?\n\t\tIf you wish to acquire the manual provided by a specific Variable, simply:\n\t\t\t[Command List] [Variable].?";
    
    /**
     * All of the command lists.
     */
    public static final HashMap<String, CommandList> commandlists = new HashMap<String, CommandList>();
    
    /**
     * Adds a command list to the map commandlists.
     */
    public static void addCommandList(CommandList cmdl)
    {
        if (commandlists.containsKey(cmdl.name))
            try
            {
                throw new ConsoleException("A Command List already exists with the name '" + cmdl.name + "!' Command List adding failed.");
            } catch (ConsoleException e)
            {
                e.printStackTrace();
            }
        commandlists.put(cmdl.name, cmdl);
    }
    
    /** The name of the command list. This will be case-insensitive. */
    public final String name;
    /**
     * The manual that will be shown when requested by the console user. Used
     * for general instruction of using the command list.
     */
    public final String manual;
    
    public HashMap<String, Command> commands = new HashMap<String, Command>();
    public HashMap<String, Variable> variables = new HashMap<String, Variable>();
    
    /**
     * @param name
     *            The name of the command list. This will be case-insensitive.
     * @param manual
     *            The manual that will be shown when requested by the console
     *            user. Used for general instruction of using the command list.
     */
    public CommandList(String name, final String manual)
    {
        // CASE INSENSITIVE!!! HAW HAWH HAWW
        this.name = name.toLowerCase();
        this.manual = manual;
    }
    
    /**
     * @param name
     *            The name of the command list. This will be case-insensitive.
     */
    public CommandList(String name)
    {
        this(name, "Command List Developer did not provide a manual.");
    }
    
    /**
     * Adds a command to the list.
     * 
     * @param cmd
     *            The command that you want to add.
     */
    public void addCommand(Command cmd)
    {
        if (commands.containsKey(cmd.name))
            try
            {
                throw new ConsoleException("A duplicate Command '" + cmd.name + "' has been attempted. Duplicate rejected.");
            } catch (ConsoleException e)
            {
                e.printStackTrace();
            }
        commands.put(cmd.name, cmd);
    }
    
    /**
     * Adds a command to the list.
     * 
     * @param v
     *            The variable that you want to add.
     */
    public void addVariable(Variable v)
    {
        if (variables.containsKey(v.name))
            try
            {
                throw new ConsoleException("A duplicate Variable '" + v.name + "' has been attempted. Duplicate rejected.");
            } catch (ConsoleException e)
            {
                e.printStackTrace();
            }
        
        if (commands.containsKey(v.name))
            try
            {
                throw new ConsoleException("A Variable '" + v.name + "' cannot share the same name as the Command! Duplicate rejected.");
            } catch (ConsoleException e)
            {
                e.printStackTrace();
            }
        
        variables.put(v.name, v);
    }
}
