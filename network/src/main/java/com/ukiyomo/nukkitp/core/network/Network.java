package com.ukiyomo.nukkitp.core.network;

import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public abstract class Network{

    protected String alias;

    // 数据包回调 -> Map<数据包runtimeId, 回调方法>
    private Map<Long, DataPacketCallback> callbacks = new HashMap<>();

    // 数据包runtimeId, 每次发送数据包都会自增(Reply除外)
    protected long runtimeId = 0;

    // 数据包管理器
    protected DataPacketManager dataPacketManager;

    public Network setDataPacketManager(DataPacketManager dataPacketManager) {
        this.dataPacketManager = dataPacketManager;
        return this;
    }

    abstract protected void init();
    abstract protected void destroy();

    public String getAlias() {
        return alias;
    }

    public DataPacketManager getDataPacketManager() {
        return dataPacketManager;
    }

    public DataPacketCallback getCallback(long runtimeId) {
        return this.callbacks.get(runtimeId);
    }

    public void delCallback(long runtimeId) {
        this.callbacks.remove(runtimeId);
    }

    /**
     * 发送数据包
     *
     * @param channel 网络通道
     * @param runtimeId 数据包runtimeId
     * @param senderAlias 发送者别名
     * @param dataPacket 数据包实例
     * @param callback 回调方法
     */
    protected void dataPacket(Channel channel, long runtimeId, String senderAlias, DataPacket dataPacket, DataPacketCallback callback) {

        // 连接失效
        if(!channel.isActive()) {
            return;
        }

        // 先加码数据包内容
        dataPacket.runtimeId = runtimeId;
        dataPacket.senderAlias = senderAlias;
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byteBuf.writeShort(dataPacket.pid());
        dataPacket.tryEncode(byteBuf);

        // 在数据包内容前面加上包长度
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer();
        byteBuf1.writeInt(byteBuf.readableBytes());
        byteBuf1.writeBytes(byteBuf);

        // 判断此数据包有没有回调方法
        if(null != callback) {
            this.callbacks.put(runtimeId, callback);
        }

        // 发送数据包
        channel.writeAndFlush(byteBuf1);
    }

}
