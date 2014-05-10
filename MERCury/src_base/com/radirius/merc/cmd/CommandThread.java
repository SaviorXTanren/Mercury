package com.radirius.merc.cmd;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.radirius.merc.log.Logger;

/**
 * A thread to be ran seperate from the library itself so that in-game freezes
 * will not effect the console, and that the Scanner will not freaking hang when
 * I ask for a simple line.
 * 
 * @author wessles
 */

public class CommandThread implements Runnable {
    private volatile boolean running = false;
    
    private static InputStream readstream = System.in;
    private static boolean readstreamchanged = false;
    
    @Override
    public void run() {
        running = true;
        
        BufferedReader buf = new BufferedReader(new InputStreamReader(readstream));
        
        runloop: while (running) {
            if (readstreamchanged) {
                buf = new BufferedReader(new InputStreamReader(readstream));
                readstreamchanged = false;
            }
            
            // Wait until we are ready... we don't want no hangin!
            try {
                while (!buf.ready() && running)
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        break runloop;
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // Get a trimmed line
            String _line = null;
            try {
                _line = buf.readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // We know we can only have 1 '.?' in the commands
            if (_line.lastIndexOf(".?") != _line.indexOf(".?")) {
                Logger.consoleproblem("Can only reference 1 manual! Please type '?' for syntax help.");
                continue runloop;
            }
            
            // Now we shall get all the variables after splitting the command
            // into spaces
            String[] line = _line.split(" ");
            
            String commandlist = null, command = null;
            commandlist = line[0];
            if (line.length >= 2)
                command = line[1];
            
            String[] arguments = new String[] {};
            
            // Seperate arguments by commas!
            if (line.length >= 3) {
                String rawargs = _line.substring(line[0].length() + line[1].length() + 2);
                
                InputStream is = new ByteArrayInputStream(rawargs.getBytes());
                BufferedReader read = new BufferedReader(new InputStreamReader(is));
                
                boolean escapechar = false;
                
                // Tell whether the character we are reading is to be escaped.
                boolean escaped = false;
                // Are we getting a variable
                boolean invar = false;
                boolean invarescaped = false;
                // The character that was read in each iteration
                int _c;
                char c;
                
                // A list so that we can have a volatile length of an array; in
                // other words, so we can 'add' to it.
                ArrayList<String> args = new ArrayList<String>();
                // The argument being concatinated every iteration.
                String arg = "";
                
                // Current point
                int idx = 0;
                // Injection point
                int inj_idx = 0;
                
                // Another list for variables...
                ArrayList<String> vars = new ArrayList<String>();
                // The variable being concatinated every iteration that is in a
                // variable
                String var = "";
                
                try {
                    // While we still have a character to read, loop.
                    while ((_c = read.read()) > 0) {
                        idx++;
                        // Set the character
                        c = (char) _c;
                        
                        if (c == '\\') {
                            escapechar = true;
                            continue;
                        }
                        
                        // If we are escaped, we will ignore all
                        // syntax-important characters.
                        if (escaped) {
                            // We must stop escaping if there is a closing
                            // parenthesis.
                            if (c == '\"' && !escapechar) {
                                escaped = false;
                                continue;
                            }
                            
                            // Concatinate
                            arg += c;
                            escapechar = false;
                            continue;
                        }
                        
                        if (invar) {
                            if (invarescaped) {
                                if (c == '\"' && !escapechar) {
                                    invarescaped = false;
                                    continue;
                                }
                                
                                var += c;
                                continue;
                            }
                            if (c == '}' && !escapechar) {
                                vars.add(var);
                                var = "";
                                
                                if (vars.size() < 2)
                                    Logger.consoleproblem("Must have at least 2 parameters for a Variable argument. Please type '?' for help.");
                                
                                // We have reached the end! Time to inject!
                                // *cracks knuckles*
                                
                                String varcommandlist = vars.get(0).toLowerCase();
                                String varname = vars.get(1).toLowerCase();
                                
                                vars.remove(0);
                                vars.remove(0);
                                
                                String[] varargs = new String[vars.size()];
                                vars.toArray(varargs);
                                
                                CommandList cmdl = CommandList.commandlists.get(varcommandlist);
                                if (cmdl == null) {
                                    Logger.consoleproblem("Could not find Command List '" + varcommandlist + "' supposed by Variable search. Please type '?' for syntax help.");
                                    invar = false;
                                    continue;
                                }
                                
                                Variable varval = cmdl.variables.get(varname);
                                if (varval == null) {
                                    Logger.consoleproblem("Could not find Variable '" + varname + "' in Command List '" + varcommandlist + ".' Please type '?' for syntax help.");
                                    invar = false;
                                    continue;
                                }
                                
                                String varvalstr = varval.get(varargs);
                                arg += varvalstr;
                                
                                invar = false;
                                continue;
                            }
                            
                            if (c == '\"' && !escapechar) {
                                invarescaped = true;
                                continue;
                            }
                            
                            if (c == ' ' && !escapechar) {
                                vars.add(var);
                                var = "";
                                continue;
                            }
                            
                            var += c;
                            escapechar = false;
                            continue;
                        }
                        
                        // From this point on, we are not escaped, nor in a
                        // variable.
                        
                        // But do we want to escape?
                        if (c == '\"' && !escapechar) {
                            escaped = true;
                            continue;
                        }
                        
                        // Do we want to inject?
                        if (c == '{' && !escapechar) {
                            inj_idx = idx;
                            
                            if (inj_idx < 0)
                                inj_idx = 0;
                            
                            invar = true;
                            continue;
                        }
                        
                        // Is there a new argument?
                        if (c == ' ' && !escapechar) {
                            args.add(arg);
                            arg = "";
                            continue;
                        }
                        
                        // Concatinate!
                        arg += c;
                        escapechar = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                if (arg != "")
                    args.add(arg);
                
                arguments = args.toArray(arguments);
            }
            
            // We already have the answer, so lets just display it.
            if (commandlist.equals("?")) {
                if (line.length > 1) {
                    Logger.consoleproblem("Stray '?' in command. Please type '?' for syntax help.");
                    continue runloop;
                }
                Logger.console(CommandList.general_usage_manual);
                continue runloop;
            }
            
            // Format them
            boolean list_manual = false, command_manual = false;
            
            if (line.length == 1) {
                if (commandlist.endsWith(".?")) {
                    commandlist = commandlist.replace(".?", "");
                    list_manual = true;
                }
            } else if (line.length == 2)
                if (command.endsWith(".?")) {
                    command = command.replace(".?", "");
                    command_manual = true;
                }
            
            // Find commandlists and commands
            CommandList cmdl = CommandList.commandlists.get(commandlist);
            if (cmdl == null) {
                Logger.consoleproblem("Could not find supposed CommandList '" + commandlist.toLowerCase() + ".' Please type '?' for syntax help.");
                continue runloop;
            }
            Command cmd = cmdl.commands.get(command);
            if (cmd == null && !list_manual) {
                Variable var = cmdl.variables.get(command.toLowerCase());
                if (var == null && !list_manual) {
                    Logger.consoleproblem("Could not find supposed Command '" + command.toLowerCase() + "' in Command List '" + commandlist.toLowerCase() + "' Please type '?' for syntax help.");
                    continue runloop;
                }
                
                Logger.console(var.manual);
                continue runloop;
            }
            
            // Do crap
            if (command_manual)
                Logger.console(cmd.manual);
            else if (list_manual)
                Logger.console(cmdl.manual);
            else
                cmd.run(arguments);
        }
        
        Logger.debug("Developer's console shutting down...");
    }
    
    public static void setInputStream(InputStream in) {
        readstream = in;
        readstreamchanged = true;
    }
}
