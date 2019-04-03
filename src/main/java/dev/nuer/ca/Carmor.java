package dev.nuer.ca;

import dev.nuer.ca.command.CaCommand;
import dev.nuer.ca.event.DeEquipEvent;
import dev.nuer.ca.event.EquipEvent;
import dev.nuer.ca.event.PlayerDamageEvent;
import dev.nuer.ca.event.guiclick.ArmorBuyGuiClick;
import dev.nuer.ca.event.guiclick.GeneralArmorGuiClick;
import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.armorequiplistener.ArmorListener;
import dev.nuer.ca.method.potion.ServerPotionCheckRunnable;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core class for the Carmor plugin
 */
public final class Carmor extends JavaPlugin {
    //New files instance
    private LoadCarmorFiles lcf;
    //Register the potion check instance, this check runs every second
    private ServerPotionCheckRunnable spcr;
    //Get the servers econ
    private static Economy econ;

    /**
     * Method called when the plugin starts up
     */
    @Override
    public void onEnable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
        //Generate all of the files for the plugin
        this.lcf = new LoadCarmorFiles();
        //Create the server check runnable
        this.spcr = new ServerPotionCheckRunnable();
        //Check if the server has an economy
        if (!setupEconomy()) {
            getLogger().severe("Vault.jar not found, disabling economy features.");
        }
        //Register the commands for the plugin
        getCommand("ca").setExecutor(new CaCommand(this));
        getCommand("carmor").setExecutor(new CaCommand(this));
        //Register the armor equip event listener by Borlea
        getServer().getPluginManager().registerEvents(new ArmorListener(lcf.getConfig().getStringList(
                "blocked")), this);
        //Register the other events for the plugin
        getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new EquipEvent(), this);
        getServer().getPluginManager().registerEvents(new DeEquipEvent(), this);
        getServer().getPluginManager().registerEvents(new GeneralArmorGuiClick(), this);
        getServer().getPluginManager().registerEvents(new ArmorBuyGuiClick(), this);
    }

    /**
     * Method called when the plugin shuts down
     */
    @Override
    public void onDisable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
    }

    //Set up the economy for the plugin
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    /**
     * Getter for the server files instance
     *
     * @return
     */
    public LoadCarmorFiles getFiles() {
        return lcf;
    }

    /**
     * Get the servers economy
     *
     * @return econ
     */
    public static Economy getEconomy() {
        return econ;
    }
}
