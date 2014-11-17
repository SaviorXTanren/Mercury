package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.Rectangle;
import org.lwjgl.opengl.GL11;

public class FrameBufferTest extends Core {
    int f = 0;

    Rectangle rect;

    FrameBuffer frameBuffer;

    public FrameBufferTest() {
        super("FrameBuffer Test", 800, 600);

        setDebugDisplays(true);
    }

    public static void main(String[] args) {
        new FrameBufferTest().start();
    }

    @Override
    public void init() {
        frameBuffer = FrameBuffer.getFrameBuffer();

        rect = new Rectangle(100, 100, 500, 500);
    }

    @Override
    public void update(float delta) {
        f++;
    }

    @Override
    public void render(Graphics g) {
        frameBuffer.use();
        {
            g.setColor(Color.CRIMSON);
            g.drawRectangle(100, 100, 100, 100);
            g.setColor(Color.CLOUDS);
            g.drawString("This is rendered to framebuffer!!", 0, 0);
        }
        frameBuffer.release();

        g.setBackground(Color.BLACK);

        g.drawTexture(frameBuffer.getTextureObject(), rect);
        rect.rotate(1);

        Runner.getInstance().addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
        Runner.getInstance().addDebugData("Frame", "" + f);
    }

    @Override
    public void cleanup() {
        frameBuffer.cleanup();
    }

}
