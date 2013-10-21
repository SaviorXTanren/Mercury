package com.wessles.MERCury.demo;

import java.util.ArrayList;

import com.wessles.MERCury.opengl.Graphics;

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
		for(Entity ent : entities)
			ent.update(delta);
		Background.allStep();
	}
	
	public static void render(Graphics g) {
		for(Entity ent : entities)
			ent.render(g);
	}
}
