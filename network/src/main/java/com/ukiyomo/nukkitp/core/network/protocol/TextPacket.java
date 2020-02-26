package com.ukiyomo.nukkitp.core.network.protocol;

import com.ukiyomo.nukkitp.core.network.utils.Binary;
import io.netty.buffer.ByteBuf;

public class TextPacket extends DataPacket {

    public final static short DATA_PACKET_ID = 1;

    public String message;

    @Override
    public short pid() {
        return TextPacket.DATA_PACKET_ID;
    }

    public void encode(ByteBuf byteBuf) {
        Binary.writeString(byteBuf, this.message);
    }

    public void decode(ByteBuf byteBuf) {
        this.message = Binary.readString(byteBuf);
    }
}
