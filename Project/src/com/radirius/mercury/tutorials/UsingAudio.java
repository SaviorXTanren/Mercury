package com.radirius.mercury.tutorials;

import com.radirius.mercury.audio.Audio;
import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.CoreSetup;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.resource.Loader;

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

	Audio cgop;

	@Override
	public void init() {
		cgop = Audio.getAudio(Loader.getResourceAsStream("com/radirius/mercury/tutorials/CG-OP.ogg"), "OGG");

		Input.setRepeatEventsEnabled(false);
	}

	@Override
	public void update() {
		if (Input.keyClicked(Input.KEY_SPACE))
			cgop.play();
		else if (Input.keyClicked(Input.KEY_LCONTROL))
			cgop.pause();
		else if (Input.keyClicked(Input.KEY_ESCAPE))
			cgop.stop();

		// Toggle looping
		if (Input.keyDown(Input.KEY_L))
			cgop.setLooping(!cgop.isLooping());

		// Amplify / deamplify the volume
		if (Input.keyDown(Input.KEY_UP))
			cgop.setVolume(cgop.getVolume() + 0.01f);
		else if (Input.keyDown(Input.KEY_DOWN))
			cgop.setVolume(cgop.getVolume() - 0.01f);

		// Raise / lower the pitch
		if (Input.keyDown(Input.KEY_LEFT))
			cgop.setPitch(cgop.getPitch() + 0.01f);
		else if (Input.keyDown(Input.KEY_RIGHT))
			cgop.setPitch(cgop.getPitch() - 0.01f);
	}

	@Override
	public void render(Graphics g) {}

	@Override
	public void cleanup() {}
}
