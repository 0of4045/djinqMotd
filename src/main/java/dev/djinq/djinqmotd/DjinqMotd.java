package dev.djinq.djinqmotd;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

public final class DjinqMotd extends JavaPlugin {
    private volatile MotdSettings settings;
    private MessageManager messages;

    @Override
    public void onEnable() {
        try {
            ResourceMigration.mergeMissingDefaults(this, "config.yml");
            ResourceMigration.mergeMissingDefaults(this, "messages.yml");
        } catch (RuntimeException exception) {
            getLogger().severe("Could not prepare configuration: " + exception.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!reloadPlugin()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MotdCommand command = new MotdCommand(this);
        PluginCommand root = Objects.requireNonNull(getCommand("djinqmotd"));
        root.setExecutor(command);
        root.setTabCompleter(command);
        getServer().getPluginManager().registerEvents(new MotdListener(this), this);
        getServer().getConsoleSender().sendMessage(TextRenderer.render(
                "<#B3E342>djinqMotd <#CACACA>made by <#B3E342>djinq "
                        + "<#CACACA>| <#B3E342>djinq.dev <#CACACA>| <#B3E342>Loaded"));
    }

    public boolean reloadPlugin() {
        try {
            MotdSettings nextSettings = MotdSettings.load(new File(getDataFolder(), "config.yml"));
            MessageManager nextMessages = new MessageManager(this);
            if (!nextMessages.reload()) {
                return false;
            }
            settings = nextSettings;
            messages = nextMessages;
            return true;
        } catch (IOException | InvalidConfigurationException | IllegalArgumentException exception) {
            getLogger().severe("Could not load djinqMotd configuration: " + exception.getMessage());
            return false;
        }
    }

    public MotdSettings settings() {
        return settings;
    }

    public MessageManager messages() {
        return messages;
    }
}
