package com.radirius.merc.res;

/**
 * A common denominator of all resources. This is so that all resources can be
 * cloned.
 * 
 * @from merc in com.radirius.merc.res
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public interface Resource extends Cloneable
{
    /**
     * A method for releasing anything that needs to be released, for it is the
     * end!
     */
    public void clean();
}
