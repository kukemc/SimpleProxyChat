package com.beanbeanjuice.simpleproxychat.proxy;

import com.beanbeanjuice.simpleproxychat.proxy.config.SPCConfig;
import lombok.Getter;

import java.io.File;
import java.util.function.Consumer;

public class SimpleProxyChat {

    @Getter private SPCConfig config;
    @Getter private SPCConfig messages;
    @Getter private String prefix;

    @Getter private Consumer<String> infoLogger;

    public SimpleProxyChat(Consumer<String> infoLogger, File directory) {
        this.infoLogger = infoLogger;

        this.log("The plugin is starting...");

        this.initializeConfig(directory);
    }

    private void initializeConfig(File directory) {
        this.log("Initializing config...");
        this.config = new SPCConfig(directory, "config.yml");
        this.messages = new SPCConfig(directory, "messages.yml");

        this.config.initialize();
        this.messages.initialize();

        int configVersion = this.config.getConfig().getInt("file-version");
        int messageVersion = this.messages.getConfig().getInt("file-version");
        this.prefix = this.messages.getConfig().getString("plugin-prefix");

        this.log(String.format("Configs initialized. Config: %d, Message: %d", configVersion, messageVersion));
    }

    public void log(String message) {
        this.infoLogger.accept(message);
    }

}
