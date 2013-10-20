package com.wessles.MERCury.opengl;

import org.lwjgl.opengl.GL11;


/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Color {
	public static final Color red = new Color(255, 0, 0);
	public static final Color green = new Color(0, 255, 0);
	public static final Color blue = new Color(0, 0, 255);
	
	public static final Color yellow = new Color(255, 255, 0);
	public static final Color purple = new Color(255, 0, 255);
	public static final Color babyblue = new Color(0, 255, 255);
	
	public static final Color white = new Color(255, 255, 255);
	public static final Color black = new Color(0, 0, 0);
	
	public static final Color gray = new Color(128, 128, 128);
	public static final Color darkred = new Color(128, 0, 0);
	public static final Color darkgreen = new Color(0, 128, 0);
	public static final Color darkblue = new Color(0, 0, 128);
	public static final Color mustard = new Color(128, 128, 0);
	public static final Color darkpurple = new Color(128, 0, 128);
	public static final Color darkbabyblue = new Color(0, 128, 128);
	
	public float r=0, g=0, b=0, a=0;
	
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
	}
	
	public Color(float r, float g, float b, float a) {
		this(r, g, b);
		this.a = a;
	}
	
	public Color(int r, int g, int b) {
		this.r = r/255;
		this.g = g/255;
		this.b = b/255;
		this.a = 1;
	}
	
	public Color(int r, int g, int b, int a) {
		this.r = r/255;
		this.g = g/255;
		this.b = b/255;
		this.a = a/255;
	}
	
	public Color(int value) {
		this.r = (value & 0x00FF0000) >> 16;
		this.g = (value & 0x0000FF00) >> 8;
		this.b = (value & 0x000000FF);
		this.a = (value & 0xFF000000) >> 24;
		
		if(a < 0)
			a += 256;
		if(a == 0)
			a = 255;
		
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}
	
	public void bind() {
		GL11.glColor4f(r, g, b, a);
	}
	
	public void darken() {
		darken(.3f);
	}
	
	public void darken(float scale) {
		scale = 1-scale;
		r*=scale;
		g*=scale;
		b*=scale;
	}
	
	public void brighten() {
		brighten(.2f);
	}
	
	public void brighten(float scale) {
		scale++;
		r*=scale;
		g*=scale;
		b*=scale;
	}
	
	public int getRed() {
		return (int) r*255;
	}
	
	public int getGreen() {
		return (int) g*255;
	}
	
	public int getBlue() {
		return (int) b*255;
	}
	
	public int getAlpha() {
		return (int) a*255;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Color) {
			Color col = (Color) obj;
			if(col.r == r && col.g == g && col.b == b && col.a == a)
				return true;
		}
		return false;
	}
}
