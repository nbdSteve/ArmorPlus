package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.player.SetPlayer;
import gg.steve.mc.ap.player.SetPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ArmorBuffListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        Player damager = null;
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                damager = (Player) arrow.getShooter();
            }
        }
        if (!(event.getDamager() instanceof Player) && damager == null) return;
        if (damager == null) {
            damager = (Player) event.getDamager();
        }
        if (!SetPlayerManager.isWearingSet(damager)) {
            if (damager.getItemInHand() == null || damager.getItemInHand().getType().equals(Material.AIR))
                return;
            NBTItem nbtItem = new NBTItem(damager.getItemInHand());
            if (nbtItem.getString("armor+.set").equalsIgnoreCase("")) return;
            Set set = SetManager.getSet(nbtItem.getString("armor+.set"));
            set.getHandData().hitWithoutSetBuff(event, damager);
            return;
        }
        SetPlayer player = SetPlayerManager.getSetPlayer(damager);
        player.getSet().onHit(event, damager);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!SetPlayerManager.isWearingSet((Player) event.getEntity())) return;
        SetPlayer player = SetPlayerManager.getSetPlayer((Player) event.getEntity());
        player.getSet().onDamage(event);
    }

    @EventHandler
    public void fall(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!SetPlayerManager.isWearingSet((Player) event.getEntity())) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        SetPlayer player = SetPlayerManager.getSetPlayer((Player) event.getEntity());
        player.getSet().onFall(event);
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!SetPlayerManager.isWearingSet((Player) event.getEntity())) return;
        SetPlayer player = SetPlayerManager.getSetPlayer((Player) event.getEntity());
        player.getSet().onHungerDeplete(event);
    }

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (!SetPlayerManager.isWearingSet(killer)) return;
        SetPlayer player = SetPlayerManager.getSetPlayer(killer);
        player.getSet().onTargetDeath(event, killer);
    }
}
