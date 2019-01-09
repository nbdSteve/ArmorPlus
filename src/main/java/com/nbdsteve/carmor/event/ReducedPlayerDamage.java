package com.nbdsteve.carmor.event;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import com.nbdsteve.carmor.method.specialattack.LightningAttack;
import com.nbdsteve.carmor.method.specialattack.PotionEffectAttack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class containing code for the reduced damage event
 */
public class ReducedPlayerDamage implements Listener {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();
    //
    private ArrayList<UUID> playerLightning;

    /**
     * All code for the event is contained in this method
     *
     * @param e the event, cannot be null
     */
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
            ((Player) e.getEntity()).setLastDamage(e.getDamage() * lcf.getArmor().getDouble(setNumber +
                    ".reduced-damage"));
            //Get the list of bonus attacks associated with the armor set
            for (String bonusAttack : lcf.getArmor().getStringList(setNumber + ".bonus-attacks")) {
                //Generate a proc chance
                double chance = Math.random() * 1;
                //Split up the attack sequence
                String[] attack = bonusAttack.split(":");
                if (attack[0].equalsIgnoreCase("lightning")) {
                    if (chance < Double.parseDouble(attack[3])) {
                        new LightningAttack(p, Boolean.parseBoolean(attack[1]), Double.parseDouble(attack[2]), lcf);
                    }
                } else if (attack[0].equalsIgnoreCase("potion")) {
                    if (chance < Double.parseDouble(attack[6])) {
                        new PotionEffectAttack(attack[1], Integer.parseInt(attack[2]), Integer.parseInt(attack[3]), p, Boolean.parseBoolean(attack[1]), Double.parseDouble(attack[2]), lcf);
                    }
                }
            }
        }
    }
}