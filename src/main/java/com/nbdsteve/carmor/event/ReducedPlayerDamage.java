package com.nbdsteve.carmor.event;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class ReducedPlayerDamage {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    @EventHandler
    public void reducedDamage(EntityDamageByEntityEvent e) {
        String setNumber;
        Player p;
        if (e.getEntity() instanceof Player) {
            p = (Player) e.getEntity();
        } else {
            return;
        }
        if (InventoryArmorCheck.checkArmor(p, lcf)) {
            setNumber = GetSetNumber.setNumber(p.getInventory().getHelmet().getItemMeta().getLore(), lcf);
            ((Player) e.getEntity()).setLastDamage(e.getDamage() * lcf.getArmor().getDouble(setNumber + ".reduced-damage"));
        }
    }
}
