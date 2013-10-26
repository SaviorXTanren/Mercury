package com.wessles.MERCury.opengl;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.wessles.MERCury.utils.TextureFactory;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class BitmapFont implements Font {
	private int width;
	private int height;
	private int size;
	private Texture[][] textures;
	
	public BitmapFont(Texture[][] textures) {
		this.textures = textures;
		this.width = textures[0][0].getTextureWidth();
		this.height = textures[0][0].getTextureHeight();
		this.size = (width > height) ? width : height;
	}
	
	public BitmapFont(Texture[][] textures, int size) {
		this.textures = textures;
		this.size = size;
		this.width = size;
		this.height = size;
	}
	
	public int getCharWidth() {
		return width;
	}
	
	public int getCharHeight() {
		return height;
	}
	
	public int getSize() {
		return size;
	}
	
	public Texture[][] getTextures() {
		return textures;
	}
	
	public static BitmapFont loadFont(String location, int divwidth, int divheight) throws FileNotFoundException, IOException {
		BufferedImage tm = ImageIO.read(new FileInputStream(location));
		for(int x = 0; x < tm.getWidth(); x++)
			for(int y = 0; y < tm.getHeight(); y++) {
				java.awt.Color c = new java.awt.Color(tm.getRGB(x, y));
				if(c.getRed() <= 10 && c.getGreen() <= 10 && c.getBlue() <= 10)
					tm.setRGB(x, y, new java.awt.Color(0, 0, 0, 0).getRGB());
			}
		
		return new BitmapFont(TextureFactory.getTextureGrid(tm, divwidth, divheight));
	}
}
