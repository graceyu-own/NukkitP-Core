package com.ukiyomo.nukkitp.core.server.plugin;

public interface Plugin {

    void onLoad();

    void onEnable();

    void onDisable();

    boolean isEnabled();

    PluginInfo getPluginInfo();

}
