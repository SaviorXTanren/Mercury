package com.radirius.test;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;

public class TestGeometry extends Core {
	public TestGeometry() {
		super("Geometry", 1280, 720);
	}
	
	public void init() {
		
	}
	
	public void update(float delta) {
		
	}
	
	public void render(Graphics g) {
		g.setBackground(Color.ASPHALT);
	}
	
	public void cleanup() {
		
	}
	
	public static void main(String[] args) {
		new TestGeometry().start();
	}
}
