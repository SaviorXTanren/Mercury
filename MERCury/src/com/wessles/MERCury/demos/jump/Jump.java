package com.wessles.MERCury.demos.jump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import kuusisto.tinysound.TinySound;

import com.wessles.MERCury.Core;
import com.wessles.MERCury.ResourceManager;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.maths.Random;
import com.wessles.MERCury.opengl.Animation;
import com.wessles.MERCury.opengl.Color;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.utils.TextureFactory;

/**
 * @from Jump
 * @author wessles
 * @website www.wessles.com
 */
public class Jump extends Core {
	Timer timer;
	Player p;
	Background bg;
	CenterPoint cp;
	
	Color cpc = Color.yellow;
	
	CopyOnWriteArrayList<Key> keys;

	public void init(ResourceManager RM) {
		try {

			RM.loadTexture(Texture.loadTexture("res/backdrop.png"), "backdrop_im");
			RM.loadTexture(TextureFactory.getTextureStrip("res/centerpoint.png", 16)[0], "cpoint_im");
			RM.loadTextures(TextureFactory.getTextureStrip("res/keys.png", 8), "key");
			RM.loadAnimation(Animation.loadAnimationFromStrip("res/centerpoint.png", 16, 150), "cpoint_anm");
			RM.loadAnimation(Animation.loadAnimationFromStrip("res/skalk_strip.png", 8, 300), "skalk_anm");
			RM.loadSound(TinySound.loadSound(new File("res/disco.wav")), "disco_snd");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		RM.getSound("disco_snd").play();

		this.p = new Player();
		this.bg = new Background();
		this.cp = new CenterPoint();
		this.keys = new CopyOnWriteArrayList<Key>();
		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				cp.catchKey();
				keys.add(new Key());
			}
		}, 2000, 1500);
	}

	public void render(Graphics g) {
		renderGame(g);
		
		for (Key k : keys)
			k.render(g);
	}

	public void update(float delta) {
		delta *= .5f;

		p.update(delta);
		bg.update(delta);
		cp.update(delta);

		for (Key k : keys) {
			if(k.dead)
				keys.remove(k);
			k.update(delta);
		}
		
		if(Runner.getInput().keyClicked(Keyboard.KEY_UP) || Runner.getInput().keyClicked(Keyboard.KEY_RIGHT) || Runner.getInput().keyClicked(Keyboard.KEY_DOWN) || Runner.getInput().keyClicked(Keyboard.KEY_LEFT))
			switch((int)Random.getRandom(0, 4)) {
				case 0: cpc = Color.blue;
				case 1: cpc = Color.yellow;
				case 2: cpc = Color.red;
				case 3: cpc = Color.purple;
			}
	}

	public void cleanup(ResourceManager RM) {
		timer.cancel();
		RM.cleanup();
	}

	private void renderGame(Graphics g) {
		bg.render(g);
		p.render(g);
		g.setColor((cpc==null) ? Color.white : cpc);
		cp.render(g);
		g.setColor(Color.white);
	}

	public static void main(String[] args) {
		new Jump();
	}
}
