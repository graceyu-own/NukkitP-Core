package com.ukiyomo.nukkitp.core.client;

import cn.nukkit.plugin.PluginBase;
import com.ukiyomo.nukkitp.core.client.command.CommandManager;
import com.ukiyomo.nukkitp.core.client.form.FormManager;
import com.ukiyomo.nukkitp.core.client.network.NetworkManager;
import com.ukiyomo.nukkitp.core.network.NetworkClient;


public class CorePlugin extends PluginBase {

    private static CorePlugin instance = null;
    private CorePluginContext ctx = null;

    @Override
    public void onLoad() {
        if(null == instance) {
            synchronized (this) {
                if(null == instance) {
                    instance = this;
                }
            }
        }

        this.ctx = CorePluginContext.Builder()
            .commandManager(new CommandManager())
            .networkManager(new NetworkManager(
                NetworkClient.Builder().host("localhost").port(9000).alias("client1").build()
            ))
            .formManager(new FormManager())
            .build();
    }

    public static CorePlugin getInstance() {
        return instance;
    }

    public CorePluginContext getCorePluginContext() {
        return this.ctx;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        this.ctx.getNetworkManager().destroy();
    }

}
