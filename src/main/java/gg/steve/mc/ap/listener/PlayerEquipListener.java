package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.armorequipevent.ArmorEquipEvent;
import gg.steve.mc.ap.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEquipListener implements Listener {

    @EventHandler
    public void equip(ArmorEquipEvent event) {
        if (event.isCancelled()) return;
        if (event.getNewArmorPiece() == null || event.getNewArmorPiece().getType().equals(Material.AIR)) return;
        NBTItem nbtItem = new NBTItem(event.getNewArmorPiece());
        if (nbtItem.getString("armor+.set") == null) return;
        String name = nbtItem.getString("armor+.set");
        Set set = SetManager.getSet(name);
        if (set.hasFullSet(event.getPlayer())) {
            set.apply(event.getPlayer());
        }
    }
}
