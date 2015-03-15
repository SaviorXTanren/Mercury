package com.radirius.mercury.tutorials;

import com.radirius.mercury.audio.Audio;
import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.resource.*;

/**
 * @author wessles
 */
public class UsingAudio extends Core {
	public UsingAudio(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Using Audio");
		coreSetup.width = 800;
		coreSetup.height = 600;

		UsingAudio usingAudio = new UsingAudio(coreSetup);
		usingAudio.start();
	}

	Audio opening;

	@Override
	public void init() {
		Loader.pushLocation(new ClasspathLocation());
		opening = Audio.getAudio(Loader.getResourceAsStream("com/radirius/mercury/tutorials/CG-OP.ogg"), "OGG");
		Loader.popLocation();

		Input.setRepeatEventsEnabled(false);
	}

	@Override
	public void update() {
		if (Input.keyClicked(Input.KEY_SPACE))
			opening.play();
		else if (Input.keyClicked(Input.KEY_LCONTROL))
			opening.pause();
		else if (Input.keyClicked(Input.KEY_ESCAPE))
			opening.stop();

		// Toggle looping
		if (Input.keyDown(Input.KEY_L))
			opening.setLooping(!opening.isLooping());

		// Amplify / de-amplify the volume
		if (Input.keyDown(Input.KEY_UP))
			opening.setVolume(opening.getVolume() + 0.01f);
		else if (Input.keyDown(Input.KEY_DOWN))
			opening.setVolume(opening.getVolume() - 0.01f);

		// Raise / lower the pitch
		if (Input.keyDown(Input.KEY_LEFT))
			opening.setPitch(opening.getPitch() + 0.01f);
		else if (Input.keyDown(Input.KEY_RIGHT))
			opening.setPitch(opening.getPitch() - 0.01f);
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void cleanup() {
	}
}
