package com.beanbeanjuice.simpleproxychat.commands.velocity;

import com.beanbeanjuice.simpleproxychat.SimpleProxyChatVelocity;
import com.beanbeanjuice.simpleproxychat.utility.helper.Helper;
import com.beanbeanjuice.simpleproxychat.utility.Tuple;
import com.beanbeanjuice.simpleproxychat.utility.config.Config;
import com.beanbeanjuice.simpleproxychat.utility.config.ConfigKey;
import com.beanbeanjuice.simpleproxychat.utility.config.Permission;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class VelocityAdminChatCommand implements SimpleCommand {

    private final SimpleProxyChatVelocity plugin;
    private final Config config;
    private static final Set<UUID> adminChatPlayers = new HashSet<>();

    public VelocityAdminChatCommand(final SimpleProxyChatVelocity plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();

        if (!(sender instanceof Player player)) {
            String message = config.get(ConfigKey.MINECRAFT_COMMAND_MUST_BE_PLAYER).asString();
            message = Helper.replaceKeys(
                    message,
                    Tuple.of("plugin-prefix", config.get(ConfigKey.PLUGIN_PREFIX).asString())
            );
            sender.sendMessage(Helper.stringToComponent(message));
            return;
        }

        if (!player.hasPermission(Permission.COMMAND_ADMIN_CHAT.getPermissionNode())) {
            String message = config.get(ConfigKey.MINECRAFT_COMMAND_NO_PERMISSION).asString();
            message = Helper.replaceKeys(
                    message,
                    Tuple.of("plugin-prefix", config.get(ConfigKey.PLUGIN_PREFIX).asString())
            );
            player.sendMessage(Helper.stringToComponent(message));
            return;
        }

        UUID playerUUID = player.getUniqueId();
        boolean isInAdminChat = adminChatPlayers.contains(playerUUID);

        if (isInAdminChat) {
            adminChatPlayers.remove(playerUUID);
            String message = config.get(ConfigKey.MINECRAFT_COMMAND_ADMIN_CHAT_DISABLED).asString();
            message = Helper.replaceKeys(
                    message,
                    Tuple.of("plugin-prefix", config.get(ConfigKey.PLUGIN_PREFIX).asString())
            );
            player.sendMessage(Helper.stringToComponent(message));
        } else {
            adminChatPlayers.add(playerUUID);
            String message = config.get(ConfigKey.MINECRAFT_COMMAND_ADMIN_CHAT_ENABLED).asString();
            message = Helper.replaceKeys(
                    message,
                    Tuple.of("plugin-prefix", config.get(ConfigKey.PLUGIN_PREFIX).asString())
            );
            player.sendMessage(Helper.stringToComponent(message));
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return List.of();
    }

    /**
     * Check if a player is in admin chat mode
     * @param playerUUID The UUID of the player to check
     * @return true if the player is in admin chat mode, false otherwise
     */
    public static boolean isInAdminChat(UUID playerUUID) {
        return adminChatPlayers.contains(playerUUID);
    }
}