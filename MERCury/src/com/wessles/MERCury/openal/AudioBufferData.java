package com.wessles.MERCury.openal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.wessles.MERCury.MERCuryException;

/**
 * @from MERCury in com.wessles.MERCury.openal
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class AudioBufferData
{
	public final int fmt, smp;
	public final ByteBuffer buf;

	public AudioBufferData(ByteBuffer buf, int fmt, int smp)
	{
		this.buf = buf;
		this.fmt = fmt;
		this.smp = smp;
	}

	public void dispose()
	{
		buf.clear();
	}

	public static AudioBufferData create(AudioInputStream audstrm)
	{

		AudioFormat audform = audstrm.getFormat();

		// Number of channels (audio streams).
		int chans = 0;

		// If the format is mono...
		if (audform.getChannels() == 1)
		{
			// If the format's sample size is 8, set the channels to 4352
			if (audform.getSampleSizeInBits() == 8)
				chans = 4352;
			// If the format's sample size is 16, set the channels to 4353
			else if (audform.getSampleSizeInBits() == 16)
				chans = 4353;
			// Neither 8, nor 16 bit. Something went wrong, so throw an
			// exception.
			else
				try
				{
					throw new MERCuryException("The sample size '" + audform.getSampleSizeInBits() + " bits' is not 8 nor 16! Invalid sample size!");
				} catch (MERCuryException e)
				{
					e.printStackTrace();
				}

		}
		// Unless the format is stereo...
		else if (audform.getChannels() == 2)
		{
			// If the format's sample size is 8, set the channels to 4354
			if (audform.getSampleSizeInBits() == 8)
				chans = 4354;
			// If the format's sample size is 8, set the channels to 4355
			else if (audform.getSampleSizeInBits() == 16)
				chans = 4355;
			// Neither 8, nor 16 bit. Something went wrong, so throw an
			// exception.
			else
				try
				{
					throw new MERCuryException("The sample size '" + audform.getSampleSizeInBits() + " bits' is not 8 nor 16! Invalid sample size!");
				} catch (MERCuryException e)
				{
					e.printStackTrace();
				}
		} else
			try
			{
				throw new MERCuryException("There are neither 1, nor 2 channels. Only mono or stereo is accepted");
			} catch (MERCuryException e)
			{
				e.printStackTrace();
			}

		// Make byte buffer, and convert the bytes.
		ByteBuffer buf = null;
		try
		{
			int available = audstrm.available();
			if (available <= 0)
				available = audform.getChannels() * (int) audstrm.getFrameLength() * audform.getSampleSizeInBits() / 8;

			byte[] bufar = new byte[available];
			int read = 0;
			int total = 0;

			while ((read = audstrm.read(bufar, total, bufar.length - total)) != 1 && total < bufar.length)
				total += read;
			buf = convertBytes(bufar, audform.getSampleSizeInBits() == 16);
		} catch (IOException ie)
		{
			return null;
		}

		// Make returning variable, close inputstream, and return the
		// returning
		// variable.
		AudioBufferData wavebufdata = new AudioBufferData(buf, chans, (int) audform.getSampleRate());
		try
		{
			audstrm.close();
		} catch (IOException ie)
		{
			try
			{
				throw new IOException("An exception occured while closing the audio input stream.");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return wavebufdata;
	}

	public static AudioBufferData create(InputStream is)
	{
		try
		{
			return create(AudioSystem.getAudioInputStream(is));
		} catch (IOException e)
		{
			try
			{
				throw new IOException("Unable to get AudioInputStream from InputStream!");
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e)
		{
			try
			{
				throw new UnsupportedAudioFileException("Audio file not supported!");
			} catch (UnsupportedAudioFileException e1)
			{
				e1.printStackTrace();
			}
		}
		return null;
	}

	private static ByteBuffer convertBytes(byte[] audbytes, boolean is16bit)
	{
		ByteBuffer result = ByteBuffer.allocateDirect(audbytes.length);

		// Order the result bytes by native order...
		result.order(ByteOrder.nativeOrder());

		ByteBuffer src = ByteBuffer.wrap(audbytes);
		// Order the source bytes by little endian (store the least
		// signifigant
		// byte in the smallest adress)...
		src.order(ByteOrder.LITTLE_ENDIAN);

		// If 16 bit, convert to ShortBuffers, then put src to result as
		// ShortBuffers.
		if (is16bit)
		{
			ShortBuffer result_short = result.asShortBuffer();
			ShortBuffer src_short = src.asShortBuffer();
			while (src_short.hasRemaining())
				result_short.put(src_short.get());
		} else
			while (src.hasRemaining())
				result.put(src.get());

		// Rewind result...
		result.rewind();

		return result;
	}
}
