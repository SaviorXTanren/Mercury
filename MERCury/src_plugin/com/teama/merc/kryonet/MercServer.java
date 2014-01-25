package com.teama.merc.kryonet;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

/**
 * @from merc in com.teama.merc.net
 * @authors opiop65
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class MercServer
{
    
    private Server server;
    private int tcpPort, udpPort;
    private boolean serverCreated, serverOpened;
    
    public MercServer(int tcpPort, int udpPort)
    {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }
    
    // Creates a new instance of the server
    public void createServer()
    {
        server = new Server();
        serverCreated = true;
        openServer();
    }
    
    // Starts and binds the server. Must be called after createServer()!
    public boolean openServer()
    {
        if (serverCreated)
        {
            server.start();
            try
            {
                server.bind(tcpPort, udpPort);
            } catch (IOException e)
            {
                e.printStackTrace();
                // add logging
                return false;
            }
        } else if (!serverCreated)
        {
            serverOpened = false;
            // add logging
            return false;
        }
        serverOpened = true;
        return true;
    }
    
    // Attempt to update the server
    public void update(int timeout)
    {
        try
        {
            server.update(timeout);
        } catch (IOException e)
        {
            // add logging
            e.printStackTrace();
        }
    }
    
    // Register the objects you want to send. Must be registered before being able to send. The Object also has to be static and cannot have a constructor!
    public void registerObject(Class<?> object)
    {
        getKryo().register(object);
    }
    
    // Send to all connected clients via TCP
    public void sendAllTCP(Object object)
    {
        server.sendToAllTCP(object);
    }
    
    // Send to all connected clients via UDP
    public void sendAllUDP(Object object)
    {
        server.sendToAllUDP(object);
    }
    
    // Send an object via TCP to specified client
    public void sendTCP(int id, Object object)
    {
        server.sendToTCP(id, object);
    }
    
    // Send an object via TCP to specified client
    public void sendTCP(MercClient client, Object object)
    {
        sendTCP(client.getID(), object);
    }
    
    // Send an object via UDP to specified client
    public void sendUDP(int id, Object object)
    {
        server.sendToUDP(id, object);
    }
    
    // Send an object via UDP to specified client
    public void sendUDP(MercClient client, Object object)
    {
        sendUDP(client.getID(), object);
    }
    
    // Send object via TCP to all clients except for specified client
    public void sendTCPExcept(int id, Object object)
    {
        server.sendToAllExceptTCP(id, object);
    }
    
    // Send object via TCP to all clients except for specified client
    public void sendTCPExcept(MercClient client, Object object)
    {
        sendTCPExcept(client.getID(), object);
    }
    
    // Send object via TCP to all clients except for specified client
    public void sendUDPExcept(int id, Object object)
    {
        server.sendToAllExceptUDP(id, object);
    }
    
    // Send object via UDP to all clients except for specified client
    public void sendUDPExcept(MercClient client, Object object)
    {
        sendUDPExcept(client.getID(), object);
    }
    
    // Add a ThreadedNetwork(QueuedListener).
    public void addThreadedNetwork(ThreadedNetwork network)
    {
        server.addListener(network);
    }
    
    // Remove the current ThreadedNetwork(QueuedListener).
    public void removeThreadedNetwork(Network network)
    {
        server.removeListener(network);
    }
    
    // Add a Network(Listener).
    public void addNetwork(Network network)
    {
        server.addListener(network);
    }
    
    // Remove the current Network(Listener).
    public void removeNetwork(Network network)
    {
        server.removeListener(network);
    }
    
    public Connection[] getClients()
    {
        return server.getConnections();
    }
    
    // Disconnect and stop the server.
    public void stop()
    {
        server.stop();
    }
    
    // Close the current connections.
    public void close()
    {
        server.close();
    }
    
    // Returns an instance of EndPoint inside the Server
    public EndPoint getServer()
    {
        if (!serverCreated)
        {
            // add logging
        }
        return server;
    }
    
    public int getTCPPort()
    {
        return tcpPort;
    }
    
    public int getUDPPort()
    {
        return udpPort;
    }
    
    // Returns an instance of Kryo from the Server
    public Kryo getKryo()
    {
        return getServer().getKryo();
    }
    
    // Is the server opened?
    public boolean isServerOpened()
    {
        return serverOpened;
    }
}
