package com.ukiyomo.nukkitp.core.network.test;

import com.ukiyomo.nukkitp.core.network.NetworkServer;
import com.ukiyomo.nukkitp.core.network.test.listener.ServerListener;

import java.util.Scanner;


public class  ServerTest {

    public static void main(String[] args) {

        NetworkServer server = NetworkServer.Builder()
            .port(9000)
            .build();

        System.out.println("注册监听器");
        server.getDataPacketManager().registerDataPacketEventListener(new ServerListener(server));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }

}
