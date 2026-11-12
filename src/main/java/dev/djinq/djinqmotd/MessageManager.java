package dev.djinq.djinqmotd;

import java.io.File;
import java.io.IOException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageManager {
    private final JavaPlugin plugin;
    private YamlConfiguration messages;
    private String prefix;

    public MessageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean reload() {
        try {
            YamlConfiguration nextMessages = new YamlConfiguration();
            nextMessages.load(new File(plugin.getDataFolder(), "messages.yml"));
            Object nextPrefix = nextMessages.get("prefix");
            if (!(nextPrefix instanceof String) || nextMessages.getConfigurationSection("messages") == null) {
                throw new InvalidConfigurationException("messages.yml needs a string prefix and messages section");
            }
            for (String key : new String[] {"no-permission", "reload-success", "reload-failed", "usage"}) {
                if (!(nextMessages.get("messages." + key) instanceof String)) {
                    throw new InvalidConfigurationException("messages." + key + " must be a string");
                }
            }
            messages = nextMessages;
            prefix = (String) nextPrefix;
            return true;
        } catch (IOException | InvalidConfigurationException exception) {
            plugin.getLogger().severe("Could not load messages.yml: " + exception.getMessage());
            return false;
        }
    }

    public void send(CommandSender sender, String key) {
        sender.sendMessage(TextRenderer.render(prefix + messages.getString("messages." + key)));
    }
}
