package com.nbdsteve.carmor.method.bonusattack;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LightningAttack {

    public LightningAttack(Player p, boolean doRandomRadius, double radius) {
        if (doRandomRadius) {
            radius = Math.random() * radius + 1;
        }
        for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
            if (!entity.isDead()) {
                entity.getWorld().strikeLightning(entity.getLocation());
                if (entity.getType().equals(EntityType.PLAYER)) {
                    if (entity.getType().getName().equals(p.getName())) {
                        Player player = (Player) entity;
                        player.setLastDamage(0.0);
                    }
                }
            }
        }
    }
}
