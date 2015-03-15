package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author Sri Harsha Chilakapati
 */
public class ShaderTest extends Core {
	private Shader shader;
	private Rectangle rect;

	public ShaderTest(CoreSetup setup) {
		super(setup);
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("Mercury Shader Test");

		new ShaderTest(setup).start();
	}

	@Override
	public void init() {
		// Fragment shader source
		String source = "#version 330 core                     \n" + "                                      \n" + "uniform sampler2D u_texture;          \n" + "uniform vec4      u_color;            \n" + "                                      \n" + "in vec4 v_color;                      \n" + "in vec4 v_texcoord;                   \n"
				+ "                                      \n" + "out vec4 fragColor;                   \n" + "                                      \n" + "void main() {                         \n" + "    fragColor = u_color;              \n" + "}";

		shader = Shader.getShader(source, Shader.FRAGMENT_SHADER);
		rect = new Rectangle(100, 100, 100, 100);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		// Use the shader
		shader.bind();
		shader.setUniformf("u_color", 1, 0, 0, 1); // Red color

		// Draw the rectangle
		g.drawFigure(rect);
	}

	@Override
	public void cleanup() {
		shader.clean();
	}
}
