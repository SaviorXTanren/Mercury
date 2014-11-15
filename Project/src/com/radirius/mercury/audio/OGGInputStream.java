/*
Copyright (c) 2013, Slick2D

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the Slick2D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.radirius.mercury.audio;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import com.radirius.mercury.utilities.logging.Logger;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An input stream that can extract ogg data. This class is
 * a bit of an experiment with continuations so uses thread
 * where possibly not required. It's just a test to see if
 * continuations make sense in some cases.
 *
 * @author Kevin Glass
 */
public class OGGInputStream extends InputStream implements AudioInputStream {
    /**
     * Temporary scratch buffer
     */
    byte[] buffer;
    /**
     * The number of bytes read
     */
    int bytes = 0;
    // 8k
    // out
    // of
    // the
    // data
    // segment.
    /**
     * The true if we should be reading big endian
     */
    boolean bigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
    /**
     * True if we're reached the end of the current bit
     * stream
     */
    boolean endOfBitStream = true;
    // all the static
    // bitstream
    // settings.
    /**
     * True if we're initialize the OGG info block
     */
    boolean initialized = false;
    /**
     * The conversion buffer size
     */
    private int convsize = 4096 * 4;
    // and
    // verify
    // incoming
    // physical
    // bitstream.
    /**
     * The buffer used to read OGG file
     */
    private byte[] convbuffer = new byte[convsize]; // take
    // physical
    // pages,
    // weld
    // into
    // logical
    // stream
    // of
    // packets.
    /**
     * The stream we're reading the OGG file from
     */
    private InputStream input;
    // page. Vorbis packets
    // are inside.
    /**
     * The audio information from the OGG header
     */
    private Info oggInfo = new Info(); // struct that stores
    // of data for
    // decode.
    /**
     * True if we're at the end of the available data
     */
    private boolean endOfStream;
    // stores
    // all the
    // bitstream
    // user
    // comments.
    /**
     * The Vorbis SyncState used to decode the OGG
     */
    private SyncState syncState = new SyncState(); // sync
    // working
    // state for
    // the
    // packet->PCM
    // Decoder.
    /**
     * The Vorbis Stream State used to decode the OGG
     */
    private StreamState streamState = new StreamState(); // take
    // working
    // space
    // for
    // packet->PCM
    // decode.
    /**
     * The current OGG page
     */
    private Page page = new Page(); // one OGG bitstream
    /**
     * The current packet page
     */
    private Packet packet = new Packet(); // one raw packet
    /**
     * The comment read from the OGG file
     */
    private Comment comment = new Comment(); // struct that
    /**
     * The Vorbis DSP stat used to decode the OGG
     */
    private DspState dspState = new DspState(); // central
    /**
     * The OGG block we're currently working with to convert
     * PCM
     */
    private Block vorbisBlock = new Block(dspState); // local
    /**
     * The index into the byte array we currently read from
     */
    private int readIndex;

    /**
     * The byte array store used to hold the data read from
     * the OGG
     */
    private ByteBuffer pcmBuffer = BufferUtils.createByteBuffer(4096 * 500);

    /**
     * The total number of bytes
     */
    private int total;

    /**
     * Create a new stream to decode OGG data
     *
     * @param input The input stream from which to read the
     *              OGG file
     * @throws IOException Indicates a failure to read from the
     *                     supplied stream
     */
    public OGGInputStream(InputStream input) throws IOException {
        this.input = input;

        total = input.available();

        init();
    }

    /**
     * Get the number of bytes on the stream
     *
     * @return The number of the bytes on the stream
     */
    public int getLength() {
        return total;
    }

    @Override
    public int getChannels() {
        return oggInfo.channels;
    }

    @Override
    public int getRate() {
        return oggInfo.rate;
    }

    /**
     * Initialize the streams and thread involved in the
     * streaming of OGG data
     *
     * @throws IOException Indicates a failure to link up the
     *                     streams
     */
    private void init() throws IOException {
        initVorbis();
        readPCM();
    }

    @Override
    public int available() {
        return endOfStream ? 0 : 1;
    }

    /**
     * Initialize the vorbis decoding
     */
    private void initVorbis() {
        syncState.init();
    }

    /**
     * Get a page and packet from that page
     *
     * @return True if there was a page available
     */
    private boolean getPageAndPacket() {
        // grab some data at the head of the stream. We want
        // the first page
        // (which is guaranteed to be small and only contain
        // the Vorbis
        // stream initial header) We need the first page to
        // get the stream
        // serialno.

        // submit a 4k block to libvorbis' OGG layer
        int index = syncState.buffer(4096);

        buffer = syncState.data;
        if (buffer == null) {
            endOfStream = true;
            return false;
        }

        try {
            bytes = input.read(buffer, index, 4096);
        } catch (Exception e) {
            Logger.warn("Failure reading in vorbis");
            e.printStackTrace();
            endOfStream = true;
            return false;
        }
        syncState.wrote(bytes);

        if (syncState.pageout(page) != 1) {
            if (bytes < 4096)
                return false;

            Logger.warn("Input does not appear to be an OGG bitstream.");

            endOfStream = true;

            return false;
        }

        streamState.init(page.serialno());

        oggInfo.init();
        comment.init();

        if (streamState.pagein(page) < 0) {
            Logger.warn("Error reading first page of OGG bitstream data.");

            endOfStream = true;

            return false;
        }

        if (streamState.packetout(packet) != 1) {
            Logger.warn("Error reading initial header packet.");

            endOfStream = true;

            return false;
        }

        if (oggInfo.synthesis_headerin(comment, packet) < 0) {
            Logger.warn("This OGG bitstream does not contain Vorbis audio data.");

            endOfStream = true;

            return false;
        }

        int i = 0;

        while (i < 2) {
            while (i < 2) {
                int result = syncState.pageout(page);

                if (result == 0)
                    break;

                if (result == 1) {
                    streamState.pagein(page);

                    while (i < 2) {
                        result = streamState.packetout(packet);

                        if (result == 0)
                            break;

                        if (result == -1) {
                            Logger.warn("Corrupt secondary header. Exiting.");

                            endOfStream = true;

                            return false;
                        }

                        oggInfo.synthesis_headerin(comment, packet);

                        i++;
                    }
                }
            }

            index = syncState.buffer(4096);

            buffer = syncState.data;

            try {
                bytes = input.read(buffer, index, 4096);
            } catch (Exception e) {
                Logger.warn("Failed to read Vorbis: ");
                e.printStackTrace();

                endOfStream = true;

                return false;
            }

            if (bytes == 0 && i < 2) {
                Logger.warn("End of file before finding all Vorbis headers!");

                endOfStream = true;

                return false;
            }

            syncState.wrote(bytes);
        }

        convsize = 4096 / oggInfo.channels;

        dspState.synthesis_init(oggInfo);
        vorbisBlock.init(dspState);

        return true;
    }

