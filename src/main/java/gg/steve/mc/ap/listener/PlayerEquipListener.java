package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.armorequipevent.ArmorEquipEvent;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.player.SetPlayerManager;
import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEquipListener implements Listener {

    @EventHandler
    public void equip(ArmorEquipEvent event) {
        if (event.isCancelled()) return;
        if (event.getNewArmorPiece() == null || event.getNewArmorPiece().getType().equals(Material.AIR))
            return;
        NBTItem nbtItem = new NBTItem(event.getNewArmorPiece());
        if (nbtItem.getString("armor+.set").equalsIgnoreCase("")) return;
        String setName = nbtItem.getString("armor+.set");
        Set set = SetManager.getSet(setName);
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
