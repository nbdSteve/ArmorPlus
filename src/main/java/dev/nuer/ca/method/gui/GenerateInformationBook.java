package dev.nuer.ca.method.gui;

import dev.nuer.ca.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that will render the information item in the main Gui
 */
public class GenerateInformationBook {

    /**
     * Render the information item in the main Gui
     *
     * @param inventory the inventory to render the book in
     * @param lcf       LoadCarmorFiles instance
     */
    public GenerateInformationBook(Inventory inventory, LoadCarmorFiles lcf) {
        //Create the item
        ItemStack item = new ItemStack(Material.valueOf(lcf.getMainGui().getString("information-item.item").toUpperCase()));
        //Store the item meta
        ItemMeta itemMeta = item.getItemMeta();
        //Create a new array list to create the lore
        List<String> itemLore = new ArrayList<>();
        //Set the display name of the item
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', lcf.getMainGui().getString("information-item.name")));
        //Add the regular lore
        for (String loreLine : lcf.getMainGui().getStringList("information-item.lore")) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        //If the button is glowing make it
        if (lcf.getMainGui().getBoolean("information-item.glowing")) {
            itemMeta.addEnchant(Enchantment.LURE, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        //Set the new lore
        itemMeta.setLore(itemLore);
        //Set the new item meta
        item.setItemMeta(itemMeta);
        //Add it to the gui
        inventory.setItem(lcf.getMainGui().getInt("information-item.slot"), item);
    }
}
