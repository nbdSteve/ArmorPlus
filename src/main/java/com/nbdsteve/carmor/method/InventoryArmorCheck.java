package com.nbdsteve.carmor.method;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Class containing static methods used to check a players armor contents
 */
public class InventoryArmorCheck {

    /**
     * Check if the player is wearing a full set of custom armor, if so return true
     * otherwise return false
     *
     * @param p   the player to check
     * @param lcf the file configuration
     * @return boolean
     */
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
            e.printStackTrace();
            System.out.println("exception caught");
            return false;
        }
        String setNumber = GetSetNumber.setNumber(helmLore, lcf);
        if (setNumber == null) {
            System.out.println("set number == null");
            return false;
        }
        String unique = ChatColor.translateAlternateColorCodes('&', lcf.getArmor().getString(setNumber +
                ".unique"));
        if (helmLore.contains(unique) && chestLore.contains(unique)
                && leggingLore.contains(unique) && bootLore.contains(unique)) {
            return true;
        }
        System.out.println("not found in lore");
        return false;
    }

    /**
     * Check if the player is wearing a full set of custom armor, if so return true
     * otherwise return false. This is called for the equip event since the armor piece
     * doesn't register when the event is called
     *
     * @param p   the player to check
     * @param lcf the file configuration
     * @param newPieceLore the lore of the new armor piece
     * @param newPieceMaterial the material that the new armor piece is
     * @return boolean
     */
    public static boolean checkEquipArmor(Player p, LoadCarmorFiles lcf, List<String> newPieceLore,
                                          Material newPieceMaterial) {
        //Store the lore for each armor piece
        List<String> helmLore;
        List<String> chestLore;
        List<String> leggingLore;
        List<String> bootLore;
        //Has to be this way because the event is called before the armor is on the player
        //Running the above methods causes an exception to be thrown
        if (newPieceMaterial.equals(Material.DIAMOND_HELMET)) {
            helmLore = newPieceLore;
        } else {
            try {
                helmLore = p.getInventory().getHelmet().getItemMeta().getLore();
            } catch (Exception e) {
                return false;
            }
        }
        if (newPieceMaterial.equals(Material.DIAMOND_CHESTPLATE)) {
            chestLore = newPieceLore;
        } else {
            try {
                chestLore = p.getInventory().getChestplate().getItemMeta().getLore();
            } catch (Exception e) {
                return false;
            }
        }
        if (newPieceMaterial.equals(Material.DIAMOND_LEGGINGS)) {
            leggingLore = newPieceLore;
        } else {
            try {
                leggingLore = p.getInventory().getLeggings().getItemMeta().getLore();
            } catch (Exception e) {
                return false;
            }
        }
        if (newPieceMaterial.equals(Material.DIAMOND_BOOTS)) {
            bootLore = newPieceLore;
        } else {
            try {
                bootLore = p.getInventory().getBoots().getItemMeta().getLore();
            } catch (Exception e) {
                return false;
            }
        }
        String setNumber = GetSetNumber.setNumber(helmLore, lcf);
        if (setNumber == null) {
            return false;
        }
        String unique = ChatColor.translateAlternateColorCodes('&', lcf.getArmor().getString(setNumber +
                ".unique"));
        if (helmLore.contains(unique) && chestLore.contains(unique)
                && leggingLore.contains(unique) && bootLore.contains(unique)) {
            return true;
        }
        return false;
    }
}
