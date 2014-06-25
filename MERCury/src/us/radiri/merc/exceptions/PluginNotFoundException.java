package us.radiri.merc.exceptions;

/**
 * An exception for when a plugin could not be found.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class PluginNotFoundException extends Exception {
    
    public PluginNotFoundException(String msg) {
        super(msg);
    }
    
}
