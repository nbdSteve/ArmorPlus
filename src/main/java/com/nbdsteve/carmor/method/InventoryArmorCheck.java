package com.nbdsteve.carmor.method;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.entity.Player;

import java.util.List;

public class InventoryArmorCheck {

    public static boolean checkArmor(Player p, LoadCarmorFiles lcf) {
        //Store the lore for each armor piece
        List<String> helmLore;
        List<String> chestLore;
        List<String> leggingLore;
        List<String> bootLore;
        //verify that the player is actually wearing a full set of armor
        try {
            helmLore = p.getInventory().getHelmet().getItemMeta().getLore();
            chestLore = p.getInventory().getChestplate().getItemMeta().getLore();
            leggingLore = p.getInventory().getLeggings().getItemMeta().getLore();
            bootLore = p.getInventory().getBoots().getItemMeta().getLore();
        } catch (Exception e) {
            return false;
        }
        String unique = GetSetNumber.setNumber(helmLore, lcf);
        if (unique == null) {
            return false;
        }
        if (helmLore.contains(unique) && chestLore.contains(unique)
                && leggingLore.contains(unique) && bootLore.contains(unique)) {
            return true;
        }
        return false;
    }
}
