package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.player.SetPlayer;
import gg.steve.mc.ap.player.SetPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ArmorBuffListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player)) return;
        if (!SetPlayerManager.isWearingSet((Player) event.getDamager())) return;
        SetPlayer player = SetPlayerManager.getSetPlayer((Player) event.getDamager());
        player.getSet().onHit(event);
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
}
