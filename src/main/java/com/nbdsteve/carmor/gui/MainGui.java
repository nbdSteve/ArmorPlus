package com.nbdsteve.carmor.gui;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.gui.GenerateInformationBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Class handling the main gui when the players runs the /ca command
 */
public class MainGui {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    /**
     * Create the main gui
     *
     * @param p the Player, cannot be null
     */
    public void mainGui(Player p) {
        //Create the gui
        Inventory inven = pl.getServer().createInventory(null, lcf.getMainGui().getInt("size"),
                ChatColor.translateAlternateColorCodes('&', lcf.getMainGui().getString("name")));
        //Add the information book to the Gui
        if (lcf.getMainGui().getBoolean("information-item.in-gui")) {
            new GenerateInformationBook(inven, lcf);
        }
        //Create the icons for the different armor sets
        for (int i = 0; i <= 54; i++) {
            String set = "armor-set-" + i;
            if (lcf.getArmorGui().getBoolean(set + ".in-gui")) {
                //Create the armor set icon
                ItemStack icon = new ItemStack(Material.valueOf(lcf.getArmorGui().getString(set + ".main" +
                        "-gui-icon.item").toUpperCase()));
                ItemMeta iconMeta = icon.getItemMeta();
                //Set the icons display name
                iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        lcf.getArmorGui().getString(set + ".main-gui-icon.name")));
                //Create and set the icons lore
                List<String> iconLore = new ArrayList<>();
                for (String lore : lcf.getArmorGui().getStringList(set + ".main-gui-icon.lore")) {
                    iconLore.add(ChatColor.translateAlternateColorCodes('&', lore));
                }
                //If the user wants the item to appear enchanted run this code
                if (lcf.getArmorGui().getBoolean(set + ".main-gui-icon.glowing")) {
                    iconMeta.addEnchant(Enchantment.LURE, 1, true);
                    iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                iconMeta.setLore(iconLore);
                icon.setItemMeta(iconMeta);
                //Add the icon to the gui
                inven.setItem(lcf.getArmorGui().getInt(set + ".main-gui-icon.slot"), icon);
            }
        }
        p.openInventory(inven);
    }
}
