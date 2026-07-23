package gg.steve.mc.ap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.gui.ApGui;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.managers.SetupManager;
import gg.steve.mc.ap.papi.ArmorPlusExpansion;
import gg.steve.mc.ap.player.PlayerArmorSetService;
import gg.steve.mc.ap.utils.LogUtil;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ArmorPlus extends JavaPlugin {
    private static ArmorPlus instance;
    private static Economy economy;
    private static ApGui apGui;
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    // kept on the plugin so the static getApGui() facade can reach it
    private ArmorSetCatalog catalog;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        // reset apgui to null for reloading
        apGui = null;
        SetupManager.setupFiles(new FileManager(instance));
        Injector injector = Guice.createInjector(new ArmorPlusModule(this));
        this.catalog = injector.getInstance(ArmorSetCatalog.class);
        PlayerArmorSetService playerArmorSetService = injector.getInstance(PlayerArmorSetService.class);
        SetupManager.registerCommands(instance, catalog);
        SetupManager.registerEvents(instance, catalog, playerArmorSetService);
        catalog.loadSets();
        playerArmorSetService.init();
        // verify that the server is running vault so that eco features can be used
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            LogUtil.info("Vault found, hooking into it now...");
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

            if (rsp != null) {
                economy = rsp.getProvider();
            }
        } else {
            LogUtil.info("Unable to find economy instance, disabling economy features. If you intend to use economy please install Vault and an economy plugin.");
            economy = null;
        }
        // placeholder hook
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansion with it now...");
            new ArmorPlusExpansion(instance, catalog, playerArmorSetService).register();
        }
        // metrics, just doing it here for now
        Metrics metrics = new Metrics(this, 7719);
        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            valueMap.put("servers", 1);
            valueMap.put("players", Bukkit.getOnlinePlayers().size());
            return valueMap;
        }));
        // send nice message
        LogUtil.info("Thanks for using Armor+ v" + getDescription().getVersion() + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LogUtil.info("Thanks for using Armor+ v" + getDescription().getVersion() + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    public static ArmorPlus get() {
        return instance;
    }

    public static Economy eco() {
        return economy;
    }

    public static ApGui getApGui() {
        if (apGui == null) {
            YamlConfiguration config = Objects.requireNonNull(ConfigManager.CONFIG.get(), "armor+.yml config is not loaded");
            apGui = new ApGui(config.getConfigurationSection("gui"), instance.catalog);
        } else {
            apGui.refresh();
        }
        return apGui;
    }

    public static String formatNumber(double amount) {
        return numberFormat.format(amount);
    }
}
