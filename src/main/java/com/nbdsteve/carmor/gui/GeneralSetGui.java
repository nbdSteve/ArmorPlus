package com.nbdsteve.carmor.gui;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class GeneralSetGui {

    public static void createGui(String setNumber, LoadCarmorFiles lcf, Plugin pl, Player p) {
        //Create the gui
        Inventory inven = pl.getServer().createInventory(null, lcf.getArmorGui().getInt(setNumber + ".size"),
                ChatColor.translateAlternateColorCodes('&',
                        lcf.getArmorGui().getString(setNumber + ".name")));
        //Create the armor set
        GenerateArmorPiece.generateHelmet(setNumber, lcf, inven);
        GenerateArmorPiece.generateChestplate(setNumber, lcf, inven);
        GenerateArmorPiece.generateLeggings(setNumber, lcf, inven);
        GenerateArmorPiece.generateBoots(setNumber, lcf, inven);
        p.openInventory(inven);
    }
}
