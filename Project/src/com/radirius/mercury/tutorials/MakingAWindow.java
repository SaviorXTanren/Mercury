package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;

/**
 * @author wessles
 */
public class MakingAWindow extends Core {
	public MakingAWindow(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Making a Window");
		coreSetup.width = 800;
		coreSetup.height = 600;

		MakingAWindow makingAWindow = new MakingAWindow(coreSetup);
		makingAWindow.start();
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void cleanup() {
	}
}
