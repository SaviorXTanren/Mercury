package com.teama.merc.net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * @from MERCury in com.wessles.MERCury.net
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class MercClient
{
    
    private Client client;
    private int tcpPort, udpPort;
    private boolean clientCreated, clientOpened;
    
    public MercClient(int tcpPort, int udpPort)
    {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }
    
    // Creates a new instance of the client
    public void createClient()
    {
        client = new Client();
        clientCreated = true;
        openClient();
    }
    
    // Starts and binds the client. Must be called after createclient()!
    public boolean openClient()
    {
        if (clientCreated)
            client.start();
        else if (!clientCreated)
        {
            clientOpened = false;
            // add logging
            return false;
        }
        clientOpened = true;
        return true;
    }
    
    // Try connecting to server for 5 seconds (5000 / 1000) with specified IP
    // and ports
    public void connectToServer(String IP)
    {
        connectToServer(5000, IP, tcpPort, udpPort);
    }
    
    public void connectToServer(int timeout, String IP)
    {
        connectToServer(timeout, IP, tcpPort, udpPort);
    }
    
    // Try connecting to server. Must be run after server and client is created
    // if you want to send anything. Client must also be started by running
    // MercClient.openClient()
    public void connectToServer(int timeout, String IP, int tcpPort, int udpPort)
    {
        try
        {
            client.connect(timeout, IP, tcpPort, udpPort);
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
    
    public void sendTCP(Object object)
    {
        client.sendTCP(object);
    }
    
    public void sendUDP(Object object)
    {
        client.sendUDP(object);
    }
    
    // Add a ThreadedNetwork(QueuedListener).
    public void addThreadedNetwork(ThreadedNetwork network)
    {
        client.addListener(network);
    }
    
    // Remove the current ThreadedNetwork(QueuedListener).
    public void removeThreadedNetwork(Network network)
    {
        client.removeListener(network);
    }
    
    // Add a Network(Listener).
    public void addNetwork(Network network)
    {
        client.addListener(network);
    }
    
    // Remove the current Network(Listener).
    public void removeNetwork(Network network)
    {
        client.removeListener(network);
    }
    
    public void reconnectToServer()
    {
        reconnectToServer(5000);
    }
    
    // Attempt to reconnect to the server
    public void reconnectToServer(int timeout)
    {
        try
        {
            client.reconnect(timeout);
        } catch (IOException e)
        {
            // add logging
            e.printStackTrace();
        }
    }
    
    // Attempt to update the client.
    public void update(int timeout)
    {
        try
        {
            client.update(timeout);
        } catch (IOException e)
        {
            // add logging
            e.printStackTrace();
        }
    }
    
    // Stops the client and shuts it down.
    public void stop()
    {
        client.stop();
    }
    
    // Closes the connection to the server? Not entirely sure on this one.
    public void close()
    {
        client.close();
    }
    
    public int getTCPPort()
    {
        return tcpPort;
    }
    
    public int getUDPPort()
    {
        return udpPort;
    }
    
    public int getID()
    {
        return client.getID();
    }
    
    // Returns an instance of EndPoint inside the client
    public EndPoint getclient()
    {
        if (!clientCreated)
        {
            // add logging
        }
        return client;
    }
    
    // Returns an instance of Kryo from the client
    public Kryo getKryo()
    {
        return getclient().getKryo();
    }
    
    // Is the client opened?
    public boolean isClientOpened()
    {
        return clientOpened;
    }
    
}
