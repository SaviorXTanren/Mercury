package radirius.merc.graphics.wip.gui;

import radirius.merc.framework.Runner;
import radirius.merc.graphics.*;
import radirius.merc.graphics.font.*;
import radirius.merc.input.Input;
import radirius.merc.math.geometry.*;

/**
 * @author wessles, Jeviny
 */

public class Window extends Panel {
	protected String name;
	protected Font namefont;
	protected Color namecol;

	protected float barheight;

	protected Texture bar;
	protected ImageButton exit;
	protected Rectangle barbounds;

	protected boolean open = true, dragging = false;

	public Window(String name, Rectangle bounds) {
		this(name, TrueTypeFont.OPENSANS_REGULAR, Color.DEFAULT_DRAWING, bounds, 20);
	}

	public Window(String name, Rectangle bounds, float padding) {
		this(name, TrueTypeFont.OPENSANS_REGULAR, Color.DEFAULT_DRAWING, bounds, padding, getDefaultTextures().getTexture(1), getDefaultTextures().getTexture(2), getDefaultTextures().getTexture(0), new ImageButton(new Rectangle(0, 0, 32, 32)));
	}

	public Window(String name, Rectangle bounds, float padding, Texture body, Texture border, Texture bar, ImageButton exit) {
		this(name, TrueTypeFont.OPENSANS_REGULAR, Color.DEFAULT_DRAWING, bounds, padding, body, border, bar.getHeight(), bar, exit);
	}

	public Window(String name, Color namecol, Rectangle bounds) {
		this(name, TrueTypeFont.OPENSANS_REGULAR, namecol, bounds, 20);
	}

	public Window(String name, Color namecol, Rectangle bounds, float padding) {
		this(name, TrueTypeFont.OPENSANS_REGULAR, namecol, bounds, padding, getDefaultTextures().getTexture(1).convertToTexture(), getDefaultTextures().getTexture(2), getDefaultTextures().getTexture(0), new ImageButton(new Rectangle(0, 0, 32, 32)));
	}

	public Window(String name, Color namecol, Rectangle bounds, float padding, Texture body, Texture border, Texture bar, ImageButton exit) {
		this(name, TrueTypeFont.OPENSANS_REGULAR, namecol, bounds, padding, body, border, bar.getHeight(), bar, exit);
	}

	public Window(String name, Font namefont, Color namecol, Rectangle bounds) {
		this(name, namefont, namecol, bounds, 20);
	}

	public Window(String name, Font namefont, Color namecol, Rectangle bounds, float padding) {
		this(name, namefont, namecol, bounds, padding, getDefaultTextures().getTexture(1).convertToTexture(), getDefaultTextures().getTexture(2), getDefaultTextures().getTexture(0), new ImageButton(new Rectangle(0, 0, 32, 32)));
	}

	public Window(String name, Font namefont, Color namecol, Rectangle bounds, float padding, Texture body, Texture border, Texture bar, ImageButton exit) {
		this(name, namefont, namecol, bounds, padding, body, border, bar.getHeight(), bar, exit);
	}

	public Window(String name, Font namefont, Color namecol, Rectangle bounds, float padding, Texture body, Texture border, float barheight, Texture bar, ImageButton exit) {
		super(bounds, padding, body, border);

		this.name = name;
		this.namefont = namefont;
		this.namecol = namecol;

		this.barheight = barheight;
		this.bar = bar;
		this.exit = exit;

		barbounds = new Rectangle(bounds.getX(), bounds.getY() - barheight, bounds.getWidth(), barheight);
		this.bounds.addChild(barbounds);
	}

	private Vec2 mouseanchor;

	@Override
	public void update(float delta) {
		if (!open)
			return;

		Input in = Runner.getInstance().getInput();

		if (in.mouseDown(0)) {
			if (isHovered(barbounds) && !isHovered(bounds))
				if (!dragging) {
					dragging = true;
					mouseanchor = in.getGlobalMousePosition();
				}

			if (dragging) {
				float dx = in.getGlobalMouseX() - mouseanchor.x, dy = in.getGlobalMouseY() - mouseanchor.y;

				bounds.translate(dx, dy);
				mouseanchor.add(new Vec2(dx, dy));
			}

		} else
			dragging = false;

		super.update(delta);

		exit.update(delta);

		if (exit.wasActive())
			open = false;
	}

	@Override
	public void render(Graphics g) {
		if (!open)
			return;

		// Draw The Content!
		super.render(g);

		// TitleBar
		barbounds = new Rectangle(bounds.getX(), bounds.getY() - barheight, bounds.getWidth(), barheight);

		g.drawTexture(bar, barbounds);

		// Close Button
		exit.bounds.translateTo(barbounds.getX(), barbounds.getCenter().y - exit.bounds.getHeight() / 2);

		exit.render(g);

		// Title
		float width = namefont.getWidth(name);
		float height = namefont.getHeight();
		float centerbarx = (barbounds.getX2() + exit.bounds.getX2() + barbounds.getWidth() * 0.01f) / 2;
		float centerbary = barbounds.getY() + barbounds.getHeight() / 2;

		g.setColor(namecol);
		g.drawString(name, namefont, centerbarx - width / 2, centerbary - height / 2);
	}

	/** Sets the window name. */
	public void setName(String name) {
		this.name = name;
	}
}
