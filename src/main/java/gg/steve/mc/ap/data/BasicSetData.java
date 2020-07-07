package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetStatusEffectsManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BasicSetData extends AbstractSetData implements SetData {
    private double increase, reduction, kb, health;
    private float walkSpeedSet, walkSpeedDefault, flySpeedSet, flySpeedDefault;
    private SetStatusEffectsManager effectsManager;

    public BasicSetData(ConfigurationSection section, String entry, Set set) {
        super(SetDataType.BASIC, section, entry, set);
        this.increase = this.section.getDouble(this.entry + ".damage-increase");
        this.reduction = this.section.getDouble(this.entry + ".damage-reduction");
        this.kb = this.section.getDouble(this.entry + ".kb");
        this.health = this.section.getDouble(this.entry + ".health");
        this.walkSpeedSet = (float) this.section.getDouble(this.entry + ".speed.walk.set");
        this.walkSpeedDefault = (float) this.section.getDouble(this.entry + ".speed.walk.default");
        this.flySpeedSet = (float) this.section.getDouble(this.entry + ".speed.fly.set");
        this.flySpeedDefault = (float) this.section.getDouble(this.entry + ".speed.fly.default");
        this.effectsManager = new SetStatusEffectsManager(this.section, this.entry, this.set);
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, Player damager) {
        if (this.set.getHandData() != null && this.set.verifyPiece(damager.getItemInHand()) && event.getCause().equals(this.set.getHandData().getActiveCause())) {
            event.setDamage(this.set.getHandData().calculateFinalDamage(event.getDamage(), this.increase));
        } else if (this.increase != -1) {
            event.setDamage(event.getDamage() * this.increase);
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (this.reduction != -1) {
            event.setDamage(event.getDamage() * this.reduction);
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
        if (this.walkSpeedSet != -1) {
            player.setWalkSpeed(this.walkSpeedSet);
        }
        if (this.flySpeedSet != -1) {
            player.setFlySpeed(this.flySpeedSet);
        }
        this.effectsManager.applyEffects(player);
    }

    @Override
    public void onRemoval(Player player) {
        if (this.health != -1) {
            player.setMaxHealth(20.0);
        }
        if (this.walkSpeedDefault != -1) {
            player.setWalkSpeed(this.walkSpeedDefault);
        }
        if (this.flySpeedDefault != -1) {
            player.setFlySpeed(this.flySpeedDefault);
        }
        this.effectsManager.removeEffects(player);
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

    public SetStatusEffectsManager getEffectsManager() {
        return effectsManager;
    }

    public void setEffectsManager(SetStatusEffectsManager effectsManager) {
        this.effectsManager = effectsManager;
    }

    public float getWalkSpeedSet() {
        return walkSpeedSet;
    }

    public void setWalkSpeedSet(float walkSpeedSet) {
        this.walkSpeedSet = walkSpeedSet;
    }

    public float getWalkSpeedDefault() {
        return walkSpeedDefault;
    }

    public void setWalkSpeedDefault(float walkSpeedDefault) {
        this.walkSpeedDefault = walkSpeedDefault;
    }

    public float getFlySpeedSet() {
        return flySpeedSet;
    }

    public void setFlySpeedSet(float flySpeedSet) {
        this.flySpeedSet = flySpeedSet;
    }

    public float getFlySpeedDefault() {
        return flySpeedDefault;
    }

    public void setFlySpeedDefault(float flySpeedDefault) {
        this.flySpeedDefault = flySpeedDefault;
    }
}
