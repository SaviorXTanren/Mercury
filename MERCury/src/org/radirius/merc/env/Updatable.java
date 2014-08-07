package org.radirius.merc.env;

/**
 * An abstraction for objects that can be updated.
 * 
 * @author wessles
 */

public interface Updatable {
    /**
     * The method for updating. In here, logic should occur, given delta.
     * 
     * @param delta
     *            The delta time variable.
     */
    public void update(float delta);
}
