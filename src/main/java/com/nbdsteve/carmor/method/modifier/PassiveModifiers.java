package com.nbdsteve.carmor.method.modifier;

import com.nbdsteve.carmor.method.modifier.passive.AlterMaxHealth;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Class that handles adding and removing all passive modifiers
 */
public class PassiveModifiers {

    /**
     * Adds each modifier to the player
     *
     * @param modifierList the list of modifiers to add
     * @param player       the player to add them to
     */
    public static void applyPassiveModifiers(List<String> modifierList, Player player) {
        for (String line : modifierList) {
            String[] modifierParts = line.split(":");
            if (modifierParts[0].equalsIgnoreCase("health")) {
                new AlterMaxHealth(player, Double.parseDouble(modifierParts[1]));
            }
        }
    }

    /**
     * Remove all modifiers from a player
     *
     * @param player the player to remove the modifiers from
     */
    public static void removePassiveModifiers(Player player) {
        new AlterMaxHealth(player, 20);
    }
}
