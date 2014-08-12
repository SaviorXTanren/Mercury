package radirius.merc.utilities;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An arraylist that you can wipe Wipeables with.
 * 
 * @author wessles
 */

@SuppressWarnings("serial")
public class WipingArrayList<T extends Wipeable> extends ArrayList<T> {
    /**
     * Goes through list, removing any objects that have declared themselves
     * 'wiped.'
     */
    public void sweep() {
        for (Iterator<?> i = iterator(); i.hasNext();) {
            Wipeable w = (Wipeable) i.next();
            if (w.wiped())
                i.remove();
        }
    }
}
