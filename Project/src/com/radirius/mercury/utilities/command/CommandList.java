package com.radirius.mercury.utilities.command;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.utilities.logging.Logger;

import java.util.HashMap;

/**
 * A collection of Commands and Variables to be accessed in
 * the given InputStream (the console by default).
 *
 * @author wessles
 */
public class CommandList {
    /**
     * The general manual that will show up when requested
     * in the dev console.
     */
    public static final String general_usage_manual = "\n[ MERCury Developer Console General Usage Manual ]\n\tFormatting of Commands:\n\t\tIf you wish to use a Command with no arguments, simply:\n\t\t\t[Command List] [Command]\n\t\tIf you wish to use a Command with an argument, simply:\n\t\t\t[Command List] [Command] [Argument]\n\t\tIf you wish to use a Command with multiple arguments, simply:\n\t\t\t[Command List] [Command] [Argument 1] [Argument 2]\n\tVariables\n\t\tIf you wish to use a Variable, simply:\n\t\t\t[Command List] [Command] {[Command List] [Variable]}\n\t\tIf the Variable requires an argument, simply:\n\t\t\t[Command List] [Command] {[Command List] [Variable] [Argument 1] [Argument 2]}\n\tEscaping\n\t\tIf your argument uses a curly brace or space, in Variable or not, you will use quotation marks, like so:\n\t\t\t[Command List] [Command] \"This argument can contain spaces without being seperated into different arguments\" \"And this is another argument\"\n\t\tIf your argument wishes to use a quotation mark, simply escape the character:\n\t\t\t[Command List] [Command] \"This argument will have a quotation mark here \\\" and here \\\" and still not be seperated\"\n\tManuals\n\t\tIf you wish to acquire the manual provided from MERCury, simply type '?' :\n\t\t\t?\n\t\tIf you wish to acquire the manual provided by a specific Command List, simply:\n\t\t\t[Command List].?\n\t\tIf you wish to acquire the manual provided by a specific Command, simply:\n\t\t\t[Command List] [Command].?\n\t\tIf you wish to acquire the manual provided by a specific Variable, simply:\n\t\t\t[Command List] [Variable].?";

    /**
     * All of the command lists.
     */
    public static final HashMap<String, CommandList> commandlists = new HashMap<String, CommandList>();
    static CommandList dcmdl;
    /**
     * The name of the command list. This will be
     * case-insensitive.
     */
    public final String name;
    /**
     * The manual that will be shown when requested by the
     * console user. Used for general instruction of using
     * the command list.
     */
    public String manual;

    public HashMap<String, Command> commands = new HashMap<String, Command>();
    public HashMap<String, Variable> variables = new HashMap<String, Variable>();

    /**
     * @param name   The name of the command list. This will be
     *               case-insensitive.
     * @param manual The manual that will be shown when
     *               requested by the console user. Used for
     *               general instruction of using the command
     *               list.
     */
    public CommandList(String name, String manual) {
        this.name = name.toLowerCase();
        this.manual = manual + "\nCommand Manuals:";
    }

    /**
     * @param name The name of the command list. This will be
     *             case-insensitive.
     */
    public CommandList(String name) {
        this(name, "Command List Developer did not provide a manual.");
    }

    /**
     * Adds a command list to the map command lists.
     */
    public static void addCommandList(CommandList cmdl) {
        if (commandlists.containsKey(cmdl.name))
            try {
                throw new MercuryException("A Command List already exists with the name '" + cmdl.name + "!' Command List adding failed.");
            } catch (MercuryException e) {
                e.printStackTrace();
            }
        commandlists.put(cmdl.name, cmdl);
    }

    public static CommandList getDefaultCommandList() {
        if (dcmdl == null) {
            // Make default CommandList
            CommandList cmdlmercury = new CommandList("mercury", "This is the default Command List for Mercury Developer Console. In it, you will find core functions to MERCury Developer Console that will allow you to modify projects within the runtime.");

            // Add in all the commands.
            cmdlmercury.addCommand(new Command("end", "Ends the program.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().end();
                }
            });

            cmdlmercury.addCommand(new Command("setFpsTarget", "mercury setFpsTarget [Fps Target]\nTargets for, or caps the framerate at a given height.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().setTargetFps(Integer.parseInt(args[0]));
                    Logger.console("Framerate targeted for " + Integer.parseInt(args[0]));
                }
            });

            cmdlmercury.addCommand(new Command("setMouseGrab", "mercury setMouseGrab [True/False]\nLocks or releases the mouse from the window.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().enableMouseGrab(Boolean.valueOf(args[0]));
                    Logger.console("Mouse " + (Boolean.valueOf(args[0]) ? "grabbed" : "released") + ".");
                }
            });

            cmdlmercury.addCommand(new Command("setVsync", "Sets whether or not there is Vertical Sync.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().enableVsync(Boolean.valueOf(args[0]));
                    Logger.console("Vsync set to " + args[0]);
                }
            });

            cmdlmercury.addCommand(new Command("setTitle", "Sets the title of the window.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().setTitle(args[0]);
                    Logger.console("Window title set to '" + args[0] + ".'");
                }
            });

            cmdlmercury.addCommand(new Command("setDeltaFactor", "Sets the delta factor, or, the number by which delta is multiplied by, to a given number.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().setDeltaFactor(Float.valueOf(args[0]));
                    Logger.console("Delta factor set to " + args[0]);
                }
            });

            cmdlmercury.addCommand(new Command("setUpdateFreeze", "Sets the freezing of the update.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().enableUpdating(Boolean.valueOf(args[0]));
                    Logger.console("Set update freeze to " + Boolean.valueOf(args[0]));
                }
            });

            cmdlmercury.addCommand(new Command("setRenderFreeze", "Sets the freezing of the render.") {
                @Override
                public void run(String... args) {
                    Runner.getInstance().enableRendering(Boolean.valueOf(args[0]));
                    Logger.console("Set render freeze to " + Boolean.valueOf(args[0]));
                }
            });

            cmdlmercury.addCommand(new Command("echo", "Echos your every word.") {
                @Override
                public void run(String... args) {
                    for (String s : args)
                        Logger.console(s);
                }
            });

            cmdlmercury.addVariable(new Variable("fps") {
                @Override
                public String get(String... args) {
                    return String.valueOf(Runner.getInstance().getFps());
                }
            });

            dcmdl = cmdlmercury;
        }

        return dcmdl;
    }

    /**
     * Adds a command to the list.
     *
     * @param cmd The command that you want to add.
     */
    public void addCommand(Command cmd) {
        if (commands.containsKey(cmd.name))
            try {
                throw new MercuryException("A duplicate Command '" + cmd.name + "' has been attempted. Duplicate rejected.");
            } catch (MercuryException e) {
                e.printStackTrace();
            }
        commands.put(cmd.name, cmd);
        manual += "\n" + cmd.name + ":\t" + cmd.manual + "\n";
    }

    /**
     * Adds a command to the list.
     *
     * @param v The variable that you want to add.
     */
    public void addVariable(Variable v) {
        if (variables.containsKey(v.name))
            try {
                throw new MercuryException("A duplicate Variable '" + v.name + "' has been attempted. Duplicate rejected.");
            } catch (MercuryException e) {
                e.printStackTrace();
            }

        if (commands.containsKey(v.name))
            try {
                throw new MercuryException("A Variable '" + v.name + "' cannot share the same name as the Command! Duplicate rejected.");
            } catch (MercuryException e) {
                e.printStackTrace();
            }

        variables.put(v.name, v);
    }
}
