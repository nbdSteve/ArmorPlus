package com.nbdsteve.carmor;

import com.nbdsteve.carmor.command.CaCommand;
import com.nbdsteve.carmor.event.AdditionalPlayerDamage;
import com.nbdsteve.carmor.event.EquipMessage;
import com.nbdsteve.carmor.event.ReducedPlayerDamage;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.armorequiplistener.ArmorListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core class for the Carmor plugin
 */
public final class Carmor extends JavaPlugin {
    //New files instance
    private LoadCarmorFiles lcf;

    /**
     * Method called when the plugin starts up
     */
    @Override
    public void onEnable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
        //Generate all of the files for the plugin
        this.lcf = new LoadCarmorFiles();
        //Register the commands for the plugin
        getCommand("ca").setExecutor(new CaCommand(this));
        getCommand("carmor").setExecutor(new CaCommand(this));
        //Register the armor equip event listener by Borlea
        getServer().getPluginManager().registerEvents(new ArmorListener(lcf.getConfig().getStringList(
                "blocked")), this);
        //Register the other events for the plugin
        getServer().getPluginManager().registerEvents(new AdditionalPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new ReducedPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new EquipMessage(), this);
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
}
