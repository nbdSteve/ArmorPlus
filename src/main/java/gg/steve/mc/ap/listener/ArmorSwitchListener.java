package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armorequipevent.ArmorEquipEvent;
import gg.steve.mc.ap.armorequipevent.ArmorListener;
import gg.steve.mc.ap.armorequipevent.ArmorType;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorSwitchListener implements Listener {

    @EventHandler
    public void switchPiece(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)))
            return;
        if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) return;
        if (ArmorType.matchType(event.getItem()) == null) return;
        if (!ConfigManager.CONFIG.get().getBoolean("armor-switch.enabled")) return;
        ArmorType type = ArmorType.matchType(event.getItem());
        Player player = event.getPlayer();
        ArmorEquipEvent change;
        switch (type) {
            case HELMET:
                if (ArmorListener.isHeadItem(event.getItem())){
                    change = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR_SWAP, type, player.getInventory().getHelmet(), event.getItem());
                    Bukkit.getPluginManager().callEvent(change);
                    if (change.isCancelled()) return;
                    event.setCancelled(true);
                    player.getInventory().setHelmet(change.getNewArmorPiece());
                    player.setItemInHand(new ItemStack(Material.AIR));
                    return;
                }
                if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType().equals(Material.AIR)) return;
                change = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR_SWAP, type, player.getInventory().getHelmet(), event.getItem());
                Bukkit.getPluginManager().callEvent(change);
                if (change.isCancelled()) return;
                player.getInventory().setHelmet(change.getNewArmorPiece());
                break;
            case CHESTPLATE:
                if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType().equals(Material.AIR)) return;
                change = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR_SWAP, type, player.getInventory().getChestplate(), event.getItem());
                Bukkit.getPluginManager().callEvent(change);
                if (change.isCancelled()) return;
                player.getInventory().setChestplate(change.getNewArmorPiece());
                break;
            case LEGGINGS:
                if (player.getInventory().getLeggings() == null || player.getInventory().getLeggings().getType().equals(Material.AIR)) return;
                change = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR_SWAP, type, player.getInventory().getLeggings(), event.getItem());
                Bukkit.getPluginManager().callEvent(change);
                if (change.isCancelled()) return;
                player.getInventory().setLeggings(change.getNewArmorPiece());
                break;
            case BOOTS:
                if (player.getInventory().getBoots() == null || player.getInventory().getBoots().getType().equals(Material.AIR)) return;
                change = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR_SWAP, type, player.getInventory().getBoots(), event.getItem());
                Bukkit.getPluginManager().callEvent(change);
                if (change.isCancelled()) return;
                player.getInventory().setBoots(change.getNewArmorPiece());
                break;
            default:
                return;
        }
        SoundUtil.playSound(ConfigManager.CONFIG.get(),"armor-switch", player);
        MessageType.doProcMessage(ConfigManager.CONFIG.get(),"armor-switch", player);
        player.setItemInHand(change.getOldArmorPiece());
    }
}
