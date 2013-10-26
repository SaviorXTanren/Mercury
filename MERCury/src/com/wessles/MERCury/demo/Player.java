package com.wessles.MERCury.demo;

import org.lwjgl.input.Keyboard;

import com.wessles.MERCury.Runner;
import com.wessles.MERCury.opengl.*;

/**
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public class Player extends Entity {
	Animation anim;
	StringBuilder renderString = new StringBuilder("Type me in!");

	public Player() {
		super(40, 100, 8, 32);
		this.anim = Runner.getResourceManager().getAnimation("player_anim");
	}

	public void update(float delta) {
		if (Runner.getInput().keyDown(Keyboard.KEY_RIGHT) && x < 300 - w)
			x += 3f*delta;
		else if (Runner.getInput().keyDown(Keyboard.KEY_LEFT) && x > 0)
			x -= 3f*delta;
		
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    renderString.setLength(0);
                }
                if (Keyboard.getEventKey() != Keyboard.KEY_BACK) {
                    if (Keyboard.getEventKey() != Keyboard.KEY_LSHIFT) {
                        renderString.append(Keyboard.getEventCharacter());
                    }
                } else if (renderString.length() > 0) {
                    renderString.setLength(renderString.length() - 1);
                }
            }
        }
	}
	
	public void render(Graphics g) {
		anim.render(x, y, w, h, g);
		g.drawString(10, 10, renderString.toString(), Runner.getResourceManager().getFont("mfont"), .5f);
	}
}
