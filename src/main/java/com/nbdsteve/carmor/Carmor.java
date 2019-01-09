package com.nbdsteve.carmor;

import com.nbdsteve.carmor.command.CaCommand;
import com.nbdsteve.carmor.event.AdditionalPlayerDamage;
import com.nbdsteve.carmor.event.DeEquipEvent;
import com.nbdsteve.carmor.event.EquipEvent;
import com.nbdsteve.carmor.event.ReducedPlayerDamage;
import com.nbdsteve.carmor.event.guiclick.ArmorBuyGuiClick;
import com.nbdsteve.carmor.event.guiclick.GeneralArmorGuiClick;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.potion.ServerPotionCheckRunnable;
import com.nbdsteve.carmor.method.armorequiplistener.ArmorListener;
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
        getServer().getPluginManager().registerEvents(new AdditionalPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new ReducedPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new EquipEvent(), this);
        getServer().getPluginManager().registerEvents(new DeEquipEvent(), this);
        getServer().getPluginManager().registerEvents(new GeneralArmorGuiClick(), this);
        getServer().getPluginManager().registerEvents(new ArmorBuyGuiClick(), this);
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
     * Method called when the plugin shuts down
     */
    @Override
    public void onDisable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
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
