package dev.djinq.djinqmotd;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class MotdListener implements Listener {
    private final DjinqMotd plugin;

    public MotdListener(DjinqMotd plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPing(PaperServerListPingEvent event) {
        event.motd(plugin.settings().select());
    }
}
