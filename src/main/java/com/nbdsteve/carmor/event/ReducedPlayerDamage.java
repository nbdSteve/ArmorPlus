package com.nbdsteve.carmor.event;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
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
//            for (Entity entity : p.getNearbyEntities(5, 5, 5)) {
//                if (entity.getType().equals(EntityType.PLAYER)) {
//                    if (entity.getType().getName().equals(p.getName())) {
//                        //Do nothing
//                    } else {
//                        entity.getWorld().strikeLightning(entity.getLocation());
//                    }
//                } else {
//                    if (!entity.isDead()) {
//                        entity.getWorld().strikeLightning(entity.getLocation());
//                    }
//                }
//            }
        }
    }

//    @EventHandler
//    public void noLightningDamage(EntityDamageEvent e) {
//        if (e.getEntity() instanceof Player) {
//            Player p = (Player) e.getEntity();
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    if (playerLightning.contains(p.getUniqueId())) {
//                        if (e.getEntityType().getName().equals(p.getName())) {
//                            if (e.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) {
//                                e.setCancelled(true);
//                            }
//                        }
//                    }
//                    playerLightning.remove(p.getUniqueId());
//                    cancel();
//                }
//            }.runTaskLater(pl, 40L);
//        }
//    }
}