package us.radiri.merc.test;

import java.util.ArrayList;

import us.radiri.merc.cmd.Command;
import us.radiri.merc.cmd.CommandList;
import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.geo.Vec2;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Texture;
import us.radiri.merc.gui.Button;
import us.radiri.merc.gui.TextBar;
import us.radiri.merc.gui.TextBox;
import us.radiri.merc.math.MercMath;
import us.radiri.merc.res.Loader;

/**
 * @author Jeviny
 */

public class GUITest extends Core {
    Runner heart = Runner.getInstance();
    
    public GUITest() {
        super("MERCury GUI Test (NSFL, u mite kry)");
        
        heart.init(this, 1024, 768);
        heart.run();
    }
    
    public static void main(String[] a) {
        new GUITest();
    }
    
    TextBox tp;
    Texture dAWWWW, panel, tbl, tbr, tbm;
    String msg = "", finalmsg = "A WILD dAWWWW HAS APPEARED! \n\n 'I LOVE YOU AND YOU WILL BE MY BEST FRIEND! WE SHALL TAKE LONG STROLLS IN THE PARK, AND THEN I WILL CONSUME YOU. IT WILL NOT BE PLEASANT, BUT I CAN ASSURE YOU IT WILL BE FOR ME.' \n\n :D :D :D :D :D :D :D :D";
    
    TextBar tbartest;
    Button btn_screamforhelp;
    
    ArrayList<Vec2> screamsforhelp = new ArrayList<Vec2>();
    
    @Override
    public void init() {
        dAWWWW = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
        panel = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/panel_main.png"));
        tp = new TextBox("", new Rectangle(10, 50, 450, 330), 30, panel, TrueTypeFont.OPENSANS_REGULAR, Color.blue, Color.black);
        
        tbl = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/button_left.png"));
        tbr = Texture.loadTexture(tbl.getSourceImage(), true, false);
        tbm = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/button_body.png"));
        
        tbartest = new TextBar("Sir, you are in danger! Please confirm you are alive:       ", tbl, tbr, tbm, 100, 600);
        btn_screamforhelp = new Button("Scream For Help (C) (TM) (R) Button", tbl, tbr, tbm, 100, 605 + tbartest.bounds.getHeight(), Color.white, Color.blue) {
            @Override
            public void act() {
                backgroundcolor = Color.cyan;
                screamsforhelp.add(new Vec2((float) MercMath.random(0, Runner.getInstance().getWidth()), (float) MercMath.random(0, Runner.getInstance().getHeight())));
            }
            
            @Override
            public void noAct() {
                backgroundcolor = Color.blue;
            }
        };
        
        CommandList cmdl = new CommandList("guitest", "A command list for tinkering with the GUI test.");
        cmdl.addCommand(new Command("set_textures", "This will set the text bar and box to have textures or not with an argument boolean.") {
            @Override
            public void run(String... args) {
                if (Boolean.valueOf(args[0])) {
                    tp = new TextBox("", new Rectangle(10, 50, 450, 330), 30, panel, TrueTypeFont.OPENSANS_REGULAR, Color.blue, Color.trans);
                    tbartest = new TextBar("Sir, you are in danger! Please confirm you are alive:       ", tbl, tbr, tbm, 100, 600);
                } else {
                    tp = new TextBox("", new Rectangle(10, 50, 450, 330));
                    tbartest = new TextBar("Sir, you are in danger! Please confirm you are alive:       ", 100, 600);
                }
                msg = "";
            }
        });
        CommandList.addCommandList(cmdl);
        
        heart.getGraphics().setBackground(Color.gray);
    }
    
    @Override
    public void update(float delta) {
        char nextchar = Runner.getInstance().getInput().getNextCharacter();
        tbartest.content += nextchar != 0 ? nextchar : "";
        btn_screamforhelp.update();
    }
    
    Rectangle bounds = new Rectangle(600, 100, 256);
    
    @Override
    public void render(Graphics g) {
        for (Vec2 v : screamsforhelp)
            g.drawString(v.x, v.y, "HELP!");
        
        try {
            msg += finalmsg.charAt(msg.length());
            tp.setContent(msg);
        } catch (Exception e) {
            
        }
        
        bounds.rotate(1);
        g.drawTexture(dAWWWW, bounds);
        
        tbartest.render(g);
        
        tp.render(g);
        btn_screamforhelp.render(g);
    }
    
    @Override
    public void cleanup() {
        
    }
}