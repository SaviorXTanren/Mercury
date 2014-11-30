package com.radirius.mercury.graphics;

import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.resource.Resource;

/**
 * An easy to use animation class. Just render, and watch the moving picture.
 *
 * @author wessles
 */
public class Animation implements Resource {

	private SpriteSheet baseTextures;

	private int frame = 0;
	private int first = 0, last = 0;
	private int framestep = 1;

	private boolean bounce;

	private int frameTimeMillis;
	private long frameMillis = 0, lastFrameMillis;

	/**
	 * @param frameTimeMillis The frame rate in milliseconds
	 * @param baseTextures    The textures, or frames.
	 */
	public Animation(SpriteSheet baseTextures, int frameTimeMillis) {
		this(baseTextures, frameTimeMillis, 0, baseTextures.getNumberOfSubTextures() - 1);
	}

	/**
	 * @param frameTimeMillis The frame rate in milliseconds
	 * @param baseTextures    The textures, or frames.
	 */
	public Animation(SpriteSheet baseTextures, int frameTimeMillis, boolean bounce) {
		this(frameTimeMillis, baseTextures, 0, baseTextures.getNumberOfSubTextures() - 1, bounce);
	}

	/**
	 * @param frameTimeMillis The frame rate in milliseconds
	 * @param baseTextures    The textures, or frames.
	 * @param startFrame      The first frame of the animation.
	 * @param endFrame        The last frame of the animation.
	 */
	public Animation(SpriteSheet baseTextures, int frameTimeMillis, int startFrame, int endFrame) {
		this(frameTimeMillis, baseTextures, startFrame, endFrame, false);
	}

	/**
	 * @param frameTimeMillis The frame rate in milliseconds
	 * @param baseTextures    The textures, or frames.
	 * @param startFrame      The first frame of the animation.
	 * @param endFrame        The last frame of the animation.
	 * @param bounce          Whether or not the animation should reverse once it gets to the end.
	 */
	public Animation(int frameTimeMillis, SpriteSheet baseTextures, int startFrame, int endFrame, boolean bounce) {
		this.frameTimeMillis = frameTimeMillis;
		this.baseTextures = baseTextures;

		frame = 0;

		if (startFrame < 0 || startFrame > baseTextures.getNumberOfSubTextures())
			throw new ArithmeticException("Invalid starting frame.");

		if (endFrame < 0 || endFrame > baseTextures.getNumberOfSubTextures())
			throw new ArithmeticException("Invalid ending frame.");

		first = startFrame;
		last = endFrame + 1;

		this.bounce = bounce;
	}

	/**
	 * Renders the current frame at x and y.
	 */
	public void render(float x, float y, Graphics g) {
		render(x, y, baseTextures.getTexture(frame).getWidth(), baseTextures.getTexture(0).getHeight(), g);
	}

	/**
	 * Renders the current frame at x and y, with a size of width w by height h.
	 */
	public void render(float x, float y, float w, float h, Graphics g) {
		render(new Rectangle(x, y, w, h), g);
	}

	public void render(Shape region, Graphics g) {
		g.drawTexture(getCurrentFrame(), region);
	}

	public void render(Shape sourceRegion, Shape region, Graphics g) {
		g.drawTexture(getCurrentFrame(), sourceRegion, region);
	}

	/**
	 * Useful if you want to render the frames in a special way.
	 *
	 * @return The current frame.
	 */
	public SubTexture getCurrentFrame() {
		return baseTextures.getTexture(frame);
	}

	/**
	 * Moves on to the next frame in the animation if the current frame has been around more than the frame-rate.
	 *
	 * @return Whether or not this is the last frame.
	 */
	public boolean passFrame() {
		frameMillis = System.currentTimeMillis();

		if (frameMillis - lastFrameMillis >= frameTimeMillis) {

			frame += framestep;

			if (!(frame + framestep <= last && frame + framestep >= 0))
				if (bounce)
					framestep *= -1;
				else
					frame = first;

			lastFrameMillis = System.currentTimeMillis();

			return frame == first;
		}

		return false;
	}

	/**
	 * @return The current frame.
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * Sets the current frame.
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}

	/**
	 * @return How many frames there are.
	 */
	public int getLength() {
		return last - first;
	}

	/**
	 * @return Whether or not we are at the last frame.
	 */
	public boolean isLastFrame() {
		return bounce ? frame == first : frame == last;
	}

	/**
	 * @return The textures for all the frames.
	 */
	public SpriteSheet getTextures() {
		return baseTextures;
	}

	@Override
	public void clean() {
		baseTextures.clean();
	}
}
