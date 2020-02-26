package com.ukiyomo.nukkitp.core.network.event.executors;

import com.ukiyomo.nukkitp.core.network.event.listener.DataPacketEventListener;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.channel.Channel;

/**
 * 数据包事件执行器接口
 */
public interface DataPacketEventExecutor {

    void execute(DataPacketEventListener listener, Channel channel, DataPacket dataPacket);

}
