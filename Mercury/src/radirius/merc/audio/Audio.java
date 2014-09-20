package radirius.merc.audio;

import static org.lwjgl.openal.AL10.*;

import java.io.*;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import radirius.merc.resource.Resource;

/**
 * An object for loading and using sounds.
 *
 * @author wessles
 */

public class Audio implements Resource {
	private final int src, buf;

	/**
	 * @param src
	 *            The source index for OpenAL.
	 * @param buf
	 *            The buffer for OpenAL to process.
	 */
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
	 *
	 * @return Me
	 */
	public Audio play() {
		alSourcePlay(src);
		return this;
	}

	/**
	 * Plays the clip if it is paused, pauses it if it is not paused.
	 *
	 * @return Me
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
	 *
	 * @return Me
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
	 * @return Me
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
	 *
	 * @return Me
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
	 *            A floating point volume value, 0.8 being 80%, 1.5 being 150%,
	 *            etc.
	 *
	 * @return Me
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
	 *            A floating point pitch value, 0.8 being 80%, 1.5 being 150%,
	 *            etc.
	 *
	 * @return Me
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
	 *
	 * @return Me
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
	 *            The integer buffer of the sound for OpenAL to process.
	 *
	 * @return Me
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
	 * Makes a source based off of buf, and then makes an Audio based off of the
	 * source.
	 *
	 * @param is
	 *            The input file to be read.
	 *
	 * @param format
	 *            The format of the audio file ("wav", "ogg"...).
	 *
	 * @return Me
	 */
	public static Audio getAudio(InputStream is, String format) {
		format = format.toLowerCase();
		if (format == "wav")
			return getAudio(getWAVBuffer(is));
		else if (format == "ogg")
			return getAudio(getOGGBuffer(is));

		return null;
	}

	/**
	 * Forms a buffer from is.
	 *
	 * @param is
	 *            The stream to the sound file.
	 *
	 * @return The integer buffer for OpenAL based off of the .ogg file at the
	 *         InputStream is.
	 */
	public static IntBuffer getOGGBuffer(InputStream is) {
		// The buffer for OpenAL
		IntBuffer buf = BufferUtils.createIntBuffer(1);

		// Make the decoder and the data.
		OGGDecoder decoder = new OGGDecoder();
		OGGData ogg = null;
		try {
			// Decode data
			ogg = decoder.getData(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Generate an index for putting said buffer data
		alGenBuffers(buf);
		// Check for errors.
		if (alGetError() != AL_NO_ERROR)
			throw new RuntimeException("An error occurred while making buffers.");

		// Putting said buffer data to said index
		alBufferData(buf.get(0), ogg.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16, ogg.data, ogg.rate);

		// And returning.
		return buf;
	}

	/**
	 * Froms a buffer from is.
	 *
	 * @param is
	 *            The stream to the sound file.
	 *
	 * @return The integer buffer for OpenAL based off of the .wav file at the
	 *         InputStream is.
	 */
	public static IntBuffer getWAVBuffer(InputStream is) {
		// The buffer for OpenAL
		IntBuffer buf = BufferUtils.createIntBuffer(1);
		// Generate an index for putting said buffer data
		alGenBuffers(buf);

		// Check for errors.
		if (alGetError() != AL_NO_ERROR)
			throw new RuntimeException("An error occurred while making buffers.");

		// Create wave file based off of the InputStream is
		WaveData waveFile = WaveData.create(is);
		// Write data to index
		alBufferData(buf.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
		// And dispose.
		waveFile.dispose();

		// Also returning...
		return buf;
	}

	@Override
	public void clean() {
		// Frees up space in OpenAL by clearing the buffer and source.
		alDeleteBuffers(buf);
		alDeleteSources(src);
	}
}
