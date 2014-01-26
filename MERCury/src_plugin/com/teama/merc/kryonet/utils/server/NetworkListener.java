package com.teama.merc.kryonet.utils.server;

import com.esotericsoftware.kryonet.Connection;
import com.teama.merc.kryonet.MercServer;
import com.teama.merc.kryonet.Network;
import com.teama.merc.kryonet.utils.packets.Packet;

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

public class NetworkListener extends Network
{

	MercServer server;
	GenericServer gServer;

	public NetworkListener(MercServer server, GenericServer gServer)
	{
		this.server = server;
		this.gServer = gServer;
	}

	@Override
	public void received(Connection connection, Object object)
	{
		if (object instanceof Packet)
		{
			switch (((Packet) object).type)
			{
				case 0:
					server.sendAllUDP(object);
					break;
				case 1:
					gServer.returnPacket((Packet) object); 
					break;
				case 2:
					break;
				case 3:
					server.sendUDPExcept(connection.getID(), object);
					break;
			}
		}

	}
}
