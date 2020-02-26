package com.ukiyomo.nukkitp.core.network.event;

import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.channel.Channel;

/**
 * 数据包事件上下文
 */
public class DataPacketEventContext implements Cloneable {

    public Channel channel;
    public DataPacket dataPacket;

    public DataPacketEventContext(Channel channel, DataPacket dataPacket) {
        this.channel = channel;
        this.dataPacket = dataPacket;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
