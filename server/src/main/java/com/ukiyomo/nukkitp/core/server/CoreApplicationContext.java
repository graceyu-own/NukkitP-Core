package com.ukiyomo.nukkitp.core.server;


import com.ukiyomo.nukkitp.core.server.command.CommandManager;
import com.ukiyomo.nukkitp.core.server.network.NetworkManager;
import com.ukiyomo.nukkitp.core.server.plugin.PluginManager;

public class CoreApplicationContext {

    private PluginManager pluginManager;
    private CommandManager commandManager;
    private NetworkManager networkManager;

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private CoreApplicationContext context = new CoreApplicationContext();

        public Builder pluginManager(PluginManager val) {
            context.pluginManager = val;
            return this;
        }

        public Builder commandManager(CommandManager val) {
            context.commandManager = val;
            return this;
        }

        public Builder networkManager(NetworkManager val) {
            context.networkManager = val;
            return this;
        }


        public CoreApplicationContext build() {
            return context;
        }

    }

}
