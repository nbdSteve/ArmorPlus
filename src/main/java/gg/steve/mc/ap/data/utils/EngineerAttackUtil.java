package gg.steve.mc.ap.data.utils;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.LogUtil;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class EngineerAttackUtil implements Listener {
    private boolean randomRadius;
    private double radius, damage, height;
    private int total, tntDropped, fuseDelay;
    private long delay;
    private ConfigurationSection section;
    private String entry;
    private BukkitTask task;
    private List<UUID> messaged;
    private static Map<UUID, List<Object>> tnt = new HashMap<>();

    public EngineerAttackUtil() {

    }

    public EngineerAttackUtil(boolean randomRadius, double radius, double damage, double height, int total, int fuseDelay, long delay, ConfigurationSection section, String entry) {
        this.randomRadius = randomRadius;
        this.radius = radius;
        this.damage = damage;
        this.height = height;
        this.total = total;
        this.fuseDelay = fuseDelay;
        this.delay = delay;
        this.section = section;
        this.entry = entry;
        this.task = null;
        this.messaged = new ArrayList<>();
    }

    public BukkitTask doDropTask(EntityDamageByEntityEvent event) {
        if (this.randomRadius) {
            this.radius = Math.random() * this.radius + 1;
        }
        this.task = Bukkit.getScheduler().runTaskTimer(ArmorPlus.get(), () -> {
            if (this.tntDropped >= this.total) {
                this.task.cancel();
                return;
            }
            World world = event.getEntity().getWorld();
            for (Entity entity : event.getEntity().getNearbyEntities(this.radius, this.radius, this.radius)) {
                if (entity.getUniqueId().equals(event.getEntity().getUniqueId())) continue;
                if (!(entity instanceof Player)) continue;
                Player player = (Player) entity;
                EntityDamageByEntityEvent tntEvent = new EntityDamageByEntityEvent(event.getEntity(), player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, this.damage);
                Bukkit.getPluginManager().callEvent(tntEvent);
                if (tntEvent.isCancelled()) continue;
                TNTPrimed primed = (TNTPrimed) world.spawnEntity(entity.getLocation().add(0, height, 0), EntityType.PRIMED_TNT);
                tnt.put(primed.getUniqueId(), new ArrayList<>());
                tnt.get(primed.getUniqueId()).add(player.getUniqueId());
                tnt.get(primed.getUniqueId()).add(event.getEntity());
                tnt.get(primed.getUniqueId()).add(this.damage);
                primed.setFuseTicks(this.fuseDelay);
                if (!messaged.contains(player.getUniqueId())) {
                    SoundUtil.playSound(this.section, this.entry, player);
                    MessageType.doAttackedMessage(this.section, this.entry, player);
                    messaged.add(player.getUniqueId());
                }
            }
            this.tntDropped++;
        }, 0L, this.delay);
        return this.task;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void explode(EntityExplodeEvent event) {
        if (tnt == null) return;
        if (!tnt.containsKey(event.getEntity().getUniqueId())) return;
        event.blockList().clear();
        Player player = Bukkit.getPlayer((UUID) tnt.get(event.getEntity().getUniqueId()).get(0));
        if (player != null) {
            player.damage((double) tnt.get(event.getEntity().getUniqueId()).get(2), (Entity) tnt.get(event.getEntity().getUniqueId()).get(1));
            player.setVelocity(player.getVelocity().subtract(player.getVelocity()));
        }
//        tnt.remove(event.getEntity().getUniqueId());
    }
}
