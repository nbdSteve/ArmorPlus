package gg.steve.mc.ap.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BasicSetData implements SetData {
    private SetDataType type;
    private double increase;
    private double reduction;
    private double kb;
    private double health;

    public BasicSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.BASIC;
        this.increase = section.getDouble(entry + ".damage-increase");
        this.reduction = section.getDouble(entry + ".damage-decrease");
        this.kb = section.getDouble(entry + ".kb");
        this.health = section.getDouble(entry + ".health");
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        if (this.increase != -1) {
            event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * this.increase);
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (this.reduction != -1) {
            event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * this.reduction);
        }
        if (this.kb != -1) {
            event.getEntity().setVelocity(event.getDamager().getLocation().getDirection().setY(0).normalize().multiply(this.kb));
        }
    }

    @Override
    public void onApply(Player player) {
        if (this.health != -1) {
            player.setMaxHealth(this.health);
        }
    }

    @Override
    public void onRemoval(Player player) {
        if (this.health != -1) {
            player.setMaxHealth(20.0);
        }
    }

    @Override
    public void onFall(EntityDamageEvent event) {

    }

    @Override
    public void onHungerDeplete(FoodLevelChangeEvent event) {

    }

    // <-- Getters and Setters from this point on -->
    public SetDataType getType() {
        return type;
    }

    public void setType(SetDataType type) {
        this.type = type;
    }

    public double getIncrease() {
        return increase;
    }

    public void setIncrease(double increase) {
        this.increase = increase;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public double getKb() {
        return kb;
    }

    public void setKb(double kb) {
        this.kb = kb;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
