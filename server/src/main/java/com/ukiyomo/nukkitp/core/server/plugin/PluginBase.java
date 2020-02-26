package com.ukiyomo.nukkitp.core.server.plugin;


import com.ukiyomo.nukkitp.core.server.CoreApplication;

public abstract class PluginBase implements Plugin{

    private CoreApplication coreApplication;
    private boolean enabled = false;
    private boolean initialized = false;

    private PluginInfo pluginInfo;

    protected void init(CoreApplication coreApplication, PluginInfo pluginInfo) {
        if(!this.initialized) {
            this.initialized = true;
            this.coreApplication = coreApplication;
            this.pluginInfo = pluginInfo;
        }
    }

    public void onLoad() {

    }

    abstract public void onEnable();

    abstract public void onDisable();

    public PluginInfo getPluginInfo() {
        return this.pluginInfo;
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean value) {
        if(this.enabled != value) {
            this.enabled = value;
            if(isEnabled()) {
                this.onEnable();
            } else {
                this.onDisable();
            }
        }
    }

    public CoreApplication getCoreApplication() {
        return this.coreApplication;
    }
}
