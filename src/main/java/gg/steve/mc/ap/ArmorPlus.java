package gg.steve.mc.ap;

import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.gui.ApGui;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.managers.SetupManager;
import gg.steve.mc.ap.player.SetPlayerManager;
import gg.steve.mc.ap.utils.LogUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorPlus extends JavaPlugin {
    private static ArmorPlus instance;
    private static Economy economy;
    private static ApGui apGui;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        SetupManager.setupFiles(new FileManager(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        SetManager.loadSets();
        SetPlayerManager.init();
        // verify that the server is running vault so that eco features can be used
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        } else {
            LogUtil.info("Unable to find economy instance, disabling economy features.");
            economy = null;
        }
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

    public static Economy eco() {
        return economy;
    }

    public static ApGui getApGui() {
        if (apGui == null) {
            apGui = new ApGui(ConfigManager.CONFIG.get().getConfigurationSection("gui"));
        } else {
            apGui.refresh();
        }
        return apGui;
    }
}
