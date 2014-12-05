package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Shader;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author Sri Harsha Chilakapati
 */
public class ShaderTest extends Core {
    private Shader    shader;
    private Rectangle rect;

    private String source = "#version 330 core                     \n" +
                            "                                      \n" +
                            "uniform sampler2D u_texture;          \n" +
                            "uniform vec4      u_color;            \n" +
                            "                                      \n" +
                            "in vec4 v_color;                      \n" +
                            "in vec4 v_texcoord;                   \n" +
                            "                                      \n" +
                            "out vec4 fragColor;                   \n" +
                            "                                      \n" +
                            "void main() {                         \n" +
                            "    fragColor = u_color;              \n" +
                            "}";

    public ShaderTest() {
        super("Mercury ShaderTest");
    }

    @Override
    public void init() {
        shader = Shader.getShader(source, Shader.FRAGMENT_SHADER);
        rect   = new Rectangle(100, 100, 100, 100);
    }

    @Override
    public void update() { }

    @Override
    public void render(Graphics g) {
        shader.use();
        shader.setUniformf("u_color", 1, 0, 0, 1); // Red color
        g.drawRectangle(rect);
    }

    @Override
    public void cleanup() {
        shader.clean();
    }

    public static void main(String[] args) {
        new ShaderTest().start();
    }
}
