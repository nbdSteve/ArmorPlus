package dev.nuer.ca.method.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * Method to check if a player has a potion effect
 */
public class PlayerPotionCheck {

    /**
     * If the player has that potion effect but the amplifier is less that the level, remove it
     *
     * @param p             the player being checked
     * @param effectToCheck the effect to check
     * @param level         the amplifier of the new effect
     */
    public static void potionCheck(Player p, String effectToCheck, int level) {
        if (p.getActivePotionEffects().size() > 0) {
            if (p.hasPotionEffect(PotionEffectType.getByName(effectToCheck))) {
                if (p.getActivePotionEffects().iterator().next().getType().equals(PotionEffectType.getByName(effectToCheck))) {
                    if (p.getActivePotionEffects().iterator().next().getAmplifier() <= level) {
                        p.removePotionEffect(PotionEffectType.getByName(effectToCheck));
                    }
                }
            }
        }
    }
}
