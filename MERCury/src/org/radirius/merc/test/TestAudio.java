package org.radirius.merc.test;

import org.radirius.merc.aud.Audio;
import org.radirius.merc.gfx.Graphics;
import org.radirius.merc.in.Input;
import org.radirius.merc.main.Core;
import org.radirius.merc.main.Runner;
import org.radirius.merc.res.Loader;

/**
 * @author wessles
 */

public class TestAudio extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestAudio() {
        super("Hai!");
        rnr.init(this, 500, 500);
        rnr.run();
    }
    
    Audio aud0;
    
    @Override
    public void init() {
        aud0 = Audio.getAudio(Audio.getOGGBuffer(Loader.streamFromClasspath("radirius/merc/test/sound.ogg"))).setLooping(true);
        aud0.play();
    }
    
    @Override
    public void update(float delta) {
        if (Runner.getInstance().getInput().keyClicked(Input.KEY_SPACE)) {
            aud0.setVolume(0.7f);
            aud0.setPitch(2.0f);
            aud0.pause();
        }
        if (Runner.getInstance().getInput().keyClicked(Input.KEY_LSHIFT)) {
            aud0.setVolume(0.7f);
            aud0.setPitch(2.0f);
            aud0.play();
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.drawString("I dare you to press <SPACE> then <SHIFT>", 10, 100);
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new TestAudio();
    }
}
