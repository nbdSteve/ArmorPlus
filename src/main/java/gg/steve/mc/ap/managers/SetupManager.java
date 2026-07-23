package gg.steve.mc.ap.managers;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armorequipevent.ArmorListener;
import gg.steve.mc.ap.cmd.ApCmd;
import gg.steve.mc.ap.data.utils.EngineerAttackUtil;
import gg.steve.mc.ap.data.utils.TravellerAttackUtil;
import gg.steve.mc.ap.gui.GuiClickListener;
import gg.steve.mc.ap.listener.*;
import gg.steve.mc.ap.player.PlayerArmorSetService;
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

    public static void registerCommands(ArmorPlus instance, ArmorSetCatalog catalog) {
        instance.getCommand("ap").setExecutor(new ApCmd(catalog));
    }

    public static void registerEvents(Plugin instance, ArmorSetCatalog catalog, PlayerArmorSetService playerArmorSetService) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new ArmorListener(ConfigManager.BLOCKED.get().getStringList("blocked")), instance);
//        pm.registerEvents(new DispenserArmorListener(), instance);
        pm.registerEvents(new PlayerEquipListener(catalog, playerArmorSetService), instance);
        pm.registerEvents(new PlayerUnequipListener(catalog, playerArmorSetService), instance);
        pm.registerEvents(new PlayerCommandListener(catalog), instance);
        pm.registerEvents(new ArmorBuffListener(catalog, playerArmorSetService), instance);
        pm.registerEvents(new GuiClickListener(), instance);
        pm.registerEvents(new ArmorSwitchListener(), instance);
        pm.registerEvents(new TravellerAttackUtil(), instance);
        pm.registerEvents(new EngineerAttackUtil(), instance);
    }
}
