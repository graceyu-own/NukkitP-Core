package com.ukiyomo.nukkitp.core.network.event.listener;


import com.ukiyomo.nukkitp.core.network.anno.DataPacketEventHandler;
import com.ukiyomo.nukkitp.core.network.event.DataPacketEventContext;
import com.ukiyomo.nukkitp.core.network.protocol.TextPacket;

/**
 * 一个简易的数据包事件监听器
 */
public class ReplyDataPacketEventListener implements DataPacketEventListener {

    @DataPacketEventHandler(TextPacket.class)
    public void textPacket(DataPacketEventContext ctx) {
        System.out.println(((TextPacket)ctx.dataPacket).message);
    }

}
