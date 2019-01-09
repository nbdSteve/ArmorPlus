package com.nbdsteve.carmor.method.specialattack;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.message.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Class to handle the lightning special attack
 */
public class LightningAttack {

    /**
     * This will strike nearby enemies with lightning
     *
     * @param p              the player wearing the armor set
     * @param doRandomRadius if the radius should be random
     * @param radius         the max radius
     * @param lcf            LoadCarmorFiles instance
     */
    public LightningAttack(Player p, boolean doRandomRadius, double radius, LoadCarmorFiles lcf) {
        if (doRandomRadius) {
            radius = Math.random() * radius + 1;
        }
        for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
            if (!entity.isDead()) {
                entity.getWorld().strikeLightning(entity.getLocation());
                if (entity.getType().equals(EntityType.PLAYER)) {
                    Player player = (Player) entity;
                    if (player.getName().equals(p.getName())) {
                        player.setLastDamage(0.0);
                    } else {
                        new SendMessage("lightning-attack", (Player) entity, lcf, "{player}", p.getName());
                    }
                }
            }
        }
    }
}