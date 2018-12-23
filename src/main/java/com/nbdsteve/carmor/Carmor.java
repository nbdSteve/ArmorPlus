package com.nbdsteve.carmor;

import com.nbdsteve.carmor.command.CaCommand;
import com.nbdsteve.carmor.event.AdditionalPlayerDamage;
import com.nbdsteve.carmor.event.EquipMessage;
import com.nbdsteve.carmor.event.ReducedPlayerDamage;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.plugin.java.JavaPlugin;

public final class Carmor extends JavaPlugin {
    //New files instance
    private LoadCarmorFiles lcf;

    @Override
    public void onEnable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
        //Generate all of the files for the plugin
        this.lcf = new LoadCarmorFiles();
        getCommand("ca").setExecutor(new CaCommand(this));
        getCommand("carmor").setExecutor(new CaCommand(this));
        getServer().getPluginManager().registerEvents(new AdditionalPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new ReducedPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new EquipMessage(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
    }

    public LoadCarmorFiles getFiles() {
        return lcf;
    }
}
