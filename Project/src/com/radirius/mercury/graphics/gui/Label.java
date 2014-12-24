package com.radirius.mercury.graphics.gui;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;

public class Label extends Component {
	public Font drawFont;
	public String message;
	
	public Label(String message, float x, float y) {
		this(message, TrueTypeFont.ROBOTO_REGULAR, x, y);
	}
	
	public Label(String message, float size, float x, float y) {
		this(message, TrueTypeFont.ROBOTO_REGULAR.deriveFont(size), x, y);
	}
	
	public Label(String message, Font drawFont, float x, float y) {
		super(x, y, drawFont.getWidth(message), drawFont.getHeight());
		
		this.message = message;
		this.drawFont = drawFont;
	}
	
	public Label(String message, Color textColor, float x, float y) {
		this(message, TrueTypeFont.ROBOTO_REGULAR, x, y);
	}
	
	public Label(String message, Color textColor, float size, float x, float y) {
		this(message, TrueTypeFont.ROBOTO_REGULAR.deriveFont(size), x, y);
	}
	
	public Label(String message, Color textColor, Font drawFont, float x, float y) {
		super(x, y, drawFont.getWidth(message), drawFont.getHeight());
		
		this.message = message;
		this.textColor = textColor;
		this.drawFont = drawFont;
	}
	
	public void render(Graphics g) {
		g.setColor(textColor);
		g.setFont(drawFont);
		g.drawString(message, boundaries.getX(), boundaries.getY());
	}
}
