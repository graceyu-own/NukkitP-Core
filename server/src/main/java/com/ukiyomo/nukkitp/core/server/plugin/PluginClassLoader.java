package com.ukiyomo.nukkitp.core.server.plugin;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class PluginClassLoader extends URLClassLoader {

    private DefaultPluginLoader loader;

    public PluginClassLoader(DefaultPluginLoader loader, ClassLoader parent, File file) throws MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, parent);
        this.loader = loader;
    }

}
