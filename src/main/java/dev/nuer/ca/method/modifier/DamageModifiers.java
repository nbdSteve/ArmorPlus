package dev.nuer.ca.method.modifier;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

/**
 * Class that handles all of the modifiers that are called with events
 */
public class DamageModifiers {

    /**
     * Applies the event modifiers to the player / event
     *
     * @param modifierList the list of modifiers
     * @param player       the player to alter
     * @param isDamager    boolean, if the player is the damager
     * @param event        the event
     */
    public static void applyDamageModifiers(List<String> modifierList, Player player, boolean isDamager, EntityDamageByEntityEvent event) {
        for (String line : modifierList) {
            String[] modifierParts = line.split(":");
            if (isDamager) {
                if (modifierParts[0].equalsIgnoreCase("+dmg")) {
                    event.setDamage(event.getDamage() * Double.parseDouble(modifierParts[1]));
                }
            } else {
                if (modifierParts[0].equalsIgnoreCase("-dmg")) {
                    player.setLastDamage(event.getDamage() * Double.parseDouble(modifierParts[1]));
                }
                if (modifierParts[0].equalsIgnoreCase("kb")) {
                    player.setVelocity(event.getDamager().getLocation().getDirection().setY(0).normalize().multiply(Double.parseDouble(modifierParts[1])));
                }
            }
        }
    }
}