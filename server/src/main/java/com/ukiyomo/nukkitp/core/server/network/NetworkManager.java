package com.ukiyomo.nukkitp.core.server.network;


import com.ukiyomo.nukkitp.core.network.DataPacketManager;
import com.ukiyomo.nukkitp.core.network.NetworkServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetworkManager {

    private List<NetworkServer> networkServers = new ArrayList<>();
    private int balancedIndex = 0;
    private DataPacketManager dataPacketManager;

    public NetworkManager(NetworkServer ...networkServers) {
        this.dataPacketManager = new DataPacketManager();
        for (NetworkServer networkServer : networkServers) {
            this.addNetworkServer(networkServer);
        }
    }

    public void addNetworkServer(NetworkServer networkServer) {
        this.syncNetworkServer(networkServer);
        this.networkServers.add(networkServer);
    }

    public void syncNetworkServer(NetworkServer networkServer) {
        try {
            networkServer.setDataPacketManager((DataPacketManager) this.dataPacketManager.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void syncNetworkServers() {
        for (NetworkServer networkServer : this.networkServers) {
            this.syncNetworkServer(networkServer);
        }
    }

    public DataPacketManager getDataPacketManager() {
        return dataPacketManager;
    }

    public List<NetworkServer> getNetworkServers() {
        return this.networkServers;
    }

    public int getNetworkServerNumber() {
        return this.networkServers.size();
    }

    public NetworkServer getRandomNetworkServer() {
        Random r = new Random();
        return this.networkServers.get(r.nextInt(this.getNetworkServerNumber()));
    }

    public NetworkServer getBalancedNetworkServer() {
        return this.networkServers.get(balancedIndex++ % this.getNetworkServerNumber());
    }

    public void destroy() {
        for (NetworkServer networkServer : this.networkServers) {
            networkServer.destroy();
        }
    }
}
