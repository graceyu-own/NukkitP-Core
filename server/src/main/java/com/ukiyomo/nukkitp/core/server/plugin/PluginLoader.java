package com.ukiyomo.nukkitp.core.server.plugin;

import java.io.File;

public interface PluginLoader {

    Plugin loadPlugin(File file) throws Exception;

    PluginInfo getPluginInfo(File file);

    void enablePlugin(Plugin plugin);

    void disablePlugin(Plugin plugin);

}
