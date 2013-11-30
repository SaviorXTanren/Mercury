package com.wessles.MERCury.openal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import com.wessles.MERCury.MERCuryException;
import com.wessles.MERCury.Resource;

/**
 * An audio class that will load, store, and use 8-16 bit sound.
 * 
 * When using a sound effect, it is adviced that you clone the audio, since only
 * one instance of the sound can play at a time.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Audio implements Resource {
	public final int src, buf;

	public Audio(int src, int buf) {
		this.src = src;
		this.buf = buf;
	}

	public void play() {
		AL10.alSourcePlay(src);
	}

	public void stop() {
		AL10.alSourceStop(src);
	}

	public void pause() {
		AL10.alSourcePause(src);
	}

	/**
	 * Volume is also known as gain.
	 */
	public Audio setVolume(float vol) {
		AL10.alSourcef(src, AL10.AL_GAIN, vol);
		return this;
	}

	public Audio setPitch(float pit) {
		AL10.alSourcef(src, AL10.AL_PITCH, pit);
		return this;
	}

	public Audio getNewSource() {
		return loadAudio(BufferUtils.createIntBuffer(1), BufferUtils.createIntBuffer(1).put(buf));
	}

	@Override
	public void clean() {
		AL10.alDeleteSources(src);
		AL10.alDeleteBuffers(buf);
	}

	public static Audio loadAudio(String location) throws MERCuryException, IOException {
		return loadAudio(location, 0, 0);
	}

	public static Audio loadAudio(String location, float srcx, float srcy) throws MERCuryException, IOException {
		return loadAudio(location, srcx, srcy, 0, 0);
	}

	public static Audio loadAudio(String location, float srcx, float srcy, float velx, float vely) throws MERCuryException, IOException {
		// Load wav file into buffer
		IntBuffer buf = BufferUtils.createIntBuffer(1), src = BufferUtils.createIntBuffer(1);

		AL10.alGenBuffers(buf);

		if (AL10.alGetError() != AL10.AL_NO_ERROR)
			throw new MERCuryException("OpenAl threw an error: " + AL10.alGetError());

		if (!new File(location).exists())
			throw new IOException("No '" + location + "' exists!");

		// Load buffer data onto OpenAL
		AudioBufferData abd = AudioBufferData.create(new BufferedInputStream(new FileInputStream(location)));
		AL10.alBufferData(buf.get(0), abd.fmt, abd.buf, abd.smp);
		abd.dispose();

		return loadAudio(src, buf);
	}

	private static Audio loadAudio(IntBuffer src, IntBuffer buf) {
		// Bind buffer with source
		AL10.alGenSources(src);

		if (AL10.alGetError() != AL10.AL_NO_ERROR)
			try {
				throw new MERCuryException("OpenAl threw an error: " + AL10.alGetError());
			} catch (MERCuryException e) {
				e.printStackTrace();
			}

		AL10.alSourcei(src.get(0), AL10.AL_BUFFER, buf.get(0));
		AL10.alSourcef(src.get(0), AL10.AL_PITCH, 1f);
		AL10.alSourcef(src.get(0), AL10.AL_GAIN, 1f);

		if (AL10.alGetError() != AL10.AL_NO_ERROR)
			try {
				throw new MERCuryException("OpenAl threw an error: " + AL10.alGetError());
			} catch (MERCuryException e) {
				e.printStackTrace();
			}

		return new Audio(src.get(0), buf.get(0));
	}
}
