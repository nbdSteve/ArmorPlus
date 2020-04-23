package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        String setName = null;
        for (String key : SetManager.getSets().keySet()) {
            if (args[0].equalsIgnoreCase("/" + key)) {
                setName = key;
                event.setCancelled(true);
            }
        }
        if (setName == null) return;
        Set set = SetManager.getSet(setName);
        if (args.length == 1) {
            set.openGui(event.getPlayer());
            return;
        }
        if (args[1].equalsIgnoreCase("g") || args[1].equalsIgnoreCase("give")) {
            for (ItemStack piece : set.getSetPieces().values()) {
                event.getPlayer().getInventory().addItem(piece);
            }
        }
    }
}