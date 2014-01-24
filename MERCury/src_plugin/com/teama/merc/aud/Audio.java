package com.teama.merc.aud;

import java.io.File;
import java.net.MalformedURLException;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import com.teama.merc.exc.MERCuryException;
import com.teama.merc.exc.PluginNotFoundException;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.res.Resource;

/**
 * A simple resource that wraps around paulscode to provide an easy way to play sounds with MERCury.
 * 
 * @from merc in com.teama.merc.aud
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 11, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Audio implements Resource
{
    private static int srcnum = 0;
    
    public final String src;
    
    public Audio(String src)
    {
        this.src = src;
    }
    
    public void play()
    {
        if (getSoundSystem().playing(src))
            getSoundSystem().stop(src);
        
        getSoundSystem().play(src);
    }
    
    public void stop()
    {
        getSoundSystem().stop(src);
    }
    
    public void toggle()
    {
        if (getSoundSystem().playing(src))
            getSoundSystem().stop(src);
        else
            getSoundSystem().play(src);
    }
    
    public Audio setVolume(float vol)
    {
        getSoundSystem().setVolume(src, vol);
        return this;
    }
    
    public Audio setPitch(float pit)
    {
        getSoundSystem().setPitch(src, pit);
        return this;
    }
    
    public Audio setLooping(boolean loop)
    {
        getSoundSystem().setLooping(src, loop);
        return this;
    }
    
    public static Audio loadAudio(String location, boolean loop) throws MERCuryException
    {
        String src = "merc_src_" + srcnum;
        srcnum++;
        
        try
        {
            getSoundSystem().newSource(false, src, new File(location).toURI().toURL(), location, loop, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
        } catch (MalformedURLException e)
        {
            throw new MERCuryException("Problems finding file '" + location + "'");
        }
        
        return new Audio(src);
    }
    
    public static void setMasterVolume(float vol)
    {
        getSoundSystem().setMasterVolume(vol);
    }
    
    public static SoundSystem getSoundSystem()
    {
        try
        {
            return ((PaulscodeSoundSystemPlugin) Runner.getInstance().getPlugin(PaulscodeSoundSystemPlugin.class.getSimpleName())).soundsystem;
        } catch (PluginNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void clean()
    {
        
    }
}
