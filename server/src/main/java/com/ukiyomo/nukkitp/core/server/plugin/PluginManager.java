package com.ukiyomo.nukkitp.core.server.plugin;

import com.ukiyomo.nukkitp.core.server.CoreApplication;
import com.ukiyomo.nukkitp.core.server.exception.PluginException;

import java.io.File;
import java.util.*;

public final class PluginManager {

    private PluginLoader pluginLoader;

    private Map<String, Plugin> plugins = new HashMap<>();

    public PluginManager(CoreApplication coreApplication) {
        this.pluginLoader = new DefaultPluginLoader(coreApplication);
        this.init();
    }

    /**
     * Load a plugin, only initial not enable
     *
     * @param file plugin jar file
     */
    public void loadPlugin(File file) {

        if (null != this.pluginLoader && null != this.pluginLoader.getPluginInfo(file)) {

            PluginInfo pluginInfo = this.pluginLoader.getPluginInfo(file);

            if(null != pluginInfo) {
                try {
                    Plugin plugin = this.pluginLoader.loadPlugin(file);
                    this.plugins.put(pluginInfo.getName(), plugin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load more plugins, only initial not enable
     *
     * @param files plugin jar files
     * @see #loadPlugin
     */
    public void loadPlugins(List<File> files) {
        for (File file : files) {
            this.loadPlugin(file);
        }
    }

    /**
     * Load more plugins, only initial not enable
     *
     * @param files plugin jar files
     * @see #loadPlugin
     */
    public void loadPlugins(File[] files) {
        for (File file : files) {
            this.loadPlugin(file);
        }
    }


    /**
     * Enable plugin, if the plugin is not initialized, the plugin cannot be found
     *
     * @param name plugin name, not is file name
     */
    public void enablePlugin(String name) {
        if(this.plugins.containsKey(name)) {
            Plugin plugin = this.plugins.get(name);
            if(plugin instanceof PluginBase) {
                for (String pluginDependency : plugin.getPluginInfo().getDependencies()) {
                    // dependency myself
                    if(name.equals(pluginDependency)) {
                        throw new PluginException("Can't rely on oneself.");
                    }
                    this.enablePlugin(pluginDependency);
                }
                ((PluginBase) plugin).setEnabled(true);
            }
        }
    }

    /**
     * Enable plugin, if the plugin is not initialized, the plugin cannot be found
     *
     * @param plugin plugin
     */
    public void enablePlugin(Plugin plugin) {
        if(plugin instanceof PluginBase) {
            this.enablePlugin(plugin.getPluginInfo().getName());
        }
    }

    /**
     * Disable plugin, if the plugin is not initialized, the plugin cannot be found
     *
     * @param plugin plugin
     */
    public void disablePlugin(Plugin plugin) {
        if(plugin instanceof PluginBase) {
            ((PluginBase) plugin).setEnabled(false);
        }
    }

    /**
     * Disable more plugins
     *
     * @param plugins plugins
     * @see #disablePlugin
     */
    public void disablePlugins(List<Plugin> plugins) {
        for (Plugin plugin : plugins) {
            this.disablePlugin(plugin);
        }
    }

    /**
     * PluginManager init method.
     */
    private void init() {

        String pluginsPath = CoreApplication.class.getResource("/").getPath() + "plugins/";
        List<File> files = new ArrayList<>();
        this.queryJars(pluginsPath, files);
        this.loadPlugins(files);
        for (Plugin plugin : this.plugins.values()) {
            this.enablePlugin(plugin);
        }
    }

    /**
     * PluginManager destroy method.
     */
    public void destroy() {
        this.plugins.forEach((k, v) -> this.disablePlugin(v));
    }

    /**
     * Query all jar files
     *
     * @param path query directory
     * @param result result list
     */
    private void queryJars(String path, List<File> result) {

        File file = new File(path);

        if(file.exists()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if(listFile.isDirectory()) {
                   this.queryJars(path + listFile.getName() + "/", result);
                } else {
                    if(listFile.getName().endsWith(".jar")) {
                        result.add(listFile);
                    }
                }
            }
        }
    }
}
