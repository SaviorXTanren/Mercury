package com.teama.merc.slickaud;

import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.teama.merc.exc.MERCuryException;
import com.teama.merc.res.Resource;

/**
 * A simple resource that wraps around slick-util to provide an easy way to play sounds with MERCury.
 * 
 * @from merc in com.teama.merc.aud
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 11, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Audio implements Resource
{
    private static float master_vol = 1f;
    
    public final org.newdawn.slick.openal.Audio slickaud;
    
    private float vol = 1f, pit = 1f;
    private boolean loop = false;
    
    public Audio(org.newdawn.slick.openal.Audio slickaud)
    {
        this.slickaud = slickaud;
    }
    
    public void play()
    {
        slickaud.stop();
        slickaud.playAsSoundEffect(vol * master_vol, pit, loop);
    }
    
    public void stop()
    {
        slickaud.stop();
    }
    
    public void toggle()
    {
        if (slickaud.isPlaying())
            stop();
        else
            play();
    }
    
    public Audio setVolume(float vol)
    {
        this.vol = vol;
        return this;
    }
    
    public Audio setPitch(float pit)
    {
        this.pit = pit;
        return this;
    }
    
    public Audio setLooping(boolean loop)
    {
        this.loop = loop;
        return this;
    }
    
    public static Audio loadAudio(String location) throws MERCuryException
    {
        org.newdawn.slick.openal.Audio slickaud = null;
        
        try
        {
            String format = location.substring(location.lastIndexOf(".") + 1);
            slickaud = AudioLoader.getAudio(format.toUpperCase(), ResourceLoader.getResourceAsStream(location));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return new Audio(slickaud);
    }
    
    public static void setMasterVolume(float vol)
    {
        Audio.master_vol = vol;
    }
    
    @Override
    public void clean()
    {
    }
}
