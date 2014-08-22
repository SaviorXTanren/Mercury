package radirius.merc.graphics.gui;

import java.util.ArrayList;

/**
 * @authors wessles, Kristoffer
 */
public interface Container {
    /** Adds a child Component to the Container. */
    public void addChild(Component... child);
    
    /** Adds a new line. */
    public void addNewLine();
    
    /**
     * Get the child components.
     * 
     * @return A list of all child components belonging to the parent container.
     */
    public ArrayList<Component> getChildren();
}
