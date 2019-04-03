package dev.nuer.ca.method.specialattack;

import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.message.SendMessage;
import dev.nuer.ca.method.potion.PlayerPotionCheck;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Class to handle the potion special attack
 */
public class PotionEffectAttack {

    /**
     * This method will inflict nearby enemies with the desired potion effect
     *
     * @param effectName     effect to give the enemies
     * @param effectLevel    level of the effect
     * @param effectDuration duration of the effect
     * @param p              the player wearing the armor
     * @param doRandomRadius if the radius should be random
     * @param radius         the max radius
     * @param lcf            LoadCarmorFiles instance
     */
    public PotionEffectAttack(String effectName, int effectLevel, int effectDuration, Player p, boolean doRandomRadius, double radius, LoadCarmorFiles lcf) {
        if (doRandomRadius) {
            radius = Math.random() * radius + 1;
        }
        for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
            if (entity.getType().equals(EntityType.PLAYER)) {
                Player player = (Player) entity;
                if (!player.getName().equals(p.getName())) {
                    PlayerPotionCheck.potionCheck(player, effectName.toUpperCase(), effectLevel - 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effectName.toUpperCase()), effectDuration * 20, effectLevel - 1));
                    new SendMessage("potion-attack", (Player) entity, lcf, "{player}", p.getName());
                }
            }
        }
    }
}