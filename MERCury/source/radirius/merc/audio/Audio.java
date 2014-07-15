package radirius.merc.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_NO_ERROR;
import static org.lwjgl.openal.AL10.AL_PAUSED;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_STOPPED;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetBoolean;
import static org.lwjgl.openal.AL10.alGetError;
import static org.lwjgl.openal.AL10.alGetSourcef;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import radirius.merc.resource.Resource;

/**
 * @author wessles
 */

public class Audio implements Resource {
    private final int src, buf;
    
    private Audio(int src, int buf) {
        this.src = src;
        this.buf = buf;
    }
    
    /**
     * @return Whether or not the clip is playing.
     */
    public boolean isPlaying() {
        return alGetSourcei(src, AL_SOURCE_STATE) == AL_PLAYING;
    }
    
    /**
     * Plays the clip. If it is already playing, it will restart. If the clip is
     * paused, it will continue.
     */
    public Audio play() {
        alSourcePlay(src);
        return this;
    }
    
    /**
     * Plays the clip if it is paused, pauses it if it is not paused.
     */
    public Audio togglePause() {
        if (isPaused())
            play();
        else
            pause();
        return this;
    }
    
    /**
     * Plays the clip if it is stopped, stops it if it is not stopped.
     */
    public Audio toggleStop() {
        if (isStopped())
            play();
        else
            stop();
        return this;
    }
    
    /**
     * @return Whether or not the clip is paused.
     */
    public boolean isPaused() {
        return alGetBoolean(AL_PAUSED);
    }
    
    /**
     * Pauses the clip. Reversible by play().
     */
    public Audio pause() {
        alSourcePause(src);
        return this;
    }
    
    /**
     * @return Whether or not the clip is stopped.
     */
    public boolean isStopped() {
        return alGetSourcei(src, AL_SOURCE_STATE) == AL_STOPPED;
    }
    
    /**
     * Stops the clip.
     */
    public Audio stop() {
        alSourceStop(src);
        return this;
    }
    
    /**
     * @return The volume of the clip.
     */
    public float getVolume() {
        return alGetSourcef(src, AL_GAIN);
    }
    
    /**
     * Sets the volume of the clip.
     * 
     * @param vol
     *            A volume between 0 and 1.
     */
    public Audio setVolume(float vol) {
        alSourcef(src, AL_GAIN, vol);
        return this;
    }
    
    /**
     * @return The pitch of the clip.
     */
    public float getPitch() {
        return alGetSourcef(src, AL_PITCH);
    }
    
    /**
     * Sets the pitch of the clip.
     * 
     * @param pit
     *            A pitch.
     */
    public Audio setPitch(float pit) {
        alSourcef(src, AL_PITCH, pit);
        return this;
    }
    
    /**
     * @return Whether or not the clip is looping.
     */
    public boolean isLooping() {
        return alGetSourcei(src, AL_LOOPING) == AL_LOOPING;
    }
    
    /**
     * Sets whether or not the clip should loop.
     * 
     * @param loop
     *            Whether or not the clip should loop.
     */
    public Audio setLooping(boolean loop) {
        alSourcei(src, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
        return this;
    }
    
    /**
     * Makes a source based off of buf, and then makes an Audio based off of the
     * source.
     * 
     * @param buf
     *            The buffer of the sound.
     */
    public static Audio getAudio(IntBuffer buf) {
        IntBuffer src = BufferUtils.createIntBuffer(1);
        alGenSources(src);
        
        alSourcei(src.get(0), AL_BUFFER, buf.get(0));
        alSourcef(src.get(0), AL_GAIN, 1);
        alSourcef(src.get(0), AL_PITCH, 1);
        
        if (alGetError() != AL_NO_ERROR)
            throw new RuntimeException("An error occurred while setting source properties.");
        
        return new Audio(src.get(0), buf.get(0));
    }
    
    /**
     * Forms a buffer from is.
     * 
     * @param is
     *            The stream to the sound file.
     */
    public static IntBuffer getOGGBuffer(InputStream is) {
        IntBuffer buf = BufferUtils.createIntBuffer(1);
        
        OggDecoder decoder = new OggDecoder();
        OggData ogg = null;
        try {
            ogg = decoder.getData(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        alGenBuffers(buf);
        alBufferData(buf.get(0), ogg.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16, ogg.data, ogg.rate);
        
        return buf;
    }
    
    /**
     * Froms a buffer from is.
     * 
     * @param is
     *            The stream to the sound file.
     */
    public static IntBuffer getWAVBuffer(InputStream is) {
        IntBuffer buf = BufferUtils.createIntBuffer(1);
        alGenBuffers(buf);
        
        if (alGetError() != AL_NO_ERROR)
            throw new RuntimeException("An error occurred while making buffers.");
        
        WaveData waveFile = WaveData.create(is);
        alBufferData(buf.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        return buf;
    }
    
    @Override
    public void clean() {
        alDeleteBuffers(buf);
        alDeleteSources(src);
    }
}
