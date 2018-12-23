package com.nbdsteve.carmor.event;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EquipMessage implements Listener {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    @EventHandler
    public void ArmorEquip(ArmorEquipEvent e) {
        String setNumber;
        Player p = e.getPlayer();
        if (e.getNewArmorPiece().hasItemMeta()) {
            if (e.getNewArmorPiece().getItemMeta().hasLore()) {
                if (InventoryArmorCheck.checkArmor(p, lcf)) {
                    setNumber = GetSetNumber.setNumber(p.getInventory().getHelmet().getItemMeta().getLore(), lcf);
                    for (String line : lcf.getMessages().getStringList(setNumber + ".equip-message")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                    }
                }
            }
        }
    }
}
