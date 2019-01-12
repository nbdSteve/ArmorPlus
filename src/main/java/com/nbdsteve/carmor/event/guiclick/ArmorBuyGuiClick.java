package com.nbdsteve.carmor.event.guiclick;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.gui.MainGui;
import com.nbdsteve.carmor.method.gui.CraftArmorPieceForPlayer;
import com.nbdsteve.carmor.method.gui.GenerateReturnButton;
import com.nbdsteve.carmor.method.gui.GetPiecePrice;
import com.nbdsteve.carmor.method.message.SendMessage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

/**
 * Event called when the player wants to buy a piece of armor
 */
public class ArmorBuyGuiClick implements Listener {
    private static Economy econ = Carmor.getEconomy();
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    @EventHandler
    public void armorGuiBuyClick(InventoryClickEvent e) {
        //Store the player
        Player p = (Player) e.getWhoClicked();
        //Store the inventory
        Inventory inven = e.getClickedInventory();
        //Check that it is the correct gui
        String setNumber = getGuiSetNumber(inven);
        if (setNumber == null) {
            return;
        }
        //Cancel the click event
        e.setCancelled(true);
        //If the server has an economy try give the player the item
        if (e.getCurrentItem().hasItemMeta()) {
            if (e.getCurrentItem().getItemMeta().hasLore()) {
                //If the item clicked is the back button return to the main gui
                if (e.getCurrentItem().getItemMeta().equals(GenerateReturnButton.getButtonMeta())) {
                    MainGui mainMenu = new MainGui();
                    mainMenu.mainGui(p);
                    return;
                }
                //Withdraw the money from the player
                if (econ != null) {
                    String itemType = e.getCurrentItem().getType().toString().toLowerCase();
                    if (econ.getBalance(p) >= GetPiecePrice.getPiecePrice(setNumber, itemType, lcf)) {
                        econ.withdrawPlayer(p, GetPiecePrice.getPiecePrice(setNumber, itemType, lcf));
                        new CraftArmorPieceForPlayer(setNumber, itemType, p, lcf);
                        new SendMessage("purchase", p, lcf);
                    } else {
                        new SendMessage("insufficient-funds", p, lcf);
                        p.closeInventory();
                    }
                } else {
                    new SendMessage("no-economy", p, lcf);
                }
            }
        }
    }

    /**
     * Method to get the number of the armor set from the gui
     *
     * @param inventory the inventory to check
     * @return String
     */
    private String getGuiSetNumber(Inventory inventory) {
        String setNumber = null;
        for (int i = 0; i <= 54; i++) {
            String temp = "armor-set-" + i;
            try {
                if (inventory.getName().equals(ChatColor.translateAlternateColorCodes('&',
                        lcf.getArmorGui().getString(temp + ".name")))) {
                    setNumber = temp;
                }
            } catch (Exception e) {
                //Do nothing it just isn't that Gui.
            }
        }
        return setNumber;
    }
}