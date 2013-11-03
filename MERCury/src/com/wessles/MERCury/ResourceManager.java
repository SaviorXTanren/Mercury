package com.wessles.MERCury;

import java.util.HashMap;

import kuusisto.tinysound.Sound;

import com.wessles.MERCury.opengl.Animation;
import com.wessles.MERCury.opengl.Font;
import com.wessles.MERCury.opengl.Shader;
import com.wessles.MERCury.opengl.Texture;

/**
 * An object that will hold, handle, and load all resources, so that one
 * resource will only have one instance.
 * 
 * With this class, you may load an object; loading means that you will input a
 * resource, and it will be stored with a given key.
 * 
 * You may also get an object; getting it means that you give the key and it
 * returns the resource(s) associated with that key.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class ResourceManager {
	private final HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private final HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private final HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	private final HashMap<String, Shader> shaders = new HashMap<String, Shader>();
	private final HashMap<String, Font> fonts = new HashMap<String, Font>();

	public void loadTexture(Texture texture, String name) {
		textures.put(name, texture);
	}

	public void loadTextures(Texture[] textures, String name) {
		for (int t = 0; t < textures.length; t++) {
			this.textures.put(name + "_" + t, textures[t]);
		}
	}

	public void loadTextures(Texture[][] textures, String name) {
		for (int x = 0; x < textures.length; x++) {
			for (int y = 0; y < textures.length; y++) {
				this.textures.put(name + "_" + x + "_" + y, textures[x][y]);
			}
		}
	}

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	public void loadAnimation(Animation animation, String name) {
		animations.put(name, animation);
	}

	public Animation getAnimation(String name) {
		return animations.get(name);
	}

	public void loadSound(Sound sound, String name) {
		sounds.put(name, sound);
	}

	public Sound getSound(String name) {
		return sounds.get(name);
	}

	public void loadFont(Font font, String name) {
		fonts.put(name, font);
	}

	public Font getFont(String name) {
		return fonts.get(name);
	}

	public void loadShader(Shader shader, String name) {
		shaders.put(name, shader);
	}

	public Shader getShader(String name) {
		return shaders.get(name);
	}

	public void cleanup() {
		sounds.clear();
		textures.clear();
		shaders.clear();
	}
}