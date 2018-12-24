package com.nbdsteve.carmor.event;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

/**
 * Class containing methods for the additional damage for the player attack event
 */
public class AdditionalPlayerDamage implements Listener {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    /**
     * All code for the event is contained in this method
     *
     * @param e the event, cannot be null
     */
    @EventHandler
    public void additionalDamage(EntityDamageByEntityEvent e) {
        String setNumber;
        Player p;
        if (e.getDamager() instanceof Player) {
            p = (Player) e.getDamager();
        } else {
            return;
        }
        if (InventoryArmorCheck.checkArmor(p, lcf)) {
            setNumber = GetSetNumber.setNumber(p.getInventory().getHelmet().getItemMeta().getLore(), lcf);
            e.setDamage(e.getDamage() * lcf.getArmor().getDouble(setNumber + ".additional-damage"));
        }
    }
}