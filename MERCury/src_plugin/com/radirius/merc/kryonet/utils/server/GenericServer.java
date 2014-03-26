package com.radirius.merc.kryonet.utils.server;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.radirius.merc.kryonet.MercServer;
import com.radirius.merc.kryonet.utils.packets.Packet;
import com.radirius.merc.kryonet.utils.packets.PacketMessage;

/**
 * An object version of shaders. Does all of the tedius stuff for you and lets
 * you use the shader easily.
 * 
 * @from MERCury in com.teama.merc.net.utils
 * @authors opiop65
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class GenericServer
{
	public static final int RELAY = 0, RETURN = 1, DISCARD = 2, RELAYNOSENDBACK = 3;

	MercServer server;
	List<Class<?>> packets;
	private int udp, tcp;

	public GenericServer(int udp, int tcp)
	{
		this.udp = udp;
		this.tcp = tcp;
	}

	public void registerObject(Class<?>... object)
	{
		packets = new ArrayList<Class<?>>();

		for (Class<?> obj : object)
		{
			packets.add(obj);
		}
	}

	public void init()
	{
		server = new MercServer(udp, tcp);
		server.createServer();

		for (int i = 0; i < packets.size(); i++)
		{
			server.registerObject(packets.get(i));
		}

		server.addNetwork(new NetworkListener(server, this));
		System.out.println("Server started!");
	}

	public void send(Connection connection, Object object)
	{
		server.sendUDP(connection.getID(), object);
	}

	public void sendAll(Object object)
	{
		server.sendAllUDP(object);
	}
	
	public void sendAllExcept(Connection connection, Object object)
	{
		server.sendUDPExcept(connection.getID(), object);
	}
	
	public void disconnect()
	{
		PacketMessage message = new PacketMessage();
		message.message = "Server disconnecting!";
		server.sendAllUDP(message);
		
		server.close();
	}
	
	public void closeServer()
	{
		disconnect();
		server.stop();
	}

	public Packet returnPacket(Packet packet)
	{
		return packet;
	}
}
