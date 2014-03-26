package com.radirius.merc.test;

import org.lwjgl.input.Keyboard;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.in.Input;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.slickaud.Audio;
import com.radirius.merc.slickaud.SlickSoundPlugin;
import com.radirius.merc.spl.SplashScreen;

/**
 * @from MERCury in com.radirius.merc.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 25, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class SlickAudioTest extends Core
{
    public Runner rnr = Runner.getInstance();
    
    public Audio ogg, wav;
    
    public SlickAudioTest()
    {
        super("Slick Audio Test!");
        rnr.addPlugin(new SlickSoundPlugin());
        rnr.init(this, 300, 300);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new SlickAudioTest();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        RM.loadResource(Audio.loadAudio(Loader.loadFromClasspath("com/teama/merc/test/sound.ogg")), "ogg");
        RM.loadResource(Audio.loadAudio(Loader.loadFromClasspath("com/teama/merc/test/sound.wav")), "wav");
        
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta)
    {
        Input in = rnr.getInput();
        if (in.keyDown(Keyboard.KEY_W))
            ((Audio) rnr.getResourceManager().retrieveResource("wav")).play();
        else if (in.keyClicked(Keyboard.KEY_O))
            ((Audio) rnr.getResourceManager().retrieveResource("ogg")).toggle();
    }
    
    @Override
    public void render(Graphics g)
    {
        g.drawString(0, 0, "O = OGG\nW = WAV");
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
    }
}
