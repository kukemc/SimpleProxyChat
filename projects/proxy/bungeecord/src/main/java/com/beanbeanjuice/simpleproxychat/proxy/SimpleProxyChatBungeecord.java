package com.beanbeanjuice.simpleproxychat.proxy;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class SimpleProxyChatBungeecord extends Plugin {

    @Getter private SimpleProxyChat plugin;

    @Getter private Metrics metrics;

    @Override
    public void onEnable() {
        this.getLogger().info("The plugin is starting...");

        this.plugin = new SimpleProxyChat(this.getLogger()::info, this.getDataFolder());

        // bStats Stuff
        this.getLogger().info("Hooking into bStats... (IF ENABLED)");
        this.metrics = new Metrics(this, 21146);

        // Plugin has started.
        this.getLogger().info("Hello, world!");
    }

}
