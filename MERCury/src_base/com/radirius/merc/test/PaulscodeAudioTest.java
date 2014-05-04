package com.radirius.merc.test;

import org.lwjgl.input.Keyboard;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.in.Input;
import com.radirius.merc.paulscodeaud.Audio;
import com.radirius.merc.paulscodeaud.PaulscodeSoundSystemPlugin;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.spl.SplashScreen;

/**
 * A simple test to show that sounds can be played. If this works, then Music
 * does too.
 * 
 * @from merc in com.radirius.merc.test
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 9, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class PaulscodeAudioTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    public PaulscodeAudioTest()
    {
        super("Paulscode Audio Test!");
        rnr.addPlugin(new PaulscodeSoundSystemPlugin());
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        try
        {
            RM.loadResource(Audio.loadAudio(Loader.loadFromClasspath("com/radirius/merc/test/sound.ogg"), false), "ogg");
            RM.loadResource(Audio.loadAudio(Loader.loadFromClasspath("com/radirius/merc/test/sound.wav"), false), "wav");
        } catch (MERCuryException e)
        {
            e.printStackTrace();
        }
        
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
    
    public static void main(String[] args)
    {
        new PaulscodeAudioTest();
    }
}
