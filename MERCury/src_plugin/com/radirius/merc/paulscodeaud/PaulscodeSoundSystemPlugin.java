package com.radirius.merc.paulscodeaud;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;

import com.radirius.merc.fmwk.Plugin;
import com.radirius.merc.log.Logger;

/**
 * @from merc in com.teama.merc.aud
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class PaulscodeSoundSystemPlugin implements Plugin
{
    public SoundSystem soundsystem;

    @Override
    public void init()
    {
        try
        {
            try
            {
                Class.forName("paulscode.sound.libraries.LibraryLWJGLOpenAL", false, this.getClass().getClassLoader());
                SoundSystemConfig.addLibrary(Class.forName("paulscode.sound.libraries.LibraryLWJGLOpenAL"));
            } catch (ClassNotFoundException e)
            {
                Logger.warn("LibraryLWJGLOpenAL not found!");
            }
            try
            {
                Class.forName("paulscode.sound.codecs.CodecWav", false, this.getClass().getClassLoader());
                SoundSystemConfig.setCodec("wav", Class.forName("paulscode.sound.codecs.CodecWav"));
            } catch (ClassNotFoundException e)
            {
                Logger.warn("No CodecWav found!");
            }
            try
            {
                Class.forName("paulscode.sound.codecs.CodecJOgg", false, this.getClass().getClassLoader());
                SoundSystemConfig.setCodec("ogg", Class.forName("paulscode.sound.codecs.CodecJOgg"));
                try
                {
                    Class.forName("com.jcraft.jogg.Page", false, this.getClass().getClassLoader());
                } catch (ClassNotFoundException e)
                {
                    Logger.warn("CodecJOgg found without jogg!");
                }
            } catch (ClassNotFoundException e)
            {
                Logger.warn("No CodecJOgg found!");
            }
        } catch (SoundSystemException e)
        {
            e.printStackTrace();
        }

        soundsystem = new SoundSystem();
    }

    @Override
    public void update()
    {
    }

    @Override
    public void cleanup()
    {
        soundsystem.cleanup();
    }
}
