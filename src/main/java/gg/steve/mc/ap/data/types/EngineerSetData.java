package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.data.SetDataType;
import gg.steve.mc.ap.data.utils.EngineerAttackUtil;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EngineerSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private boolean randomRadius;
    private double radius, damage, chance, height;
    private int total, fuseDelay;
    private long delay;

    public EngineerSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.ENGINEER;
        this.section = section;
        this.entry = entry;
        this.randomRadius = section.getBoolean(entry + ".random-radius");
        this.radius = section.getDouble(entry + ".radius");
        this.total = section.getInt(entry + ".total-tnt");
        this.fuseDelay = section.getInt(entry + ".fuse-delay");
        this.delay = section.getLong(entry + ".drop-delay-ticks");
        this.damage = section.getDouble(entry + ".damage-per-tnt");
        this.height = section.getDouble(entry + ".height-above-player");
        this.chance = section.getDouble(entry + ".chance");
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void onRemoval(Player player) {

    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, Player damager) {

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (Math.random() * 1 > this.chance) return;
        new EngineerAttackUtil(this.randomRadius, this.radius, this.damage, this.height, this.total, this.fuseDelay, this.delay, this.section, this.entry).doDropTask(event);
        SoundUtil.playSound(this.section, this.entry, (Player) event.getEntity());
        MessageType.doAttackerMessage(this.section, this.entry, (Player) event.getEntity());
    }

    @Override
    public void onFall(EntityDamageEvent event) {

    }

    @Override
    public void onHungerDeplete(FoodLevelChangeEvent event) {

    }

    @Override
    public void onTargetDeath(EntityDeathEvent event, Player killer) {

    }

    // <-- Getters and Setters from this point on -->
    public SetDataType getType() {
        return type;
    }

    public void setType(SetDataType type) {
        this.type = type;
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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}