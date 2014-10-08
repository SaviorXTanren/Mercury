package radirius.merc.graphics.wip.gui;

import radirius.merc.graphics.*;
import radirius.merc.math.geometry.Rectangle;

/**
 * @author wessles
 */

public class ImageButton extends Component implements Button {
	protected Texture idle, hover, clicked;

	protected boolean wasactive;

	public ImageButton(Rectangle bounds) {
		this(bounds, getDefaultTextures().getTexture(7), getDefaultTextures().getTexture(8), getDefaultTextures().getTexture(9));
	}

	public ImageButton(Rectangle bounds, Texture idle, Texture hover, Texture clicked) {
		super("", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		this.idle = idle;
		this.hover = hover;
		this.clicked = clicked;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		wasactive = false;

		if (isClicked(bounds))
			wasactive = true;
	}

	@Override
	public void render(Graphics g) {
		if (!wasactive)
			if (!isHovered(bounds))
				g.drawTexture(idle, bounds);
			else
				g.drawTexture(hover, bounds);
		else
			g.drawTexture(clicked, bounds);
	}

	@Override
	public boolean wasActive() {
		boolean _wasclicked = wasactive;
		if (wasactive == true)
			wasactive = false;
		return _wasclicked;
	}
}
