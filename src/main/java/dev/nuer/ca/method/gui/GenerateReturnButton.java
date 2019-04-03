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
 * Class that will render the button to return to the main gui
 */
public class GenerateReturnButton {
    //Store the item meta of the button
    private static ItemMeta itemMeta;

    /**
     * Create the return button in the given Gui
     *
     * @param setNumber armor set gui to create it in
     * @param lcf       LoadCarmorFiles instance
     * @param setGui    the gui to render it in
     */
    public GenerateReturnButton(String setNumber, LoadCarmorFiles lcf, Inventory setGui) {
        //Create the item
        ItemStack item = new ItemStack(Material.valueOf(lcf.getArmorGui().getString("return-button.item").toUpperCase()));
        //Store the item meta
        itemMeta = item.getItemMeta();
        //Create a new array list to create the lore
        List<String> itemLore = new ArrayList<>();
        //Set the display name of the item
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', lcf.getArmorGui().getString("return-button.name")));
        //Add the regular lore
        for (String loreLine : lcf.getArmorGui().getStringList("return-button.lore")) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        //If the button is glowing make it
        if (lcf.getArmorGui().getBoolean("return-button.glowing")) {
            itemMeta.addEnchant(Enchantment.LURE, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        //Set the new lore
        itemMeta.setLore(itemLore);
        //Set the new item meta
        item.setItemMeta(itemMeta);
        //Add it to the gui
        setGui.setItem(lcf.getArmorGui().getInt(setNumber + ".return-button-slot"), item);
    }

    /**
     * Static method to get the button meta
     *
     * @return ItemMeta
     */
    public static ItemMeta getButtonMeta() {
        return itemMeta;
    }
}
