package com.ukiyomo.nukkitp.core.network;


import com.ukiyomo.nukkitp.core.network.handler.RouterChannelHandler;
import com.ukiyomo.nukkitp.core.network.handler.XDecoder;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkClient extends Network {

    private EventLoopGroup workerGroup;
    private Channel channel;

    private String host;
    private int port;

    private NetworkClient() {
    }



    public static Builder Builder() {
        return new Builder();
    }

    @Override
    protected void init() {

        this.workerGroup = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();

            bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new XDecoder());
                        socketChannel.pipeline().addLast(new RouterChannelHandler(NetworkClient.this));
                    }
                });

            ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).sync();
            this.channel = channelFuture.channel();
            //this.channelFuture.channel().closeFuture();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        this.workerGroup.shutdownGracefully();
    }

    public void sendDataPacket(DataPacket dataPacket, DataPacketCallback callback) {
        this.dataPacket(this.channel, this.runtimeId++, this.alias, dataPacket, callback);
    }

    public void replyDataPacket(long runtimeId, DataPacket dataPacket) {
        this.dataPacket(this.channel, runtimeId, this.alias, dataPacket, null);
    }

    public static class Builder {

        private NetworkClient networkClient = new NetworkClient();

        public Builder host(String val) {
            networkClient.host = val;
            return this;
        }

        public Builder port(int val) {
            networkClient.port = val;
            return this;
        }

        public Builder alias(String val) {
            if(!"".equals(val)) {
                networkClient.alias = val;
            } else {
                networkClient.alias = "client" + System.currentTimeMillis();
            }

            return this;
        }

        public NetworkClient build() {
            networkClient.init();
            return networkClient;
        }
    }

}
