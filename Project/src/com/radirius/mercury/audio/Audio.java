package com.radirius.mercury.audio;

import com.radirius.mercury.resource.Resource;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;

/**
 * An object for loading and using sounds.
 *
 * @author wessles, Jeviny
 */
public class Audio implements Resource {
    /**
     * The source index for OpenAL.
     */
    private final int source;

    /**
     * The buffer for OpenAL to process.
     */
    private final int buffer;

    /**
     * @param source The source index for OpenAL.
     * @param buffer The buffer for OpenAL to process.
     */
    private Audio(int source, int buffer) {
        this.source = source;
        this.buffer = buffer;
    }

    /**
     * Makes a source based off of buffer, and then makes an
     * Audio based off of the source.
     *
     * @param buffer The integer buffer of the sound for OpenAL
     *               to process.
     * @return the audio file.
     */
    public static Audio getAudio(IntBuffer buffer) {
        IntBuffer source = BufferUtils.createIntBuffer(1);
        alGenSources(source);

        alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
        alSourcef(source.get(0), AL_GAIN, 1);
        alSourcef(source.get(0), AL_PITCH, 1);

        if (alGetError() != AL_NO_ERROR)
            throw new RuntimeException("An error occurred while setting source properties.");

        return new Audio(source.get(0), buffer.get(0));
    }

    /**
     * Makes a source based off of buffer, and then makes an
     * Audio based off of the source.
     *
     * @param is     The input file to be read.
     * @param format The format of the audio file ("wav",
     *               "ogg"...).
     * @return the audio file.
     */
    public static Audio getAudio(InputStream is, String format) {
        if (format.equalsIgnoreCase("wav"))
            return getAudio(getWAVBuffer(is));
        else if (format.equalsIgnoreCase("ogg"))
            return getAudio(getOGGBuffer(is));

        return null;
    }

    /**
     * Forms a buffer from is.
     *
     * @param is The stream to the sound file.
     * @return The integer buffer for OpenAL based off of the
     * .ogg file at the InputStream is.
     */
    public static IntBuffer getOGGBuffer(InputStream is) {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);

        OGGDecoder decoder = new OGGDecoder();
        OGGData ogg = null;

        try {
            ogg = decoder.getData(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        alGenBuffers(buffer);

        if (alGetError() != AL_NO_ERROR)
            throw new RuntimeException("An error occurred while making buffers.");

        alBufferData(buffer.get(0), ogg.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16, ogg.data, ogg.rate);

        return buffer;
    }

    /**
     * Forms a buffer from is.
     *
     * @param is The stream to the sound file.
     * @return The integer buffer for OpenAL based off of the
     * .wav file at the InputStream is.
     */
    public static IntBuffer getWAVBuffer(InputStream is) {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);

        alGenBuffers(buffer);

        if (alGetError() != AL_NO_ERROR)
            throw new RuntimeException("An error occurred while making buffers.");

        WaveData waveFile = WaveData.create(is);

        alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();

        return buffer;
    }

    /**
     * @return Whether or not the clip is playing.
     */
    public boolean isPlaying() {
        return alGetSourcei(source, AL_SOURCE_STATE) == AL_PLAYING;
    }

    /**
     * Plays the clip. If it is already playing, it will
     * restart. If the clip is paused, it will continue.
     *
     * @return the audio file.
     */
    public Audio play() {
        alSourcePlay(source);

        return this;
    }

    /**
     * Plays the clip if it is paused, pauses it if it is
     * not paused.
     *
     * @return the audio file.
     */
    public Audio togglePause() {
        if (isPaused())
            play();
        else
            pause();

        return this;
    }

    /**
     * Plays the clip if it is stopped, stops it if it is
     * not stopped.
     *
     * @return the audio file.
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
     *
     * @return the audio file.
     */
    public Audio pause() {
        alSourcePause(source);

        return this;
    }

    /**
     * @return Whether or not the clip is stopped.
     */
    public boolean isStopped() {
        return alGetSourcei(source, AL_SOURCE_STATE) == AL_STOPPED;
    }

    /**
     * Stops the clip.
     *
     * @return the audio file.
     */
    public Audio stop() {
        alSourceStop(source);

        return this;
    }

    /**
     * @return The volume of the clip.
     */
    public float getVolume() {
        return alGetSourcef(source, AL_GAIN);
    }

    /**
     * Sets the volume of the clip.
     *
     * @param vol A floating point volume value, 0.8 being
     *            80%, 1.5 being 150%, etc.
     * @return the audio file.
     */
    public Audio setVolume(float vol) {
        alSourcef(source, AL_GAIN, vol);

        return this;
    }

    /**
     * @return The pitch of the clip.
     */
    public float getPitch() {
        return alGetSourcef(source, AL_PITCH);
    }

    /**
     * Sets the pitch of the clip.
     *
     * @param pit A floating point pitch value, 0.8 being
     *            80%, 1.5 being 150%, etc.
     * @return the audio file.
     */
    public Audio setPitch(float pit) {
        alSourcef(source, AL_PITCH, pit);

        return this;
    }

    /**
     * @return Whether or not the clip is looping.
     */
    public boolean isLooping() {
        return alGetSourcei(source, AL_LOOPING) == AL_LOOPING;
    }

    /**
     * Sets whether or not the clip should loop.
     *
     * @param loop Whether or not the clip should loop.
     * @return the audio file.
     */
    public Audio setLooping(boolean loop) {
        alSourcei(source, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);

        return this;
    }

    @Override
    public void clean() {
        alDeleteBuffers(buffer);
        alDeleteSources(source);
    }
}
