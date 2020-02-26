package com.ukiyomo.nukkitp.core.network.event;


import com.ukiyomo.nukkitp.core.network.event.executors.DataPacketEventExecutor;
import com.ukiyomo.nukkitp.core.network.event.listener.DataPacketEventListener;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.channel.Channel;

/**
 * 注册的监听器, 此类属于中间件, 用于数据包事件监听器和数据包事件执行器沟通的主要类
 */
public class RegisteredListener {

    private DataPacketEventListener listener;
    private DataPacketEventExecutor executor;

    public RegisteredListener(DataPacketEventListener listener, DataPacketEventExecutor executor) {
        this.listener = listener;
        this.executor = executor;
    }

    public void callDataPacketEvent(Channel channel, DataPacket dataPacket) {
        this.executor.execute(this.listener, channel, dataPacket);
    }
}
