package com.radirius.merc.env;

/**
 * An abstraction for objects that can be updated.
 * 
 * @from merc in com.radirius.merc.env
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
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
