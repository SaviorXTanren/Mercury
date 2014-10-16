package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.Renderable;
import com.radirius.mercury.utilities.Updatable;

/**
 * @author wessles, Jeviny
 *
 *         A base for all components.
 */
public class Component implements Updatable, Renderable {
	public static Component focused;

	public static final int TYPE_NONSPAN = 0;
	public static final int TYPE_SPAN = 1;
	public static final int TYPE_NONE = 2;
	public int TYPE = TYPE_NONSPAN;

	public static final int FLOAT_LEFT = -1, FLOAT_CENTER = 0, FLOAT_RIGHT = 1;
	public int FLOAT = FLOAT_LEFT;

	public String content;
	public Rectangle bounds;

	public Component(String text, float x, float y, float w, float h) {
		content = text;

		bounds = new Rectangle(x, y, w, h);
	}

	@Override
	public void update(float delta) {
		if (Runner.getInstance().getInput().mouseClicked(Input.MOUSE_LEFT))
			if (isHovered(bounds))
				setFocused(true);
			else if (this == focused)
				setFocused(false);
	}

	@Override
	public void render(Graphics g) {
		renderContent(g);
	}

	public void renderContent(Graphics g) {
		g.drawString(content, bounds.getX(), bounds.getY());
	}

	public boolean hasFocus() {
		return this == focused;
	}

	public void setFocused(boolean focus) {
		if (focus)
			focused = this;
		else
			focused = null;
	}

	public static boolean isHovered(Rectangle bounds) {
		Input input = Runner.getInstance().getInput();
		Vector2f globalmousepos = input.getGlobalMousePosition();

		globalmousepos.div(Runner.getInstance().getGraphics().getScaleDimensions());

		return bounds.contains(globalmousepos);
	}

	public static boolean isClicked(Rectangle bounds) {
		Input input = Runner.getInstance().getInput();

		if (isHovered(bounds))
			if (input.mouseClicked(Input.MOUSE_LEFT))
				return true;

		return false;
	}

	private static SpriteSheet defaultTextures;

	public static SpriteSheet getDefaultTextures() {
		if (defaultTextures == null) {
			Texture spritesheet_texture = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/mercury/graphics/wip/gui/res/gui_spritesheet.png"));

			SubTexture window_bar = new SubTexture(spritesheet_texture, 0, 0, 512, 32); // 0
			SubTexture panel_body = new SubTexture(spritesheet_texture, 1, 41 - 8, 17, 57 - 8); // 1
			SubTexture panel_border = new SubTexture(spritesheet_texture, 0, 41 - 8, 1, 512 - 8); // 2
			SubTexture button_left = new SubTexture(spritesheet_texture, 17, 41 - 8, 33, 72 - 8); // 3
			SubTexture button_body = new SubTexture(spritesheet_texture, 34, 41 - 8, 48, 72 - 8); // 4
			SubTexture checkbox_unchecked = new SubTexture(spritesheet_texture, 49, 41 - 8, 72, 63 - 8); // 5
			SubTexture checkbox_checked = new SubTexture(spritesheet_texture, 73, 41 - 8, 96, 63 - 8); // 6
			SubTexture imagebutton_idle = new SubTexture(spritesheet_texture, 145, 41 - 8, 176, 72 - 8); // 7
			SubTexture imagebutton_hover = new SubTexture(spritesheet_texture, 177, 41 - 8, 208, 72 - 8); // 8
			SubTexture imagebutton_active = new SubTexture(spritesheet_texture, 209, 41 - 8, 240, 72 - 8); // 9
			SubTexture text_field_left = new SubTexture(spritesheet_texture, 218, 40 - 8, 233, 72 - 8); // 10
			SubTexture text_field_body = new SubTexture(spritesheet_texture, 234, 40 - 8, 250, 72 - 8); // 11

			defaultTextures = SpriteSheet.loadSpriteSheet(spritesheet_texture, window_bar, panel_body, panel_border, button_left, button_body, checkbox_unchecked, checkbox_checked, imagebutton_idle, imagebutton_hover, imagebutton_active, text_field_left, text_field_body);
		}

		return defaultTextures;
	}
}
