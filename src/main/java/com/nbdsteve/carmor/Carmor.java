package com.nbdsteve.carmor;

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
    }

    @Override
    public void onDisable() {
        getLogger().info("Thanks for using Carmor - nbdSteve");
    }

    public LoadCarmorFiles getFiles() {
        return lcf;
    }
}
