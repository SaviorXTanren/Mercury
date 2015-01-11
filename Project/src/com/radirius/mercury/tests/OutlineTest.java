package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.CoreSetup;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.resource.Loader;

/**
 * @author wessles
 */
public class OutlineTest extends Core {

    public OutlineTest(CoreSetup coreSetup) {
        super(coreSetup);
    }

    public static void main(String[] args) {
        CoreSetup coreSetup = new CoreSetup("Outline Test");
        coreSetup.width = 1280;
        coreSetup.height = 720;
        OutlineTest outlineTest = new OutlineTest(coreSetup);
        outlineTest.start();
    }

    Texture swirly;
    
    public void init() {
        Graphics g = getGraphics();
        g.setScale(8);

        swirly = Texture.loadTexture(Loader.getResourceAsStream("com/radirius/mercury/tests/swirly.png"));
    }

    public void update() {
    }

    public void render(Graphics g) {
        g.drawTexture(swirly, 0, 0);
    }

    public void cleanup() {
    }
}