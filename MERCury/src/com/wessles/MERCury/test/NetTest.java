package com.wessles.MERCury.test;

import com.esotericsoftware.kryonet.Connection;
import com.wessles.MERCury.net.MercClient;
import com.wessles.MERCury.net.MercServer;
import com.wessles.MERCury.net.Network;

/**
 * @from MERCury in com.wessles.MERCury.test
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class NetTest {

	public static void main(String[] args) {
		MercServer server = new MercServer(8192, 8193);
		server.createServer();
		server.openServer();
		
		MercClient client = new MercClient(8192, 8193);
		client.createClient();
		client.openclient();
		client.addNetwork(new Network(){
			public void received(Connection connection, Object object){
				System.out.println("Message recieved!");
			}
		});
		client.connectToServer("localhost");
		
		String message = "Hello World!";
		server.sendAllTCP(message);
	}
}
