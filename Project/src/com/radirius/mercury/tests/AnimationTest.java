package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.Animation;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.SpriteSheet;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.resource.Loader;

class AnimationTest extends Core {
    Animation animation;
    SpriteSheet spriteSheet;

    public AnimationTest() {
        super("Animation Test", 800, 600);
    }

    public static void main(String[] args) {
        new AnimationTest().start();
    }

    @Override
    public void init() {
        spriteSheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.streamFromClasspath("com/radirius/mercury/tests/animationTest.png")), 64);
        animation = new Animation(spriteSheet, 150);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Graphics g) {
        g.drawAnimation(animation, 64, 64, 256, 256);
    }

    @Override
    public void cleanup() {
    }
}
