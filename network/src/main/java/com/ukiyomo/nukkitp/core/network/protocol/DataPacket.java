package com.ukiyomo.nukkitp.core.network.protocol;

import com.ukiyomo.nukkitp.core.network.utils.Binary;
import io.netty.buffer.ByteBuf;

public abstract class DataPacket implements Cloneable {

    public final static short DATA_PACKET_ID = 1;

    public long runtimeId = 0;
    public String senderAlias;

    abstract public short pid();

    public void tryEncode(ByteBuf byteBuf) {
        byteBuf.writeLong(this.runtimeId);
        Binary.writeString(byteBuf, this.senderAlias);
        this.encode(byteBuf);
    }

    abstract public void encode(ByteBuf byteBuf);

    public void tryDecode(ByteBuf byteBuf) {
        this.runtimeId = byteBuf.readLong();
        this.senderAlias = Binary.readString(byteBuf);
        this.decode(byteBuf);
    }

    abstract public void decode(ByteBuf byteBuf);

}
