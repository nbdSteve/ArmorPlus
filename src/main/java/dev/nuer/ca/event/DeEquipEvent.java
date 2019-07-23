package dev.nuer.ca.event;

import dev.nuer.ca.Carmor;
import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.GetSetNumber;
import dev.nuer.ca.method.InventoryArmorCheck;
import dev.nuer.ca.method.armorequiplistener.ArmorEquipEvent;
import dev.nuer.ca.method.message.SendMessage;
import dev.nuer.ca.method.modifier.PassiveModifiers;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

/**
 * Class containing code for the deequip armor event
 */
public class DeEquipEvent implements Listener {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    /**
     * All code for the equip event is in this method
     *
     * @param e the event, cannot be null
     */
    @EventHandler
    public void ArmorDeEquip(ArmorEquipEvent e) {
        //Store the set number
        String setNumber;
        //Store the player from the event
        Player p = e.getPlayer();
        //Make sure its an actual item and not air
        if (e.getOldArmorPiece() == null || e.getOldArmorPiece().getType().equals(Material.AIR)) {
            return;
        }
        //Verify that the player is wearing armor with meta & lore
        if (e.getOldArmorPiece().hasItemMeta()) {
            if (e.getOldArmorPiece().getItemMeta().hasLore()) {
                //Check to see if the armor is part of a full set
                if (InventoryArmorCheck.checkEquipArmor(p, lcf,
                        e.getOldArmorPiece().getItemMeta().getLore(), e.getOldArmorPiece().getType())) {
                    //Get the set number
                    setNumber = GetSetNumber.setNumber(e.getOldArmorPiece().getItemMeta().getLore(), lcf);
                    //Send set removal message
                    new SendMessage(setNumber + ".removal-message", p, lcf);
                    // Commands to run on unequip
                    for (String command : lcf.getArmor().getStringList(setNumber + ".commandonunequip")) {
                    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{username}", p.getName()));
                    }
                    //Clear the potion effects from the player
                    for (String effect : lcf.getArmor().getStringList(setNumber + ".potion-effects")) {
                        String[] parts = effect.split(":");
                        p.removePotionEffect(PotionEffectType.getByName(parts[0].toUpperCase()));
                    }
                    //Remove the passive effects
                    PassiveModifiers.removePassiveModifiers(p);
                }
            }
        }
    }
}
