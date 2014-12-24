package com.radirius.mercury.graphics;

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
	 * Useful if you want to render the frames in a special way. Returns The current frame.
	 */
	public SubTexture getCurrentFrame() {
		return baseTextures.getTexture(frame);
	}

	/**
	 * Moves on to the next frame in the animation if the current frame has been around more than the frame-rate.
	 * Returns Whether or not this is the last frame.
	 */
	public boolean passFrame() {
		frameMillis = System.currentTimeMillis();

		if (frameMillis - lastFrameMillis >= frameTimeMillis) {

			int maybeFrame = frame + framestep;

			if (maybeFrame >= last || maybeFrame < 0)
				if (bounce)
					framestep *= -1;
				else
					frame = first;

			frame += framestep;

			lastFrameMillis = System.currentTimeMillis();

			return frame == first;
		}

		return false;
	}

	/**
	 * Returns The current frame.
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
	 * Returns How many frames there are.
	 */
	public int getLength() {
		return last - first;
	}

	/**
	 * Returns Whether or not we are at the last frame.
	 */
	public boolean isLastFrame() {
		return bounce ? frame == first : frame == last;
	}

	/**
	 * Returns The textures for all the frames.
	 */
	public SpriteSheet getTextures() {
		return baseTextures;
	}

	@Override
	public void clean() {
		baseTextures.clean();
	}
}
