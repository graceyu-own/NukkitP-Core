package com.ukiyomo.nukkitp.core.server;

import com.ukiyomo.nukkitp.core.network.NetworkServer;
import com.ukiyomo.nukkitp.core.server.command.CommandManager;
import com.ukiyomo.nukkitp.core.server.network.NetworkManager;
import com.ukiyomo.nukkitp.core.server.plugin.PluginManager;
import org.springframework.boot.SpringApplication;


public final class CoreApplication {

    private CoreApplicationContext ctx = null;

    public CoreApplication() { }

    private void init() {

        this.ctx = CoreApplicationContext.Builder()
            .commandManager(new CommandManager())
            .networkManager(new NetworkManager(
                NetworkServer.Builder().port(9000).alias("server1").build()
            ))
            .pluginManager(new PluginManager(this))
            .build();
    }

    public static CoreApplication getInstance() {
        return ServerInstance.INSTANCE;
    }

    public CoreApplicationContext getCorePluginContext() {
        return this.ctx;
    }

    public void shutdown() {
        this.ctx.getNetworkManager().destroy();
        this.ctx.getPluginManager().destroy();
        System.exit(0);
    }


    private static class ServerInstance {
        public final static CoreApplication INSTANCE = new CoreApplication();
    }

    public static void main(String[] params) {

        CoreApplication.getInstance().init();
        SpringApplication.run(CoreBootApplication.class, params);

    }
}
