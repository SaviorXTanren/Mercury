package us.radiri.merc.gui;

import java.util.ArrayList;

/**
 * @author wessles
 */

public interface Container {
    /** Adds a/multiple child(ren) to the container. */
    public void addChild(Component... child);
    
    /** Adds a new line. */
    public void addNewLine();
    
    /** Adds a/multiple child(ren), with a new line, to the container. */
    public void addChildWithNewLine(Component... child);
    
    /**
     * Get the children!
     * 
     * @return A list of all children in the container.
     */
    public ArrayList<Component> getChildren();
}
