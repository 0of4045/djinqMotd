package dev.djinq.djinqmotd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public record MotdSettings(boolean random, List<Component> messages) {
    public MotdSettings {
        messages = List.copyOf(messages);
    }

    public static MotdSettings load(File file) throws IOException, InvalidConfigurationException {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        Object rawMode = config.get("motd.mode");
        if (!(rawMode instanceof String mode)) {
            throw new InvalidConfigurationException("motd.mode must be STATIC or RANDOM");
        }
        String normalized = mode.toUpperCase(Locale.ROOT);
        if (!normalized.equals("STATIC") && !normalized.equals("RANDOM")) {
            throw new InvalidConfigurationException("motd.mode must be STATIC or RANDOM");
        }

        String path = "motd.motd_line";
        Object rawMessages = config.get(path);
        if (!(rawMessages instanceof List<?> entries) || entries.isEmpty()) {
            throw new InvalidConfigurationException(path + " must contain at least one MOTD");
        }
        List<Component> prepared = new ArrayList<>();
        for (int index = 0; index < entries.size(); index++) {
            if (!(entries.get(index) instanceof Map<?, ?> lines) || lines.size() != 2
                    || !(lines.get("1") instanceof String firstLine)
                    || !(lines.get("2") instanceof String secondLine)) {
                throw new InvalidConfigurationException(
                        path + "[" + index + "] must contain exactly two strings, '1' and '2'");
            }
            prepared.add(
                    TextRenderer.render(firstLine).append(Component.newline()).append(TextRenderer.render(secondLine)));
        }
        return new MotdSettings(normalized.equals("RANDOM"), prepared);
    }

    public Component select() {
        return messages.get(random ? ThreadLocalRandom.current().nextInt(messages.size()) : 0);
    }
}
