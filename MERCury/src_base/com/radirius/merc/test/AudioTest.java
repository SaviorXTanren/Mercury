package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.aud.Audio;
import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.in.Input;
import com.radirius.merc.log.Logger;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

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
    public void init(ResourceManager RM) throws IOException, MERCuryException {
        aud0 = Audio.getAudio(Audio.getOGGBuffer(Loader.streamFromClasspath("com/radirius/merc/test/sound.ogg"))).setLooping(true);
        aud0.play();
        
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
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
        
        Logger.debug("" + aud0.getVolume());
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        g.drawString(10, 10, "I dare you to press <SPACE> then <SHIFT>");
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    public static void main(String[] args) {
        new AudioTest();
    }
}