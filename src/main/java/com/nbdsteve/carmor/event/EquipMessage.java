package com.nbdsteve.carmor.event;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import com.nbdsteve.carmor.method.armorequiplistener.ArmorEquipEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Class that handles armor equiping, it will send a message to the player if they have a full set
 */
public class EquipMessage implements Listener {
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
    public void ArmorEquip(ArmorEquipEvent e) {
        //Store the set number
        String setNumber;
        //Store the player from the event
        Player p = e.getPlayer();
        //Verify that the player is wearing armor with meta & lore
        if (e.getNewArmorPiece().hasItemMeta()) {
            if (e.getNewArmorPiece().getItemMeta().hasLore()) {
                //Check to see if the armor is part of a full set
                if (InventoryArmorCheck.checkEquipArmor(p, lcf,
                        e.getNewArmorPiece().getItemMeta().getLore(), e.getNewArmorPiece().getType())) {
                    //Get the set number
                    setNumber = GetSetNumber.setNumber(e.getNewArmorPiece().getItemMeta().getLore(), lcf);
                    //Send the player the equip message for that set
                    for (String line : lcf.getMessages().getStringList(setNumber + ".equip-message")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                    }
                }
            }
        }
    }
}