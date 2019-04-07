package dev.nuer.ca.method.specialattack;

import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.message.SendMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Class to handle the lightning special attack
 */
public class LightningAttack {

    /**
     * This will strike nearby enemies with lightning, using a runnable to strike multiple times if the
     * user desires
     *
     * @param p                   player wearing the armor set
     * @param doRandomRadius      boolean, if the radius to strike is random
     * @param radius              int, the radius from the player that will be affected
     * @param numberOfStrikes     int, the amount of times lightning will strike
     * @param delayBetweenStrikes int, delay (in ticks) between strikes
     * @param damagePerStrike     double, damage dealt to the entity affected
     * @param lcf                 LoadCarmorFiles instance
     * @param plugin              main plugin instance
     */
    public LightningAttack(Player p, boolean doRandomRadius, double radius,
                           int numberOfStrikes, int delayBetweenStrikes, double damagePerStrike,
                           LoadCarmorFiles lcf, Plugin plugin) {
        //Check if the radius is random, update it if it is
        if (doRandomRadius) {
            radius = Math.random() * radius + 1;
        }
        //Store the radius as final so it can be accessed inside the runnable
        final double rad = radius;
        //Store the players who have been messaged (reduces the proc spam).
        ArrayList<String> alreadyMessaged = new ArrayList<>();

        new BukkitRunnable() {
            //To increment the strikes
            int base = 0;

            @Override
            public void run() {
                if (base < numberOfStrikes) {
                    for (Entity entity : p.getNearbyEntities(rad, rad, rad)) {
                        if (!entity.isDead()) {
                            if (entity.getType().equals(EntityType.PLAYER)) {
                                Player player = (Player) entity;
                                if (!player.getName().equals(p.getName())) {
                                    EntityDamageEvent strikeDamageByPlayer = new EntityDamageEvent(entity,
                                            EntityDamageEvent.DamageCause.ENTITY_ATTACK, damagePerStrike);
                                    Bukkit.getPluginManager().callEvent(strikeDamageByPlayer);
                                    if (strikeDamageByPlayer.isCancelled()) {
                                        this.cancel();
                                    }
                                    if (!alreadyMessaged.contains(entity.getName())) {
                                        new SendMessage("lightning-attack", (Player) entity, lcf, "{player}",
                                                p.getName());
                                        entity.getWorld().strikeLightningEffect(entity.getLocation());
                                        alreadyMessaged.add(entity.getName());
                                    }
                                }
                            }
                        }
                    }
                    base++;
                }
            }
        }.runTaskTimer(plugin, 0L, delayBetweenStrikes);
    }
}