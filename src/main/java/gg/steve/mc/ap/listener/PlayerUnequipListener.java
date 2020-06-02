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
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerUnequipListener implements Listener {

    @EventHandler
    public void unEquip(ArmorEquipEvent event) {
        if (event.isCancelled()) return;
        if (event.getOldArmorPiece() == null || event.getOldArmorPiece().getType().equals(Material.AIR))
            return;
        NBTItem nbtItem = new NBTItem(event.getOldArmorPiece());
        if (nbtItem.getString("armor+.set").equalsIgnoreCase("")) return;
        if (event.getNewArmorPiece() != null && !event.getNewArmorPiece().getType().equals(Material.AIR) && ConfigManager.CONFIG.get().getStringList("head-items").contains(event.getNewArmorPiece().getType().toString().toLowerCase())) {
            event.setCancelled(true);
        }
        String name = nbtItem.getString("armor+.set");
        Set set = SetManager.getSet(name);
        Piece piece = null;
        for (Piece pieces : set.getSetPieces().keySet()) {
            if (event.getOldArmorPiece().getType().equals(set.getPiece(pieces).getType())) piece = pieces;
        }
        if (piece != null) {
            CommandUtil.execute(set.getConfig().getStringList("set-pieces." + piece.name().toLowerCase() + ".commands.remove"), event.getPlayer());
        }
        if (set.isWearingSet(event.getPlayer(), event.getType(), event.getOldArmorPiece())) {
            SetPlayerManager.removeSetPlayer(event.getPlayer());
            set.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void join(PlayerQuitEvent event) {
        for (Set set : SetManager.getSets().values()) {
            if (!set.isWearingSet(event.getPlayer(), null, null)) continue;
            SetPlayerManager.removeSetPlayer(event.getPlayer());
            set.remove(event.getPlayer());
            return;
        }
    }
}
