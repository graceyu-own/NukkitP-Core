package com.ukiyomo.nukkitp.core.client.network;


import com.ukiyomo.nukkitp.core.network.DataPacketManager;
import com.ukiyomo.nukkitp.core.network.NetworkClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetworkManager {

    private List<NetworkClient> networkClients = new ArrayList<>();
    private int balancedIndex = 0;
    private DataPacketManager dataPacketManager;

    public NetworkManager(NetworkClient ...networkClients) {
        this.dataPacketManager = new DataPacketManager();
        for (NetworkClient networkClient : networkClients) {
            this.addNetworkClient(networkClient);
        }
    }

    public void addNetworkClient(NetworkClient networkClient) {
        this.syncNetworkClient(networkClient);
        this.networkClients.add(networkClient);
    }

    public void syncNetworkClient(NetworkClient networkClient) {
        try {
            networkClient.setDataPacketManager((DataPacketManager) this.dataPacketManager.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void syncNetworkClients() {
        for (NetworkClient networkClient : this.networkClients) {
            this.syncNetworkClient(networkClient);
        }
    }

    public DataPacketManager getDataPacketManager() {
        return dataPacketManager;
    }

    public List<NetworkClient> getNetworkClients() {
        return this.networkClients;
    }

    public int getNetworkClientNumber() {
        return this.networkClients.size();
    }

    public NetworkClient getRandomNetworkClient() {
        Random r = new Random();
        return this.networkClients.get(r.nextInt(this.getNetworkClientNumber()));
    }

    public NetworkClient getBalancedNetworkClient() {
        return this.networkClients.get(balancedIndex++ % this.getNetworkClientNumber());
    }

    public void destroy() {
        for (NetworkClient networkClient : this.networkClients) {
            networkClient.destroy();
        }
    }

}
