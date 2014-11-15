package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;

/**
 * @author Jeviny
 */
public class TextButton extends TextBar implements Button {
    protected boolean wasactive;

    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Font textfont) {
        super(txt, left, right, body, x, y, textcolor, textfont);
    }

    public TextButton(String txt, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, null, null, null, x, y, textcolor, TrueTypeFont.ROBOTO_REGULAR);
    }

    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor) {
        this(txt, left, right, body, x, y, textcolor, TrueTypeFont.ROBOTO_REGULAR);
    }

    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y) {
        this(txt, left, right, body, x, y, Color.BLACK);
    }

    public TextButton(String txt, float x, float y) {
        this(txt, getDefaultTextures().getTexture(3), getDefaultTextures().getTexture(3).convertToTexture(true, false), getDefaultTextures().getTexture(4), x, y, Color.WHITE);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (isClicked(bounds))
            wasactive = true;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public boolean wasActive() {
        boolean _waspressed = wasactive;

        wasactive = false;

        return _waspressed;
    }
}
