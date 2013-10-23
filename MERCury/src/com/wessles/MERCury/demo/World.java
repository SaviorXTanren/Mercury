package com.wessles.MERCury.demo;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.utils.Random;

/**
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public class World {
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static Player player;
	static {
		entities.add(new Background(0, 0));
		entities.add(new Background(-600, 0));
		
		player = new Player();
		entities.add(player);
	}
	
	public static void update(float delta) {
		for(int e = 0; e < entities.size(); e++) {
			Entity ent = entities.get(e);
			
			if(ent instanceof Comet) {
				Comet comet = (Comet) ent;
				if(comet.dead)
					entities.remove(e);
			}
			
			ent.update(delta);
		}
		if(Random.chance(.01f))
			spawnComet();
		
		Background.allStep();
	}
	
	public static void render(Graphics g) {
		for(Entity ent : entities)
			ent.render(g);
	}
	
	public static void spawnComet() {
		entities.add(new Comet(Random.getRandom(8, Display.getWidth()/2-8), Random.getRandom(8, 50), Vector2f.get(0, .5f)));
	}
}
