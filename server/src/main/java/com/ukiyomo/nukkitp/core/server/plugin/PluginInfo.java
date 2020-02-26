package com.ukiyomo.nukkitp.core.server.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PluginInfo {

    private String name;
    private String main;
    private String version;

    private List<String> authors;
    private List<String> dependencies;

    public PluginInfo(Properties properties) {
        name = properties.getProperty("bootstrap.name", null);
        main = properties.getProperty("bootstrap.main", null);
        version = properties.getProperty("bootstrap.version", null);
        dependencies = parseDependencies(properties.getProperty("bootstrap.dependencies", null));
        authors = parseAuthor(properties.getProperty("dev.authors", null));

    }

    private List<String> parseAuthor(String raw) {
        return parseArray(raw);
    }

    private List<String> parseDependencies(String raw) {
        return parseArray(raw);
    }

    private List<String> parseArray(String raw) {
        List<String> values = new ArrayList<>();
        if(null != raw) {
            for (String s : raw.split(",")) {
                values.add(s.trim());
            }
        }
        return values;
    }

    public String getName() {
        return name;
    }

    public String getMain() {
        return main;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getDependencies() {
        return dependencies;
    }
}
