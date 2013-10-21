package com.wessles.MERCury.opengl;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

/**
 * An object version of a texture. This will store the width and height of the object.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Texture {
	public static final int BYTES_PER_PIXEL = 4;

	private final int textureid, width, height;

	public Texture(int textureid, int width, int height) {
		this.textureid = textureid;
		this.width = width;
		this.height = height;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureid);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void delete() {
		glDeleteTextures(textureid);
	}

	public int getTextureWidth() {
		return width;
	}

	public int getTextureHeight() {
		return height;
	}

	public int getTextureId() {
		return textureid;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Texture) {
			Texture other = (Texture) obj;
			if (other.getTextureHeight() == getTextureHeight() && other.getTextureWidth() == getTextureWidth() && other.getTextureId() == getTextureId())
				return true;
		}
		return false;
	}

	public static void bindTexture(Texture tex) {
		tex.bind();
	}

	public static void unbindTextures() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public static Texture loadTexture(String location) throws IOException {
		return loadTexture(location, false, false, GL_NEAREST);
	}

	public static Texture loadTexture(BufferedImage bi) {
		return loadTexture(bi, false, false);
	}

	public static Texture loadTexture(String location, boolean fliphor, boolean flipvert) throws IOException {
		return loadTexture(location, fliphor, flipvert, GL_NEAREST);
	}

	public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert) {
		return loadTexture(bi, fliphor, flipvert, GL_NEAREST);
	}

	public static Texture loadTexture(String location, int filter) throws IOException {
		return loadTexture(location, false, false, filter);
	}

	public static Texture loadTexture(BufferedImage bi, int filter) {
		return loadTexture(bi, false, false, filter);
	}

	public static Texture loadTexture(String location, boolean fliphor, boolean flipvert, int filter) throws IOException {
		return loadTexture(ImageIO.read(new FileInputStream(location)), fliphor, flipvert, filter);
	}

	public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int filter) {
		int[][] pixels = new int[bi.getWidth()][bi.getHeight()];

		for (int y = 0; y < bi.getHeight(); y++)
			for (int x = 0; x < bi.getWidth(); x++)
				pixels[x][y] = bi.getRGB(x, y);

		ByteBuffer buffer = BufferUtils.createByteBuffer(bi.getWidth() * bi.getHeight() * BYTES_PER_PIXEL);

		for (int y = (flipvert ? (bi.getHeight() - 1) : 0); (flipvert ? (y > -1) : (y < bi.getHeight())); y += (flipvert ? -1 : 1)) {
			for (int x = (fliphor ? (bi.getWidth() - 1) : 0); (fliphor ? (x > -1) : (x < bi.getWidth())); x += (fliphor ? -1 : 1)) {
				int pixel = pixels[x][y];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		int textureid = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureid);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bi.getWidth(), bi.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return new Texture(textureid, bi.getWidth(), bi.getHeight());
	}

	public static Texture getEmptyTexture() {
		return new Texture(0, 0, 0);
	}
}