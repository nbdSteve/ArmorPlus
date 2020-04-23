package gg.steve.mc.ap;

import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.managers.SetupManager;
import gg.steve.mc.ap.player.SetPlayerManager;
import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorPlus extends JavaPlugin {
    private static ArmorPlus instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        SetupManager.setupFiles(new FileManager(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        SetManager.loadSets();
        SetPlayerManager.init();
        LogUtil.info("Thanks for using Armor+, please contact nbdSteve#0583 on discord if you find any bugs!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LogUtil.info("Thanks for using Armor+, please contact nbdSteve#0583 on discord if you find any bugs!");
    }

    public static ArmorPlus get() {
        return instance;
    }
}
