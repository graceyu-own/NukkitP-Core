package com.ukiyomo.nukkitp.core.network.test.listener;


import com.ukiyomo.nukkitp.core.network.anno.DataPacketEventHandler;
import com.ukiyomo.nukkitp.core.network.event.DataPacketEventContext;
import com.ukiyomo.nukkitp.core.network.event.listener.DataPacketEventListener;
import com.ukiyomo.nukkitp.core.network.protocol.TextPacket;

public class ClientListener implements DataPacketEventListener {

    @DataPacketEventHandler(TextPacket.class)
    public void test(DataPacketEventContext ctx) {


    }
}
