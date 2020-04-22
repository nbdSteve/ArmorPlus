package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.armorequipevent.ArmorEquipEvent;
import gg.steve.mc.ap.nbt.NBTItem;
import org.bukkit.event.EventHandler;

public class PlayerUnequipListener {

    @EventHandler
    public void equip(ArmorEquipEvent event) {
        if (event.isCancelled()) return;
        NBTItem nbtItem = new NBTItem(event.getNewArmorPiece());
        if (nbtItem.getString("armor+.set") == null) return;
        String name = nbtItem.getString("armor+.set");
        SetManager.getSet(name).apply(event.getPlayer());
    }
}
