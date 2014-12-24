package com.radirius.mercury.graphics.gui;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.math.geometry.Rectangle;

public abstract class Button extends Component {
	public Texture idleTexture, hoverTexture, clickTexture;
	public String message = "";
	public Font drawFont;

	public Button(String message, float x, float y) {
		this(message, new Rectangle(x - 16, y - 8, TrueTypeFont.ROBOTO_REGULAR.getWidth(message) + 16, TrueTypeFont.ROBOTO_REGULAR.getHeight() + 8));
	}

	public Button(String message, Rectangle boundaries) {
		this(message, TrueTypeFont.ROBOTO_REGULAR, boundaries);
	}

	public Button(String message, Font drawFont, Rectangle boundaries) {
		super(boundaries);

		this.message = message;
		this.drawFont = drawFont;
		
		addDefaultActionCheck();
	}

	public Button(String message, Texture idleTexture, Rectangle boundaries) {
		this(message, idleTexture, idleTexture, idleTexture, boundaries);
	}

	public Button(String message, Texture idleTexture, Texture hoverTexture, Texture clickTexture, Rectangle boundaries) {
		this(message, TrueTypeFont.ROBOTO_REGULAR, idleTexture, hoverTexture, clickTexture, boundaries);
	}

	public Button(String message, Font drawFont, Texture idleTexture, Texture hoverTexture, Texture clickTexture, Rectangle boundaries) {
		super(boundaries);

		this.message = message;
		this.drawFont = drawFont;
		this.idleTexture = idleTexture;
		this.hoverTexture = hoverTexture;
		this.clickTexture = clickTexture;
		
		addDefaultActionCheck();
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Graphics g) {
		if (idleTexture != null && hoverTexture != null && clickTexture != null) {
			if (state == IDLE)
				g.drawTexture(idleTexture, boundaries);
			else if (state == HOVERED)
				g.drawTexture(hoverTexture, boundaries);
			else if (state == CLICKED)
				g.drawTexture(clickTexture, boundaries);
		} else {
			g.setFont(drawFont);
			g.setLineWidth(8);

			if (state == IDLE) {
				g.setColor(new Color(39, 174, 96));
				g.drawRectangle(boundaries);
				g.traceRectangle(boundaries);
			} else if (state == HOVERED || state == CLICKED) {
				g.setColor(new Color(46, 204, 113));
				g.drawRectangle(boundaries);
				g.traceRectangle(boundaries);
			}

			g.setLineWidth(1);
		}

		g.setColor(textColor);
		g.drawString(message, boundaries.getCenter().getX() - g.getFont().getWidth(message) / 2, boundaries.getCenter().getY() - g.getFont().getHeight() / 2);
	}

	private void addDefaultActionCheck() {
		addActionCheck(new ActionCheck() {
			@Override
			public void noAct() {
				((Button) parent).noAct();
			}

			@Override
			public boolean isActed() {
				return ((Button) parent).isActed();
			}

			@Override
			public void act() {
				((Button) parent).act();
			}
		});
	}

	public abstract void act();

	public boolean isActed() {
		return isClicked(boundaries);
	}

	public abstract void noAct();
}
