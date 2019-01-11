package com.nbdsteve.carmor.method.modifier.passive;

import org.bukkit.entity.Player;

/**
 * Class to change the players maximum health
 */
public class AlterMaxHealth {

    /**
     * Set the players max health
     *
     * @param player    the player to alter
     * @param maxHealth their new max health
     */
    public AlterMaxHealth(Player player, double maxHealth) {
        player.setMaxHealth(maxHealth);
    }
}
