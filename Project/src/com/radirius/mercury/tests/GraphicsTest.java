package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.math.geometry.Rectangle;
import org.lwjgl.opengl.GL11;

public class GraphicsTest extends Core {
    int f = 0;

    Rectangle rect;

    public GraphicsTest()
    {
        super("Graphics Test", 800, 600);

        setDebugDisplays(true);
    }

    @Override
    public void init() {
        rect = new Rectangle(100, 100, 100, 100);
    }

    @Override
    public void update(float delta) { f++; }

    @Override
    public void render(Graphics g) {
        g.translate((float) Math.random() * 5, (float) Math.random() * 5);

        g.setColor(Color.CRIMSON);
        g.setBackground(Color.ASBESTOS);

        Runner.getInstance().addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
        Runner.getInstance().addDebugData("Frame", "" + f);

        rect.rotate(1);
        g.drawRectangle(rect);
    }

    @Override
    public void cleanup() {}

    public static void main(String[] args) {
        new GraphicsTest().start();
    }

}
