package dev.djinq.djinqmotd;

import java.util.List;
import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public final class MotdCommand implements CommandExecutor, TabCompleter {
    private final DjinqMotd plugin;

    public MotdCommand(DjinqMotd plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            plugin.messages().send(sender, "usage");
            return true;
        }
        if (!sender.hasPermission("djinqmotd.reload")) {
            plugin.messages().send(sender, "no-permission");
            return true;
        }
        boolean reloaded = plugin.reloadPlugin();
        plugin.messages().send(sender, reloaded ? "reload-success" : "reload-failed");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && sender.hasPermission("djinqmotd.reload")
                && "reload".startsWith(args[0].toLowerCase(Locale.ROOT))) {
            return List.of("reload");
        }
        return List.of();
    }
}
