package com.radirius.merc.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An arraylist that you can wipe Wipeables with.
 * 
 * @from MERCury in com.radirius.merc.util
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

@SuppressWarnings("serial")
public class WipingArrayList<T extends Wipeable> extends ArrayList<T>
{
    public void sweep()
    {
        for (Iterator<?> i = iterator(); i.hasNext();)
        {
            Wipeable w = (Wipeable) i.next();
            if (w.wiped())
                i.remove();
        }
    }
}
