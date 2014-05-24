package com.radirius.merc.res;

/**
 * A common denominator of all resources. This is so that all resources can be
 * cloned.
 * 
 * @author wessles
 */
public interface Resource extends Cloneable {
    /**
     * A method for releasing anything that needs to be released, for it is the
     * end!
     */
    public void clean();
}
