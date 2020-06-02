package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.armorequipevent.ArmorEquipEvent;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.player.SetPlayerManager;
import gg.steve.mc.ap.utils.CommandUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEquipListener implements Listener {

    @EventHandler
    public void equip(ArmorEquipEvent event) {
        if (event.isCancelled()) return;
        if (event.getNewArmorPiece() == null || event.getNewArmorPiece().getType().equals(Material.AIR)) {
            return;
        }
        NBTItem nbtItem = new NBTItem(event.getNewArmorPiece());
        if (nbtItem.getString("armor+.set").equalsIgnoreCase("")) return;
        if (ConfigManager.CONFIG.get().getStringList("head-items").contains(nbtItem.getItem().getType().toString().toLowerCase())) {
            event.getPlayer().getInventory().setHelmet(event.getNewArmorPiece());
            if (event.getMethod().equals(ArmorEquipEvent.EquipMethod.PICK_DROP) || event.getMethod().equals(ArmorEquipEvent.EquipMethod.SHIFT_CLICK)) {
                if (event.getOldArmorPiece() != null && !event.getOldArmorPiece().getType().equals(Material.AIR)) {
                    event.getPlayer().setItemOnCursor(event.getOldArmorPiece());
                } else {
                    event.setCancelled(true);
                    event.getPlayer().setItemOnCursor(new ItemStack(Material.AIR));
                }
            } else if (event.getMethod().equals(ArmorEquipEvent.EquipMethod.HOTBAR_SWAP)) {
                if (event.getOldArmorPiece() != null && !event.getOldArmorPiece().getType().equals(Material.AIR)) {
                    event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), event.getOldArmorPiece());
                } else {
                    event.setCancelled(true);
                    event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                }
            }
        }
        String setName = nbtItem.getString("armor+.set");
        Set set = SetManager.getSet(setName);
        Piece piece = null;
        for (Piece pieces : set.getSetPieces().keySet()) {
            if (event.getNewArmorPiece().getType().equals(set.getPiece(pieces).getType())) piece = pieces;
        }
        if (piece != null) {
            CommandUtil.execute(set.getConfig().getStringList("set-pieces." + piece.name().toLowerCase() + ".commands.apply"), event.getPlayer());
        }
        if (set.isWearingSet(event.getPlayer(), event.getType(), event.getNewArmorPiece())) {
            SetPlayerManager.addSetPlayer(event.getPlayer(), setName);
            set.apply(event.getPlayer());
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        for (Set set : SetManager.getSets().values()) {
            if (!set.isWearingSet(event.getPlayer(), null, null)) continue;
            SetPlayerManager.addSetPlayer(event.getPlayer(), set.getName());
            set.apply(event.getPlayer());
            return;
        }
    }
}
