package com.radirius.merc.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

/**
 * @from merc in com.teama.merc.net
 * @authors opiop65
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class ThreadedNetwork extends ThreadedListener
{

    // All the methods in this class will be seperately threaded through the
    // QueuedListener in the Kryonet library.

    // Requires a Listener from the Kryonet package, probably need to update it
    // to reference from the Network.java from the MERCury engine.
    public ThreadedNetwork(Listener listener)
    {
        super(listener);
    }

    @Override
    public void connected(Connection connection)
    {
    }

    // Called when a remote client is disconnected.
    @Override
    public void disconnected(Connection connection)
    {
    }

    // Called when an object is received from a remote client. This method will
    // not block any threads by design.
    @Override
    public void received(Connection connection, Object object)
    {
    }

    // Called when the connection is below the idle threshold.
    @Override
    public void idle(Connection connection)
    {
    }

}
