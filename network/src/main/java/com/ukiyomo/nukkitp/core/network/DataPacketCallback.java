package com.ukiyomo.nukkitp.core.network;


import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;

public interface DataPacketCallback {

    void execute(DataPacket dataPacket);

}
