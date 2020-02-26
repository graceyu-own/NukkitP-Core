package com.ukiyomo.nukkitp.core.server.plugin;

import com.ukiyomo.nukkitp.core.server.CoreApplication;
import com.ukiyomo.nukkitp.core.server.exception.PluginException;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DefaultPluginLoader implements PluginLoader {

    private CoreApplication coreApplication;
    private Map<String, ClassLoader> classLoaders = new HashMap<>();

    public DefaultPluginLoader(CoreApplication coreApplication) {
        this.coreApplication = coreApplication;
    }

    @Override
    public Plugin loadPlugin(File file) throws Exception {

        PluginInfo pluginInfo = this.getPluginInfo(file);

        if(null != pluginInfo) {

            String mainClass = pluginInfo.getMain();
            PluginClassLoader loader = new PluginClassLoader(this, this.getClass().getClassLoader(), file);

            try {
                Class<?> classes = loader.loadClass(mainClass);
                if(!PluginBase.class.isAssignableFrom(classes)) {
                    throw new PluginException("Cannot load plugin: " + pluginInfo.getName() + ", the main class must extend PluginBase!");
                }

                if(classLoaders.containsKey(pluginInfo.getName())) {
                    throw new PluginException("This plugin already loaded!");
                }

                try {
                    Class<? extends PluginBase> base = classes.asSubclass(PluginBase.class);
                    PluginBase pluginBase = base.newInstance();
                    pluginBase.init(coreApplication, pluginInfo);
                    this.classLoaders.put(pluginInfo.getName(), loader);

                    return pluginBase;

                } catch (ClassCastException e) {
                    throw new PluginException("Error whilst initializing main class `" + pluginInfo.getName() + "'", e);
                } catch (IllegalAccessException | InstantiationException other) {
                    other.fillInStackTrace();
                }

            } catch (ClassNotFoundException e) {
                throw new PluginException("Couldn't load plugin " + pluginInfo.getName() + ": main class not found");
            }
        }

        return null;
    }

    @Override
    public PluginInfo getPluginInfo(File file) {

        try {
            JarFile jarFile = new JarFile(file);

            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if(jarEntry.getName().equals("plugin.properties")) {
                    Properties properties = new Properties();
                    properties.load(jarFile.getInputStream(jarEntry));
                    return new PluginInfo(properties);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        if(plugin instanceof PluginBase && !plugin.isEnabled()) {
            ((PluginBase) plugin).setEnabled(true);
        }
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        if(plugin instanceof PluginBase && plugin.isEnabled()) {
            ((PluginBase) plugin).setEnabled(false);
        }
    }
}
