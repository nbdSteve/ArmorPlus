package dev.nuer.ca.gui;

import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.gui.GenerateArmorSet;
import dev.nuer.ca.method.gui.GenerateReturnButton;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

/**
 * Generic class to create a gui for the desired armor set
 */
public class GeneralSetGui {

    /**
     * Constructor the create the gui
     *
     * @param setNumber the armor set to add to the gui
     * @param lcf       LoadCarmorFiles instance
     * @param pl        Plugin instance
     * @param p         the Player opening the gui
     */
    public GeneralSetGui(String setNumber, LoadCarmorFiles lcf, Plugin pl, Player p) {
        //Create the gui
        Inventory inven = pl.getServer().createInventory(null, lcf.getArmorGui().getInt(setNumber + ".size"),
                ChatColor.translateAlternateColorCodes('&',
                        lcf.getArmorGui().getString(setNumber + ".name")));
        //Create the return button
        new GenerateReturnButton(setNumber, lcf, inven);
        //Create the armor set
        new GenerateArmorSet(setNumber, lcf, inven);
        //Open the new inventory
        p.openInventory(inven);
    }
}
