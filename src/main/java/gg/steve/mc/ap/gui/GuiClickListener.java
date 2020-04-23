package gg.steve.mc.ap.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GuiClickListener implements Listener {

    /**
     * Event called when the player clicks an item in the Gui
     *
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void guiItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (AbstractGui.openInventories.get(player.getUniqueId()) != null) {
            event.setCancelled(true);
            AbstractGui gui =
                    AbstractGui.getInventoriesByID().get(AbstractGui.openInventories.get(player.getUniqueId()));
            AbstractGui.inventoryClickActions clickAction = gui.getClickActions().get(event.getSlot());
            if (clickAction != null) {
                clickAction.itemClick(player);
            }
        }
    }

    /**
     * Event called when the player closes a Gui, void method to remove them from the openInventories map
     *
     * @param event InventoryCloseEvent
     */
    @EventHandler
    public void guiClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        AbstractGui.openInventories.remove(player.getUniqueId());
    }

    /**
     * Event called when the player leaves the server, void method to remove them from the openInventories map
     *
     * @param event PlayerQuitEvent
     */
    @EventHandler
    public void guiCloseByQuit(PlayerQuitEvent event) {
        AbstractGui.openInventories.remove(event.getPlayer().getUniqueId());
    }
}
