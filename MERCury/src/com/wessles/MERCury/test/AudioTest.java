package com.wessles.MERCury.test;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.wessles.MERCury.aud.Audio;
import com.wessles.MERCury.aud.PaulscodeSoundSystemPlugin;
import com.wessles.MERCury.exception.MERCuryException;
import com.wessles.MERCury.fwk.Core;
import com.wessles.MERCury.fwk.Runner;
import com.wessles.MERCury.gfx.Graphics;
import com.wessles.MERCury.gfx.Texture;
import com.wessles.MERCury.in.Input;
import com.wessles.MERCury.res.ResourceManager;
import com.wessles.MERCury.spl.SplashScreen;

/**
 * A simple test to show that sounds can be played. If this works, then Music does too.
 * 
 * @from MERCury in com.wessles.MERCury.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 9, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class AudioTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    public AudioTest()
    {
        super("yo");
        rnr.addPlugin(new PaulscodeSoundSystemPlugin());
        rnr.init(this, 100, 100);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        try
        {
            RM.loadResource(Audio.loadAudio("res/test/AudioTest/sound.ogg", false), "ogg");
            RM.loadResource(Audio.loadAudio("res/test/AudioTest/sound.wav", false), "wav");
        } catch (MERCuryException e)
        {
            e.printStackTrace();
        }
        
        try
        {
            Texture tex = Texture.loadTexture("res/splash.png");
            float ratio = tex.getTextureWidth() / tex.getTextureHeight();
            int height = (int) (rnr.width() / ratio);
            SplashScreen splash = new SplashScreen(tex, 3000, rnr.width(), height, true);
            rnr.addSplashScreen(splash);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(float delta)
    {
        Input in = rnr.input();
        if (in.keyDown(Keyboard.KEY_W))
            ((Audio) rnr.resourceManager().retrieveResource("wav")).play();
        else if (in.keyClicked(Keyboard.KEY_O))
            ((Audio) rnr.resourceManager().retrieveResource("ogg")).toggle();
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
        new AudioTest();
    }
}
