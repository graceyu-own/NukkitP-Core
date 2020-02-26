package com.ukiyomo.nukkitp.core.network;


import com.ukiyomo.nukkitp.core.network.handler.RouterChannelHandler;
import com.ukiyomo.nukkitp.core.network.handler.ServerTempChannelSaveHandler;
import com.ukiyomo.nukkitp.core.network.handler.XDecoder;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

public class NetworkServer extends Network {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    public Map<String, Channel> tempChannels;

    private int port;

    private NetworkServer() {
    }

    public static Builder Builder() {
        return new Builder();
    }

    @Override
    protected void init() {

        this.tempChannels = new HashMap<>();
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new XDecoder());
                        socketChannel.pipeline().addLast(new ServerTempChannelSaveHandler(NetworkServer.this));
                        socketChannel.pipeline().addLast(new RouterChannelHandler(NetworkServer.this));
                    }
                });

            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
            this.channel = channelFuture.channel();
            //this.channelFuture.channel().closeFuture();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }

    public void sendDataPacket(Channel channel, DataPacket dataPacket, DataPacketCallback callback) {
        this.dataPacket(channel, this.runtimeId++, this.alias, dataPacket, callback);
    }

    public void sendDataPacketAll(DataPacket dataPacket, DataPacketCallback callback) {
        for (Channel channel : this.tempChannels.values()) {
            this.sendDataPacket(channel, dataPacket, callback);
        }
    }

    public void replyDataPacket(Channel channel, long runtimeId, DataPacket dataPacket) {
        this.dataPacket(channel, runtimeId, this.alias, dataPacket, null);
    }

    public static class Builder {

        private NetworkServer networkServer = new NetworkServer();

        public Builder port(int val) {
            networkServer.port = val;
            return this;
        }

        public Builder alias(String val) {
            if(!"".equals(val)) {
                networkServer.alias = val;
            } else {
                networkServer.alias = "server" + System.currentTimeMillis();
            }

            return this;
        }

        public NetworkServer build() {
            networkServer.init();
            return networkServer;
        }
    }

}
