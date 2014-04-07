package com.radirius.merc.tuts;

import java.io.IOException;

import com.radirius.merc.cmd.Command;
import com.radirius.merc.cmd.CommandList;
import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.geo.TexturedRectangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @from MERCury in com.radirius.merc.tuts
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class DrawingTextures extends Core
{
    public Runner rnr = Runner.getInstance();

    public DrawingTextures()
    {
        super("Drawing Textures!");
        rnr.init(this, 500, 500);
        rnr.run();
    }

    // Texture
    public Texture lard;
    // Geometric Texture (for rotation and stuffs)
    public TexturedRectangle geom_lard;

    // And hows about a rotation constant so that we can screw with the Dev
    // Console.
    public float rot = 1;

    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException
    {
        /* Let's make a red background, scaled up x6 */
        rnr.getGraphics().setBackground(Color.red);
        rnr.getGraphics().scale(4);

        /* Load in our Texture, as usual */
        RM.loadResource(Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/tuts/lard.png")), "tex_lard");
        lard = (Texture) RM.retrieveResource("tex_lard");
        geom_lard = new TexturedRectangle(new Rectangle(lard.getTextureWidth() * 2, lard.getTextureHeight() / 2, lard.getTextureWidth(), lard.getTextureHeight()), lard);

        /*
         * If you do not get this section, ignore it. It has to do the with the
         * magical Developers Console. All you need to know for now, is that you
         * can type in "lard setrotate 10" into the console (System.in) to set the rotation
         * variable to 10.
         */
        CommandList lardcmdl = new CommandList("lard");
        lardcmdl.addCommand(new Command("setrotate")
        {
            public void run(String... args)
            {
                rot = Float.valueOf(args[0]);
            }
        });
        CommandList.addCommandList(lardcmdl);
    }

    @Override
    public void update(float delta) throws MERCuryException
    {
    }

    @Override
    public void render(Graphics g) throws MERCuryException
    {
        /* Draw the actual texture; no size change, or deformation. */
        g.drawTexture(lard, 0, 0);

        /* Draw the texture at half size (squeesed, not cropped). */
        g.drawTexture(lard, lard.getTextureWidth(), 0, lard.getTextureWidth() / 2, lard.getTextureHeight() / 2);

        /* Draw the texture at half size (cropped, not squeesed). */
        g.drawTexture(lard, 0, 0, lard.getTextureWidth() / 2, lard.getTextureHeight() / 2, 0, lard.getTextureHeight());

        /* Draw the texture, but repeat it twice. */
        g.drawTexture(lard, 0, 0, lard.getTextureWidth() * 3, lard.getTextureHeight(), 0, lard.getTextureHeight() * 2);

        /*
         * Draw the texture, but rotate it slightly every render for a sickening
         * effect.
         */
        geom_lard.rotate(rot);
        g.drawRect(geom_lard);
    }

    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException
    {
    }

    public static void main(String[] args)
    {
        new DrawingTextures();
    }

}
