package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import sun.rmi.runtime.Log;

public class BasicSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private Set set;
    private double increase;
    private double reduction;
    private double kb;
    private double health;

    public BasicSetData(ConfigurationSection section, String entry, Set set) {
        this.type = SetDataType.BASIC;
        this.section = section;
        this.entry = entry;
        this.set = set;
        this.increase = this.section.getDouble(this.entry + ".damage-increase");
        this.reduction = this.section.getDouble(this.entry + ".damage-decrease");
        this.kb = this.section.getDouble(this.entry + ".kb");
        this.health = this.section.getDouble(this.entry + ".health");
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, Player damager) {
        if (this.set.getHandData() != null && this.set.verifyPiece(damager.getItemInHand()) && event.getCause().equals(this.set.getHandData().getActiveCause())) {
            event.setDamage(EntityDamageEvent.DamageModifier.BASE, this.set.getHandData().calculateFinalDamage(event.getDamage(), this.increase));
        } else if (this.increase != -1) {
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
