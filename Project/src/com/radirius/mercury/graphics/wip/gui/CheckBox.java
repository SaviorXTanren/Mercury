package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;

/**
 * @author wessles
 */
public class CheckBox extends Component {
	protected Texture unchecked, checked;
	protected boolean ticked = false;

	protected float boxsize;

	protected boolean boxtoleftoftext = false;

	protected Color textcolor;
	protected Font font;

	public CheckBox(String txt, float x, float y) {
		this(txt, x, y, Color.DEFAULT_DRAWING);
	}

	public CheckBox(String txt, float x, float y, Color textcolor) {
		this(txt, x, y, textcolor, TrueTypeFont.OPENSANS_LIGHT);
	}

	public CheckBox(String txt, float x, float y, Color textcolor, Font font) {
		this(txt, x, y, getDefaultTextures().getTexture(5), getDefaultTextures().getTexture(6), getDefaultTextures().getTexture(5).getWidth(), true, textcolor, font);
	}

	public CheckBox(String txt, float x, float y, float boxsize) {
		this(txt, x, y, getDefaultTextures().getTexture(5), getDefaultTextures().getTexture(6), boxsize, true, Color.DEFAULT_DRAWING, TrueTypeFont.OPENSANS_LIGHT);
	}

	public CheckBox(String txt, float x, float y, float boxsize, Color textcolor) {
		this(txt, x, y, getDefaultTextures().getTexture(5), getDefaultTextures().getTexture(6), boxsize, true, textcolor, TrueTypeFont.OPENSANS_LIGHT);
	}

	public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext) {
		this(txt, x, y, boxsize, boxtoleftoftext, TrueTypeFont.OPENSANS_LIGHT);
	}

	public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Font font) {
		this(txt, x, y, getDefaultTextures().getTexture(5), getDefaultTextures().getTexture(6), boxsize, boxtoleftoftext, Color.DEFAULT_DRAWING, font);
	}

	public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Color textcolor, Font font) {
		this(txt, x, y, getDefaultTextures().getTexture(5), getDefaultTextures().getTexture(6), boxsize, boxtoleftoftext, textcolor, font);
	}

	public CheckBox(String txt, float x, float y, Texture unchecked, Texture checked, float boxsize, boolean boxtoleftoftext, Color textcolor, Font font) {
		super(txt, x, y, font.getWidth(txt) + boxsize, Math.max(font.getHeight(), boxsize));
		this.unchecked = unchecked;
		this.checked = checked;

		this.boxsize = boxsize;

		this.boxtoleftoftext = boxtoleftoftext;

		this.textcolor = textcolor;
		this.font = font;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (isClicked(bounds))
			ticked = !ticked;
	}

	@Override
	public void render(Graphics g) {
		if (boxtoleftoftext) {
			g.setColor(textcolor);
			g.drawString(content, font, bounds.getX() + boxsize, bounds.getY());

			if (!ticked)
				g.drawTexture(unchecked, bounds.getX(), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
			else
				g.drawTexture(checked, bounds.getX(), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
		} else {
			g.setColor(textcolor);
			g.drawString(content, font, bounds.getX(), bounds.getY());

			if (!ticked)
				g.drawTexture(unchecked, bounds.getX() + font.getWidth(content), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
			else
				g.drawTexture(checked, bounds.getX() + font.getWidth(content), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
		}
	}

	public boolean isTicked() {
		return ticked;
	}

	public void setTicked(boolean ticked) {
		this.ticked = ticked;
	}
}
