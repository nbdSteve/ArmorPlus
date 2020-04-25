package gg.steve.mc.ap.data;

import gg.steve.mc.ap.data.utils.LightningEffectUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LightningSetData implements SetData {
    private boolean randomRadius;
    private double radius;
    private int totalStrikes;
    private long delay;
    private double damage;

    public LightningSetData(boolean randomRadius, double radius, int totalStrikes, long delay, double damage) {
        this.randomRadius = randomRadius;
        this.radius = radius;
        this.totalStrikes = totalStrikes;
        this.delay = delay;
        this.damage = damage;
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
        new LightningEffectUtil(this.randomRadius, this.radius, this.totalStrikes, this.delay, this.damage).doHitTask(event);
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
}
