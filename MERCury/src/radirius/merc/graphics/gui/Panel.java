package radirius.merc.graphics.gui;

import java.util.ArrayList;

import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Shape;

/**
 * A component to contain and render sorted components.
 * 
 * @author wessles, Jeviny
 */

public class Panel extends Component implements Container {
	public static final int LEFTWARDS = -1, RIGHTWARDS = 1, CENTERED = 0;

	protected ArrayList<Component> children = new ArrayList<Component>();
	protected Rectangle textbounds;
	protected Texture body, border;

	public Panel(Rectangle bounds) {
		this(bounds, 0);
	}

	public Panel(Rectangle bounds, float padding) {
		this(bounds, padding, getDefaultTextures().getTexture(1).convertToTexture(), getDefaultTextures().getTexture(2));
	}

	public Panel(Rectangle bounds, Texture body, Texture border) {
		this(bounds, 0, body, border);
	}

	public Panel(Rectangle bounds, float padding, Texture body, Texture border) {
		super("", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		textbounds = new Rectangle(bounds.getX() + padding, bounds.getY() + padding, bounds.getWidth() - padding, bounds.getHeight() - padding);
		this.body = body;

		if (body != null)
			if (!body.fullCapabilities())
				throw new IllegalArgumentException("Body Texture has to be fully capable. This requires PoT size, and not being a SubTexture.");

		this.border = border;
	}

	@Override
	public void update(float delta) {
		for (Component child : children)
			child.update(delta);
	}

	@Override
	public void render(Graphics g) {
		float bw = border.getWidth();

		// Draw The Body.
		if (body != null)
			g.drawTexture(body, new Rectangle(0, 0, bounds.getWidth(), bounds.getHeight()), bounds);

		// Draw The Borders.
		if (border != null) {
			// Vertical borders
			g.drawTexture(border, bounds.getX(), bounds.getY(), bw, bounds.getHeight() + bw); // Left
			g.drawTexture(border, bounds.getX2(), bounds.getY2(), -bw, -bounds.getHeight() - bw); // Right

			// Horizontal borders
			Rectangle bottomborderbounds = new Rectangle(0, 0, bw, bounds.getWidth());
			bottomborderbounds.rotate(0, 0, -90);
			bottomborderbounds.translateTo(bounds.getX(), bounds.getY2());

			g.drawTexture(border, bottomborderbounds); // Width

			bottomborderbounds.flipX();
			bottomborderbounds.flipY();
			bottomborderbounds.translateTo(bounds.getX(), bounds.getY());

			g.drawTexture(border, bottomborderbounds); // Height
		}

		// Draw The Components.
		for (Component child : children)
			child.render(g);
	}

	public void sortChildren() {
		float x = textbounds.getX(), y = textbounds.getY(), max_height = 0;
		int lastfloat = FLOAT_LEFT;

		ArrayList<Shape> rightshift = new ArrayList<Shape>(), centershift = new ArrayList<Shape>();
		ArrayList<ArrayList<Shape>> rightshiftlines = new ArrayList<ArrayList<Shape>>(), centershiftlines = new ArrayList<ArrayList<Shape>>();

		for (int cnum = 0; cnum < children.size(); cnum++) {
			Component child = children.get(cnum);

			if (child.FLOAT == FLOAT_RIGHT)
				rightshift.add(child.bounds);
			else if (child.FLOAT == FLOAT_CENTER)
				centershift.add(child.bounds);

			// If the line is too long, or the component is a div, or the
			// floating has changed, then make a new line.
			if (x + child.bounds.getWidth() > textbounds.getX2() || child.TYPE == Component.TYPE_NONSPAN || child.TYPE != lastfloat || cnum == children.size()) {
				if (!rightshift.isEmpty()) {
					rightshiftlines.add(new ArrayList<Shape>(rightshift));
					rightshift.clear();
				}
				if (!centershift.isEmpty()) {
					centershiftlines.add(new ArrayList<Shape>(centershift));
					centershift.clear();
				}

				y += max_height;
				max_height = 0;
				x = textbounds.getX();
			}

			child.bounds.translateTo(x, y);

			// Update the current length of the line, since it has been
			// proven to fit.
			x += child.bounds.getWidth();

			// Update the type!
			lastfloat = child.FLOAT;

			// Update the maximum height of all objects on this line.
			max_height = Math.max(max_height, child.bounds.getHeight());
		}

		// Shift all lines in post.
		if (!rightshiftlines.isEmpty())
			for (ArrayList<Shape> elements : rightshiftlines) {
				float dx = textbounds.getX2() - elements.get(elements.size() - 1).getX2();

				for (Shape element : elements)
					element.translate(dx, 0);
			}
		if (!centershiftlines.isEmpty())
			for (ArrayList<Shape> elements : centershiftlines) {
				float dx = (bounds.getX2() - elements.get(elements.size() - 1).getX2()) / 2;

				for (Shape element : elements)
					element.translate(dx, 0);
			}
	}

	@Override
	public void addChild(Component... children) {
		for (Component child : children) {
			this.children.add(child);

			bounds.addChild(child.bounds);
		}
	}

	@Override
	public void addNewLine() {
		addChild(new NewLine());
	}

	@Override
	public ArrayList<Component> getChildren() {
		return children;
	}
}
