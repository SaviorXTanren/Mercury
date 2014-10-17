package com.radirius.mercury.utilities;

import com.radirius.mercury.graphics.Graphics;

public interface Component {
	public void init();
	public void update(float delta);
	public void render(Graphics g);
	public void cleanup();
}
