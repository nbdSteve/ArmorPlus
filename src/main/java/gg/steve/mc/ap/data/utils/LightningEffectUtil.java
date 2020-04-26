package gg.steve.mc.ap.data.utils;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
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
    private ConfigurationSection section;
    private String entry;
    private int timesStriked;
    private BukkitTask task;
    private List<UUID> messaged;

    public LightningEffectUtil(boolean randomRadius, double radius, int totalStrikes, long delay, double damage, ConfigurationSection section, String entry) {
        this.randomRadius = randomRadius;
        this.radius = radius;
        this.totalStrikes = totalStrikes;
        this.delay = delay;
        this.damage = damage;
        this.section = section;
        this.entry = entry;
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
                    SoundUtil.playSound(this.section, this.entry, player);
                    MessageType.doAttackedMessage(this.section, this.entry, player);
                    messaged.add(player.getUniqueId());
                }
            }
            this.timesStriked++;
        }, 0L, this.delay);
        return this.task;
    }

    // <-- Getters and Setters from this point on -->
    public boolean isRandomRadius() {
        return randomRadius;
    }

    public void setRandomRadius(boolean randomRadius) {
        this.randomRadius = randomRadius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getTotalStrikes() {
        return totalStrikes;
    }

    public void setTotalStrikes(int totalStrikes) {
        this.totalStrikes = totalStrikes;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public void setSection(ConfigurationSection section) {
        this.section = section;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public int getTimesStriked() {
        return timesStriked;
    }

    public void setTimesStriked(int timesStriked) {
        this.timesStriked = timesStriked;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public List<UUID> getMessaged() {
        return messaged;
    }

    public void setMessaged(List<UUID> messaged) {
        this.messaged = messaged;
    }
}