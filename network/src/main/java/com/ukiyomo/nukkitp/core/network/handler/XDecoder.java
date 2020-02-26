package com.ukiyomo.nukkitp.core.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class XDecoder extends ByteToMessageDecoder {

    // 用来临时保留没有处理过的请求报文
    ByteBuf tempMsg = Unpooled.buffer();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int readLength = -1;
        ByteBuf message;
        int tmpMsgSize = tempMsg.readableBytes();

        // 如果暂存有上一次余下的请求报文，则合并
        if (tmpMsgSize > 0) {
            message = Unpooled.buffer();
            message.writeBytes(tempMsg);
            message.writeBytes(in);
        } else {
            message = in;
        }

        while (message.readableBytes() >= 4) {

            int packetLength = message.readInt();

            if(message.readableBytes() >= packetLength) {
                out.add(message.readBytes(packetLength));
            } else {

                // 剩余长度不足数据包长度
                readLength = packetLength;
                break;
            }
        }

        int size = message.readableBytes();
        if (size != 0) {
            tempMsg.clear();
            if(readLength != -1) {
                tempMsg.writeInt(readLength);
            }
            tempMsg.writeBytes(message.readBytes(size));
        }

    }
}
