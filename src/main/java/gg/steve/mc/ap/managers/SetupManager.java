package gg.steve.mc.ap.managers;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.armorequipevent.ArmorListener;
import gg.steve.mc.ap.gui.GuiClickListener;
import gg.steve.mc.ap.listener.PlayerCommandListener;
import gg.steve.mc.ap.listener.PlayerDamageListener;
import gg.steve.mc.ap.listener.PlayerEquipListener;
import gg.steve.mc.ap.listener.PlayerUnequipListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        // general files
        for (ConfigManager file : ConfigManager.values()) {
            file.load(fileManager);
        }
    }

    public static void registerCommands(ArmorPlus instance) {
//        instance.getCommand("ap").setExecutor(new FactionsCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new ArmorListener(ConfigManager.BLOCKED.get().getStringList("blocked")), instance);
//        pm.registerEvents(new DispenserArmorListener(), instance);
        pm.registerEvents(new PlayerEquipListener(), instance);
        pm.registerEvents(new PlayerUnequipListener(), instance);
        pm.registerEvents(new PlayerCommandListener(), instance);
        pm.registerEvents(new PlayerDamageListener(), instance);
        pm.registerEvents(new GuiClickListener(), instance);
    }
}
