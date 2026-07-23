package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.player.PlayerArmorSetService;
import gg.steve.mc.ap.player.SetPlayer;
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
    private final ArmorSetCatalog catalog;
    private final PlayerArmorSetService playerArmorSetService;

    public ArmorBuffListener(ArmorSetCatalog catalog, PlayerArmorSetService playerArmorSetService) {
        this.catalog = catalog;
        this.playerArmorSetService = playerArmorSetService;
    }

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
        if (!playerArmorSetService.isWearingSet(damager)) {
            if (damager.getItemInHand() == null || damager.getItemInHand().getType().equals(Material.AIR))
                return;
            NBTItem nbtItem = new NBTItem(damager.getItemInHand());
            if (nbtItem.getString("armor+.set").equalsIgnoreCase("")) return;
            Set set = catalog.getSet(nbtItem.getString("armor+.set"));
            set.getHandData().hitWithoutSetBuff(event, damager);
            return;
        }
        SetPlayer player = playerArmorSetService.getSetPlayer(damager);
        player.getSet().onHit(event, damager);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!playerArmorSetService.isWearingSet((Player) event.getEntity())) return;
        SetPlayer player = playerArmorSetService.getSetPlayer((Player) event.getEntity());
        player.getSet().onDamage(event);
    }

    @EventHandler
    public void fall(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!playerArmorSetService.isWearingSet((Player) event.getEntity())) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        SetPlayer player = playerArmorSetService.getSetPlayer((Player) event.getEntity());
        player.getSet().onFall(event);
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!playerArmorSetService.isWearingSet((Player) event.getEntity())) return;
        SetPlayer player = playerArmorSetService.getSetPlayer((Player) event.getEntity());
        player.getSet().onHungerDeplete(event);
    }

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (!playerArmorSetService.isWearingSet(killer)) return;
        SetPlayer player = playerArmorSetService.getSetPlayer(killer);
        player.getSet().onTargetDeath(event, killer);
    }
}
