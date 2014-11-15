package com.radirius.mercury.utilities;

import com.radirius.mercury.utilities.misc.Cleanable;
import com.radirius.mercury.utilities.misc.Initializable;
import com.radirius.mercury.utilities.misc.Renderable;
import com.radirius.mercury.utilities.misc.Updatable;

/**
 * An abstraction for components, objects which are initialized, updated,
 * rendered and cleaned up.
 *
 * @author Jeviny
 */
public interface Component extends Initializable, Updatable, Renderable, Cleanable {
}
