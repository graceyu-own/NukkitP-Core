package com.ukiyomo.nukkitp.core.network.handler;

import com.ukiyomo.nukkitp.core.network.NetworkServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerTempChannelSaveHandler extends ChannelInboundHandlerAdapter {

    private NetworkServer networkServer;

    public ServerTempChannelSaveHandler(NetworkServer networkServer) {
        this.networkServer = networkServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        if(!networkServer.tempChannels.containsKey(address)) {
            networkServer.tempChannels.put(address, ctx.channel());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        networkServer.tempChannels.remove(address);
    }
}
