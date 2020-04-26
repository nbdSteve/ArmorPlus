package gg.steve.mc.ap.data;

import gg.steve.mc.ap.data.utils.LightningEffectUtil;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class LightningSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private boolean randomRadius;
    private double radius;
    private int totalStrikes;
    private long delay;
    private double damage;
    private double chance;

    public LightningSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.LIGHTNING;
        this.section = section;
        this.entry = entry;
        this.randomRadius = section.getBoolean(entry + ".random-radius");
        this.radius = section.getDouble(entry + ".radius");
        this.totalStrikes = section.getInt(entry + ".total-strikes");
        this.delay = section.getLong(entry + ".strike-delay-ticks");
        this.damage = section.getDouble(entry + ".damage-per-strike");
        this.chance = section.getDouble(entry + ".chance");
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void onRemoval(Player player) {

    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (Math.random() * 1 > chance) return;
        new LightningEffectUtil(this.randomRadius, this.radius, this.totalStrikes, this.delay, this.damage, this.section, this.entry).doHitTask(event);
        SoundUtil.playSound(this.section, this.entry, (Player) event.getEntity());
        MessageType.doAttackerMessage(this.section, this.entry, (Player) event.getEntity());
    }

    @Override
    public void onFall(EntityDamageEvent event) {

    }

    @Override
    public void onHungerDeplete(FoodLevelChangeEvent event) {

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

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

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
}
