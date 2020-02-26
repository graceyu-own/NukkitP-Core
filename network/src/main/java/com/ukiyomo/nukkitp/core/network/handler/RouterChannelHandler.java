package com.ukiyomo.nukkitp.core.network.handler;


import com.ukiyomo.nukkitp.core.network.DataPacketCallback;
import com.ukiyomo.nukkitp.core.network.Network;
import com.ukiyomo.nukkitp.core.network.event.RegisteredListener;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class RouterChannelHandler extends ChannelInboundHandlerAdapter {

    private Network network;

    public RouterChannelHandler() {
    }

    public RouterChannelHandler(Network network) {
        this.network = network;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf) msg;
        short pid = byteBuf.readShort();

        // 开启taskQueue 异步执行操作
        ctx.channel().eventLoop().execute(() -> {

            try {
                // 获得数据包的id
                Class<? extends DataPacket> dataPacketClass = this.network.getDataPacketManager().getDataPacket(pid);

                // 检查数据包是否注册
                if(null != dataPacketClass) {

                    // 如果注册则实例化并且解码
                    DataPacket dataPacket = dataPacketClass.newInstance();
                    dataPacket.tryDecode(byteBuf);

                    // 拿到数据包的回调方法
                    DataPacketCallback callback = this.network.getCallback(dataPacket.runtimeId);

                    // 检查数据包是否注册了回调
                    if(null != callback) {

                        // 使用回调消费
                        callback.execute(dataPacket);
                        this.network.delCallback(dataPacket.runtimeId);
                    } else {

                        // 使用监听器方法消费
                        List<RegisteredListener> registeredListener = this.network.getDataPacketManager().getRegisteredListener(pid);
                        if(null != registeredListener) {
                            for (RegisteredListener listener : registeredListener) {
                                listener.callDataPacketEvent(ctx.channel(), dataPacket);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }
}