    /**
     * Decode the OGG file as shown in the jogg/jorbis
     * examples
     *
     * @throws IOException Indicates a failure to read from the
     *                     supplied stream
     */
    private void readPCM() throws IOException {
        boolean wrote = false;

        while (true) {
            if (endOfBitStream) {
                if (!getPageAndPacket())
                    break;

                endOfBitStream = false;
            }

            if (!initialized) {
                initialized = true;

                return;
            }

            float[][][] _pcm = new float[1][][];
            int[] _index = new int[oggInfo.channels];

            while (!endOfBitStream) {
                while (!endOfBitStream) {
                    int result = syncState.pageout(page);

                    if (result == 0)
                        break;

                    if (result == -1)
                        Logger.warn("Corrupt or missing data in bitstream; continuing...");
                    else {
                        streamState.pagein(page);

                        while (true) {
                            result = streamState.packetout(packet);

                            if (result == 0)
                                break;
                            if (result == -1)
                                continue;
                            else {
                                int samples;

                                if (vorbisBlock.synthesis(packet) == 0)
                                    dspState.synthesis_blockin(vorbisBlock);

                                while ((samples = dspState.synthesis_pcmout(_pcm, _index)) > 0) {
                                    float[][] pcm = _pcm[0];
                                    int bout = samples < convsize ? samples : convsize;

                                    for (int i = 0; i < oggInfo.channels; i++) {
                                        int ptr = i * 2;
                                        int mono = _index[i];
                                        for (int j = 0; j < bout; j++) {
                                            int val = (int) (pcm[i][mono + j] * 32767.);
                                            if (val > 32767)
                                                val = 32767;

                                            if (val < -32768)
                                                val = -32768;

                                            if (val < 0)
                                                val = val | 0x8000;

                                            if (bigEndian) {
                                                convbuffer[ptr] = (byte) (val >>> 8);
                                                convbuffer[ptr + 1] = (byte) val;
                                            } else {
                                                convbuffer[ptr] = (byte) val;
                                                convbuffer[ptr + 1] = (byte) (val >>> 8);
                                            }

                                            ptr += 2 * oggInfo.channels;
                                        }
                                    }

                                    int bytesToWrite = 2 * oggInfo.channels * bout;

                                    if (bytesToWrite >= pcmBuffer.remaining())
                                        Logger.warn("Read block from OGG that was too big to be buffered: " + bytesToWrite);
                                    else
                                        pcmBuffer.put(convbuffer, 0, bytesToWrite);

                                    wrote = true;

                                    dspState.synthesis_read(bout);
                                }
                            }
                        }

                        if (page.eos() != 0)
                            endOfBitStream = true;

                        if (!endOfBitStream && wrote)
                            return;
                    }
                }

                if (!endOfBitStream) {
                    bytes = 0;

                    int index = syncState.buffer(4096);

                    if (index >= 0) {
                        buffer = syncState.data;

                        try {
                            bytes = input.read(buffer, index, 4096);
                        } catch (Exception e) {
                            Logger.warn("Failure during vorbis decoding");
                            e.printStackTrace();

                            endOfStream = true;

                            return;
                        }
                    } else
                        bytes = 0;

                    syncState.wrote(bytes);

                    // ^ This might break. Contact Jev if
                    // this breaks.

                    if (bytes == 0)
                        endOfBitStream = true;
                }
            }

            streamState.clear();

            vorbisBlock.clear();
            dspState.clear();
            oggInfo.clear();
        }

        syncState.clear();

        endOfStream = true;
    }

    @Override
    public int read() throws IOException {
        if (readIndex >= pcmBuffer.position()) {
            pcmBuffer.clear();

            readPCM();

            readIndex = 0;
        }

        if (readIndex >= pcmBuffer.position())
            return -1;

        int value = pcmBuffer.get(readIndex);

        if (value < 0)
            value = 256 + value;

        readIndex++;

        return value;
    }

    /**
     * @see org.newdawn.slick.openal.AudioInputStream#atEnd()
     */
    @Override
    public boolean atEnd() {
        return endOfStream && readIndex >= pcmBuffer.position();
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; i++)
            try {
                int value = read();
                if (value >= 0)
                    b[i] = (byte) value;
                else if (i == 0)
                    return -1;
                else
                    return i;
            } catch (IOException e) {
                e.printStackTrace();
                return i;
            }

        return len;
    }

    /**
     * @see java.io.InputStream#read(byte[])
     */
    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {
    }
}
