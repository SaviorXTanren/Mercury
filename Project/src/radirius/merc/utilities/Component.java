package radirius.merc.utilities;

import radirius.merc.graphics.Graphics;

public interface Component {
	public void init();
	public void update(float delta);
	public void render(Graphics g);
}
