package com.nbdsteve.carmor.gui.method;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a piece of armor
 */
public class CraftArmorPieceForPlayer {

    /**
     * Call this method to create a piece of armor and give it to the player
     *
     * @param setNumber the armor set
     * @param itemType  the type of item i.e. helmet
     * @param p         the player
     * @param lcf       LoadCarmorFiles instance
     */
    public CraftArmorPieceForPlayer(String setNumber, String itemType, Player p, LoadCarmorFiles lcf) {
        String[] item = itemType.split("_");
        String armorPiece = setNumber + "." + item[1];
        //Store the information about the armor set
        String[] armorPieceParts = lcf.getArmorGui().getString(armorPiece).split(":");
        //Create the item
        ItemStack piece = new ItemStack(Material.valueOf(armorPieceParts[0].toUpperCase()));
        //Store the item meta
        ItemMeta pieceMeta = piece.getItemMeta();
        //Create a new array list to create the lore
        List<String> pieceLore = new ArrayList<>();
        //Set the display name of the item
        pieceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                lcf.getArmor().getString(armorPiece + ".name")));
        //Add the regular lore
        for (String lore : lcf.getArmor().getStringList(armorPiece + ".lore")) {
            pieceLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        //Add the regular enchantments
        for (String ench : lcf.getArmor().getStringList(armorPiece + ".enchantments")) {
            String[] parts = ench.split(":");
            pieceMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()),
                    Integer.parseInt(parts[1]),
                    true);
        }
        //Set the new lore
        pieceMeta.setLore(pieceLore);
        //Set the new item meta
        piece.setItemMeta(pieceMeta);
        //Give the piece to the player
        p.getInventory().addItem(piece);
    }
}
