package gg.steve.mc.ap.data.utils;

import gg.steve.mc.ap.ArmorPlus;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LightningEffectUtil {
    private boolean randomRadius;
    private double radius;
    private int totalStrikes;
    private long delay;
    private double damage;
    private int timesStriked;
    private BukkitTask task;
    private List<UUID> messaged;

    public LightningEffectUtil(boolean randomRadius, double radius, int totalStrikes, long delay, double damage) {
        this.randomRadius = randomRadius;
        this.radius = radius;
        this.totalStrikes = totalStrikes;
        this.delay = delay;
        this.damage = damage;
        this.timesStriked = 0;
        this.task = null;
        this.messaged = new ArrayList<>();
    }

    public BukkitTask doHitTask(EntityDamageByEntityEvent event) {
        if (this.randomRadius) {
            this.radius = Math.random() * this.radius + 1;
        }
        this.task = Bukkit.getScheduler().runTaskTimer(ArmorPlus.get(), () -> {
            if (this.timesStriked >= this.totalStrikes) {
                this.task.cancel();
                return;
            }
            World world = event.getEntity().getWorld();
            for (Entity entity : event.getEntity().getNearbyEntities(this.radius, this.radius, this.radius)) {
                if (entity.getUniqueId().equals(event.getEntity().getUniqueId())) continue;
                if (!(entity instanceof Player)) continue;
                Player player = (Player) entity;
                EntityDamageByEntityEvent strikeEvent = new EntityDamageByEntityEvent(event.getEntity(), player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, this.damage);
                Bukkit.getPluginManager().callEvent(strikeEvent);
                if (strikeEvent.isCancelled()) continue;
                world.strikeLightningEffect(entity.getLocation());
                player.damage(this.damage, event.getEntity());
                player.setVelocity(player.getVelocity().subtract(player.getVelocity()));
                if (!messaged.contains(player.getUniqueId())) {
                    messaged.add(player.getUniqueId());
                }
            }
            this.timesStriked++;
        }, 0L, this.delay);
        return this.task;
    }
}
