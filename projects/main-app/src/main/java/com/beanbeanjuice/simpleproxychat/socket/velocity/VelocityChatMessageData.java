package com.beanbeanjuice.simpleproxychat.socket.velocity;

import com.beanbeanjuice.simpleproxychat.SimpleProxyChatVelocity;
import com.beanbeanjuice.simpleproxychat.socket.ChatMessageData;
import com.beanbeanjuice.simpleproxychat.utility.config.ConfigKey;
import com.beanbeanjuice.simpleproxychat.utility.config.Permission;
import com.beanbeanjuice.simpleproxychat.utility.listeners.MessageType;
import com.beanbeanjuice.simpleproxychat.utility.listeners.velocity.VelocityServerListener;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Collection;
import java.util.UUID;

public class VelocityChatMessageData extends ChatMessageData {

    private SimpleProxyChatVelocity plugin;
    @Getter private final RegisteredServer server;

    public VelocityChatMessageData(SimpleProxyChatVelocity plugin, MessageType type, RegisteredServer server,
                                   Player player, String message) {
        super(
                type,
                server.getServerInfo().getName(),
                player.getUsername(),
                player.getUniqueId(),
                message
        );
        this.plugin = plugin;
        this.server = server;
    }

    public VelocityChatMessageData(SimpleProxyChatVelocity plugin, MessageType type, RegisteredServer server,
                                 Player player, String message,
                                 String parsedMinecraftString, String parsedDiscordString, String parsedDiscordEmbedTitle, String parsedDiscordEmbedMessage) {
        super(
                type,
                server.getServerInfo().getName(),
                player.getUsername(),
                player.getUniqueId(),
                message,
                parsedMinecraftString,
                parsedDiscordString,
                parsedDiscordEmbedTitle,
                parsedDiscordEmbedMessage
        );

        this.plugin = plugin;
        this.server = server;
    }

    @Override
    public void chatSendToAllOtherPlayers(String parsedMessage) {
        Collection<Player> blacklistedUUIDs = server.getPlayersConnected();

        Component component = MiniMessage.miniMessage().deserialize(parsedMessage);

        // Check if player is in admin chat mode
        boolean isAdminChat = false;
        try {
            // Use reflection to avoid direct dependency on VelocityAdminChatCommand
            Class<?> adminChatCommandClass = Class.forName("com.beanbeanjuice.simpleproxychat.commands.velocity.VelocityAdminChatCommand");
            java.lang.reflect.Method isInAdminChatMethod = adminChatCommandClass.getMethod("isInAdminChat", UUID.class);
            isAdminChat = (boolean) isInAdminChatMethod.invoke(null, this.getPlayerUUID());
        } catch (Exception e) {
            // If any error occurs, assume not in admin chat
            isAdminChat = false;
        }

        if (isAdminChat) {
            // If in admin chat, only send to players with admin chat permission
            // We don't need to exclude players from the same server anymore since we're canceling the original event
            plugin.getProxyServer().getAllPlayers().stream()
                    .filter((streamPlayer) -> plugin.getConfig().get(ConfigKey.USE_PERMISSIONS).asBoolean() ? 
                            streamPlayer.hasPermission("simpleproxychat.adminchat") : false)
                    .filter((streamPlayer) -> !VelocityServerListener.playerIsInDisabledServer(streamPlayer, plugin))
                    .forEach((streamPlayer) -> streamPlayer.sendMessage(component));
        } else {
            // Normal chat behavior
            plugin.getProxyServer().getAllPlayers().stream()
                    .filter((streamPlayer) -> !blacklistedUUIDs.contains(streamPlayer))
                    .filter((streamPlayer) -> {
                        if (!plugin.getConfig().get(ConfigKey.USE_PERMISSIONS).asBoolean()) return true;
                        return streamPlayer.hasPermission(Permission.READ_CHAT_MESSAGE.getPermissionNode());
                    })
                    .filter((streamPlayer) -> !VelocityServerListener.playerIsInDisabledServer(streamPlayer, plugin))
                    .forEach((streamPlayer) -> streamPlayer.sendMessage(component));
        }
    }

    @Override
    public void startPluginMessage() {
        server.sendPluginMessage(VelocityPluginMessagingListener.IDENTIFIER, this.getAsBytes());
    }

}
