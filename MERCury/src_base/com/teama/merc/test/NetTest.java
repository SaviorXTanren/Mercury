package com.teama.merc.test;

import org.lwjgl.input.Keyboard;

import com.esotericsoftware.kryonet.Connection;
import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.in.Input;
import com.teama.merc.kryonet.MercClient;
import com.teama.merc.kryonet.MercServer;
import com.teama.merc.kryonet.Network;
import com.teama.merc.res.ResourceManager;
import com.teama.merc.spl.SplashScreen;

/**
 * @from merc in com.teama.merc.test
 * @authors opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class NetTest extends Core
{
    
    Runner rnr = Runner.getInstance();
    
    MercServer server;
    MercClient client;
    
    public static Circle mob;
    
    public NetTest()
    {
        super("NetTest");
        rnr.init(this, 800, 600, false, false);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        server = new MercServer(8192, 8193);
        server.createServer();
        server.registerObject(Packet.class);
        
        client = new MercClient(8192, 8193);
        client.createClient();
        client.connectToServer("localhost");
        client.addNetwork(new NetworkListener());
        client.registerObject(Packet.class);
        
        mob = new Circle(400, 300);
        
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta)
    {
        Input in = rnr.getInput();
        if (in.keyDown(Keyboard.KEY_W))
        {
            Packet p = new Packet();
            p.y = -4;
            server.sendTCP(client.getID(), p);
        }
        if (in.keyDown(Keyboard.KEY_S))
        {
            Packet p = new Packet();
            p.y = 4;
            server.sendTCP(client.getID(), p);
        }
        if (in.keyDown(Keyboard.KEY_A))
        {
            Packet p = new Packet();
            p.x = -4;
            server.sendTCP(client.getID(), p);
        }
        if (in.keyDown(Keyboard.KEY_D))
        {
            Packet p = new Packet();
            p.x = 4;
            server.sendTCP(client.getID(), p);
        }
        if (in.keyDown(Keyboard.KEY_ESCAPE))
        {
            cleanup(rnr.getResourceManager());
            rnr.end();
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        mob.render(g);
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
        server.stop();
        client.stop();
    }
    
    public static void main(String[] args)
    {
        new NetTest();
    }
    
    private class Circle
    {
        
        public int x, y;
        
        public Circle(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        public void render(Graphics g)
        {
            g.drawCircle(x, y, 50);
        }
    }
    
    static class Packet
    {
        public int x, y;
    }
    
    private class NetworkListener extends Network
    {
        @Override
        public void received(Connection connection, Object object)
        {
            System.out.println("Received: " + connection.getRemoteAddressTCP());
            if (object instanceof Packet)
            {
                System.out.println(((Packet) object).x + " , " + ((Packet) object).y);
                mob.x += ((Packet) object).x;
                mob.y += ((Packet) object).y;
            }
        }
    }
    
}
