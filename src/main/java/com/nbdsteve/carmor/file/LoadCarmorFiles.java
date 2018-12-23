package com.nbdsteve.carmor.file;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.providedfile.GenerateCarmorFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * Class to load a specified file, to create a new file simply add the name of it to the ENUM.
 * Then add another line to the LoadProvidedFiles method and create a getter for that file.
 */
public class LoadCarmorFiles {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //HashMap to store the files
    private HashMap<Files, GenerateCarmorFiles> fileList;

    /**
     * Enum to store each file, this is public so we can call method on these
     */
    public enum Files {
        CONFIG, MESSAGES, ARMOR
    }

    /**
     * Generate all of the files in the enum
     */
    public LoadCarmorFiles() {
        fileList = new HashMap<>();
        fileList.put(Files.CONFIG, new GenerateCarmorFiles("config.yml"));
        fileList.put(Files.MESSAGES, new GenerateCarmorFiles("messages.yml"));
        fileList.put(Files.ARMOR, new GenerateCarmorFiles("armor.yml"));
        pl.getLogger().info("Loading provided files...");
    }

    public FileConfiguration getConfig() {
        return fileList.get(Files.CONFIG).get();
    }

    public FileConfiguration getMessages() {
        return fileList.get(Files.MESSAGES).get();
    }

    public FileConfiguration getArmor() {
        return fileList.get(Files.ARMOR).get();
    }

    public void reload() {
        for (Files file : Files.values()) {
            fileList.get(file).reload();
        }
        pl.getLogger().info("Reloading provided files...");
    }
}
