package com.radirius.mercury.utilities;

import com.radirius.mercury.graphics.Graphics;

public interface Component extends Initializable, Updatable, Renderable {
	
	public void cleanup();
	
}
