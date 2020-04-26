package gg.steve.mc.ap.managers;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum ConfigManager {
    CONFIG("armor+.yml"),
    PERMISSIONS("permissions.yml"),
    DEBUG("lang" + File.separator + "debug.yml"),
    MESSAGES("lang" + File.separator + "messages.yml"),
    BLOCKED("assets" + File.separator + "blocked.yml");

    private final String path;

    ConfigManager(String path) {
        this.path = path;
    }

    public void load(FileManager fileManager) {
        fileManager.add(name(), this.path);
    }

    public YamlConfiguration get() {
        return FileManager.get(name());
    }

    public void save() {
        FileManager.save(name());
    }

    public static void reload() {
        FileManager.reload();
    }
}
