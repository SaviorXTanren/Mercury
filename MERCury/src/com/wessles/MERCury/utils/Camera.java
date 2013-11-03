package com.wessles.MERCury.utils;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.opengl.Graphics;

/**
 * @from Horde
 * @author wessles
 * @website www.wessles.com
 */
public class Camera {
	private float x, y;

	private Vector2f snap;
	private Vector2f relsnap;
	private boolean snapped = false;

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
		relsnap = Vector2f.get(0, 0);
	}

	public void pre(Graphics g) {
		if (snap != null) {
			snapped = true;

			x = snap.x;
			y = snap.y;
		} else {
			snapped = false;
		}

		glPushMatrix();
		glTranslatef(-x + relsnap.x, -y + relsnap.y, 0);

		g.pre();
	}

	public void post(Graphics g) {
		g.post();

		glPopMatrix();
	}

	public void snapTo(Vector2f snap) {
		this.snap = snap;
	}

	public void setSnapRelativeToScreen(Vector2f relsnap) {
		this.relsnap = relsnap;
	}

	public Vector2f getSnap() {
		return snap;
	}

	public boolean snapped() {
		return snapped;
	}

	public float getOffsetX() {
		return x;
	}

	public float getOffsetY() {
		return y;
	}

	public Vector2f getOffset() {
		return Vector2f.get(x, y);
	}
}
