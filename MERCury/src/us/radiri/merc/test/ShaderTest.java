package us.radiri.merc.test;

import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Shader;
import us.radiri.merc.gfx.Texture;
import us.radiri.merc.res.Loader;
import us.radiri.merc.spl.SplashScreen;

/**
 * An object version of shaders. Does all of the tedius stuff for you and lets
 * you use the shader easily.
 * 
 * @author opiop65
 */

public class ShaderTest extends Core {
    
    Runner rnr = Runner.getInstance();
    Shader program;
    
    public ShaderTest() {
        super("MERCury Shader Test");
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    Texture tex;
    
    @Override
    public void init() {
        // rnr.getGraphics().scale(4);
        
        program = Shader.getShader(Loader.streamFromClasspath("us/radiri/merc/test/custom.vs"), Shader.VERTEX_SHADER);
        tex = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/torch.png"));
        
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        g.setBackground(Color.red);
        
        g.useShader(program);
        g.drawTexture(tex, 0, 0);
        g.drawRect(new Rectangle(100, 100, 50, 50));
        g.releaseShaders();
        
        g.drawString(0, 0, "Do not worry\nDo not fear\nObey\nKNEEEL\n(but srsly m8s this is normla -m777777");
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new ShaderTest();
    }
}
