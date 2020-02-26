package com.ukiyomo.nukkitp.core.network.test;

import com.ukiyomo.nukkitp.core.network.NetworkClient;
import com.ukiyomo.nukkitp.core.network.protocol.TextPacket;
import com.ukiyomo.nukkitp.core.network.test.listener.ClientListener;



public class ClientTest {

    public static void main(String[] args) throws InterruptedException {

        NetworkClient client = NetworkClient.Builder()
                .host("localhost")
                .port(9000)
                .build();

        client.getDataPacketManager().registerDataPacketEventListener(new ClientListener());
        
        System.out.println("enter");
        TextPacket textPacket = new TextPacket();
        textPacket.message = "qwe";

        client.sendDataPacket(textPacket, (dp) -> {
            if(dp instanceof TextPacket) {
                System.out.println("数据包收到回复: " + ((TextPacket) dp).message);
            }
        });



    }

}
