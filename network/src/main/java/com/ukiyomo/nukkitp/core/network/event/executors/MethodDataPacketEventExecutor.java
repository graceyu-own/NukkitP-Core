package com.ukiyomo.nukkitp.core.network.event.executors;


import com.ukiyomo.nukkitp.core.network.event.DataPacketEventContext;
import com.ukiyomo.nukkitp.core.network.event.listener.DataPacketEventListener;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 数据包事件执行器 - 方法执行器
 */
public class MethodDataPacketEventExecutor implements DataPacketEventExecutor {

    private final Method method;

    public MethodDataPacketEventExecutor(Method method) {
        this.method = method;
    }

    @Override
    public void execute(DataPacketEventListener listener, Channel channel, DataPacket dataPacket) {
        try {
            DataPacketEventContext context = new DataPacketEventContext(channel, dataPacket);
            this.method.invoke(listener, context);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
