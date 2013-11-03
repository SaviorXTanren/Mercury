package com.wessles.MERCury;

import com.wessles.MERCury.opengl.Graphics;

/**
 * An abstraction of objects that can be updated and rendered.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public interface ObjectUR {
	public void update(float delta);

	public void render(Graphics g);
}
