package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.armorequipevent.ArmorEquipEvent;
import gg.steve.mc.ap.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerUnequipListener implements Listener {

    @EventHandler
    public void unequip(ArmorEquipEvent event) {
        if (event.isCancelled()) return;
        if (event.getOldArmorPiece() == null || event.getOldArmorPiece().getType().equals(Material.AIR)) return;
        NBTItem nbtItem = new NBTItem(event.getOldArmorPiece());
        if (nbtItem.getString("armor+.set") == null) return;
        String name = nbtItem.getString("armor+.set");
        Set set = SetManager.getSet(name);
        if (set.hadFullSet(event.getPlayer(), event.getType(), event.getOldArmorPiece())) {
            set.remove(event.getPlayer());
        }
    }
}
