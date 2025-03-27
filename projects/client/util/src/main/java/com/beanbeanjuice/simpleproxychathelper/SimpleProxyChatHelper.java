package com.beanbeanjuice.simpleproxychathelper;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleProxyChatHelper extends JavaPlugin {

    private Metrics metrics;

    @Override
    public void onEnable() {
        this.getLogger().info("The plugin is starting...");

        this.getLogger().info("Hooking into bStats... (IF ENABLED)");
        metrics = new Metrics(this, 22052);

        this.getLogger().info("Hello, world!");
    }

}
