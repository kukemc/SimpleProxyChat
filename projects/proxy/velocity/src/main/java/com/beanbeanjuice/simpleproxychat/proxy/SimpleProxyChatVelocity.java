package com.beanbeanjuice.simpleproxychat.proxy;

import com.beanbeanjuice.simpleproxychat.proxy.config.SPCConfig;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class SimpleProxyChatVelocity {

    private final Metrics.Factory metricsFactory;

    @Getter private final ProxyServer proxyServer;
    @Getter private final Logger logger;

    @Getter private SimpleProxyChat plugin;

    private Metrics metrics;

    @Inject
    public SimpleProxyChatVelocity(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.metricsFactory = metricsFactory;

        this.plugin = new SimpleProxyChat(this.logger::info, dataDirectory.toFile());
    }

    @Subscribe(priority = 0)
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // bStats Stuff
        this.getLogger().info("Hooking into bStats... (IF ENABLED)");
        this.metrics = metricsFactory.make(this, 21147);

        // Plugin has started.
        this.plugin.log("Hello, world!");
    }

}
