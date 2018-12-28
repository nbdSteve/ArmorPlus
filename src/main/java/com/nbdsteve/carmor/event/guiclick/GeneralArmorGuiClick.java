package com.nbdsteve.carmor.event.guiclick;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.gui.GeneralSetGui;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Class handling click events from the main gui
 */
public class GeneralArmorGuiClick implements Listener {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    /**
     * All code for the event is in this method, when the player clicks open up the respective armor gui
     *
     * @param e the event, cannot be null
     */
    @EventHandler
    public void guiClick(InventoryClickEvent e) {
        //Store the player
        Player p = (Player) e.getWhoClicked();
        //Store the inventory
        Inventory inven = e.getClickedInventory();
        if (inven != null) {
            if (inven.getName().equals(ChatColor.translateAlternateColorCodes('&',
                    lcf.getMainGui().getString("name")))) {
                e.setCancelled(true);
                if (e.getCurrentItem().hasItemMeta()) {
                    if (e.getCurrentItem().getItemMeta().hasLore()) {
                        ItemMeta iconMeta = e.getCurrentItem().getItemMeta();
                        List<String> iconLore = iconMeta.getLore();
                        String setNumber = null;
                        String perm = null;
                        for (int i = 0; i <= 54; i++) {
                            String set = "armor-set-" + String.valueOf(i);
                            try {
                                lcf.getArmorGui().getString(set + ".name");
                                if (iconMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', lcf.getArmorGui().getString(set + ".name")))) {
                                    perm = String.valueOf(i);
                                    setNumber = set;
                                }
                            } catch (Exception ex) {
                                //Do nothing, this set just doesn't exist.
                            }
                        }
                        if (setNumber == null) {
                            return;
                        }
                        if (p.hasPermission("carmor.gui." + perm)) {
                            new GeneralSetGui(setNumber, lcf, pl, p);
                        } else {
                            p.closeInventory();
                            for (String line : lcf.getMessages().getStringList("no-permission")) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        }
                    }
                }
            }
        }
    }
}