package com.ukiyomo.nukkitp.core.network.test.listener;


import com.ukiyomo.nukkitp.core.network.NetworkServer;
import com.ukiyomo.nukkitp.core.network.anno.DataPacketEventHandler;
import com.ukiyomo.nukkitp.core.network.event.DataPacketEventContext;
import com.ukiyomo.nukkitp.core.network.event.listener.DataPacketEventListener;
import com.ukiyomo.nukkitp.core.network.protocol.TextPacket;

public class ServerListener implements DataPacketEventListener {

    private NetworkServer networkServer;

    public ServerListener() {
    }

    public ServerListener(NetworkServer networkServer) {
        this.networkServer = networkServer;
    }

    @DataPacketEventHandler(TextPacket.class)
    public void test(DataPacketEventContext ctx) {

        TextPacket packet = (TextPacket) ctx.dataPacket;

        System.out.println(packet.runtimeId + " : " + packet.message);

        TextPacket packet1 = new TextPacket();
        packet1.message = "hello, 你好";

        networkServer.replyDataPacket(ctx.channel, packet.runtimeId, packet1);
    }

}
