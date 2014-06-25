package us.radiri.merc.test;

import us.radiri.merc.audio.Audio;
import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.input.Input;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class AudioTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public AudioTest() {
        super("Hai!");
        rnr.init(this, 500, 500);
        rnr.run();
    }
    
    Audio aud0;
    
    @Override
    public void init() {
        aud0 = Audio.getAudio(Audio.getOGGBuffer(Loader.streamFromClasspath("us/radiri/merc/test/sound.ogg"))).setLooping(true);
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
        g.drawString(10, 100, "I dare you to press <SPACE> then <SHIFT>");
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new AudioTest();
    }
}
