package com.beanbeanjuice.simpleproxychat;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import org.bstats.bungeecord.Metrics;

import java.util.logging.Level;

public class SimpleProxyChatBungeecord extends Plugin {

    @Getter private Metrics metrics;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        this.getLogger().info("The plugin is starting.");

        // bStats Stuff
        this.getLogger().info("Starting bStats... (IF ENABLED)");
        this.metrics = new Metrics(this, 21146);

        // Plugin has started.
        this.getLogger().log(Level.INFO, "The plugin has been started.");
    }

}
