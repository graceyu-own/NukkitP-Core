package com.ukiyomo.nukkitp.core.network.utils;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

public final class Binary {

    public static void writeString(ByteBuf byteBuf, String source) {
        if(null == source) source = "";
        byte[] bytes = source.getBytes(CharsetUtil.UTF_8);
        writeByteArray(byteBuf, bytes);
    }

    public static String readString(ByteBuf byteBuf) {
        return new String(readByteArray(byteBuf), CharsetUtil.UTF_8);
    }

    private static void writeByteArray(ByteBuf byteBuf, byte[] bytes) {
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    private static byte[] readByteArray(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readInt()];
        byteBuf.readBytes(bytes);

        return bytes;
    }




}
