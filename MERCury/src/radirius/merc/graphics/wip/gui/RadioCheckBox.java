package radirius.merc.graphics.wip.gui;

import java.util.ArrayList;

import radirius.merc.graphics.*;
import radirius.merc.graphics.font.*;

/**
 * @author wessles
 */

public class RadioCheckBox extends CheckBox {
	protected ArrayList<RadioCheckBox> group;

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y) {
		this(group, txt, x, y, Color.DEFAULT_TEXT_COLOR);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, Color textcolor) {
		this(group, txt, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, Color textcolor, Font font) {
		this(group, txt, x, y, getDefaultTextures().getTexture(5), getDefaultTextures().getTexture(6), Math.max(getDefaultTextures().getTexture(6).getWidth(), getDefaultTextures().getTexture(5).getWidth()), true, textcolor, font);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, float boxsize) {
		this(group, txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, true, Color.DEFAULT_TEXT_COLOR, TrueTypeFont.OPENSANS_REGULAR);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, float boxsize, Color textcolor) {
		this(group, txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, true, textcolor, TrueTypeFont.OPENSANS_REGULAR);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, float boxsize, boolean boxtoleftoftext) {
		this(group, txt, x, y, boxsize, boxtoleftoftext, TrueTypeFont.OPENSANS_REGULAR);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Font font) {
		this(group, txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, boxtoleftoftext, Color.DEFAULT_TEXT_COLOR, font);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Color textcolor, Font font) {
		this(group, txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, boxtoleftoftext, textcolor, font);
	}

	public RadioCheckBox(ArrayList<RadioCheckBox> group, String txt, float x, float y, Texture unchecked, Texture checked, float boxsize, boolean boxtoleftoftext, Color textcolor, Font font) {
		super(txt, x, y, unchecked, checked, boxsize, boxtoleftoftext, textcolor, font);
		this.group = group;
		this.group.add(this);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (isClicked(bounds)) {
			for (RadioCheckBox rcb : group)
				rcb.setTicked(false);
			setTicked(true);
		}
	}
}
