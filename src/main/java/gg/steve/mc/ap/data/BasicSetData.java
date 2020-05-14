package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetStatusEffectsManager;
import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BasicSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private Set set;
    private double increase;
    private double reduction;
    private double kb;
    private double health;
    private SetStatusEffectsManager effectsManager;

    public BasicSetData(ConfigurationSection section, String entry, Set set) {
        this.type = SetDataType.BASIC;
        this.section = section;
        this.entry = entry;
        this.set = set;
        this.increase = this.section.getDouble(this.entry + ".damage-increase");
        this.reduction = this.section.getDouble(this.entry + ".damage-reduction");
        this.kb = this.section.getDouble(this.entry + ".kb");
        this.health = this.section.getDouble(this.entry + ".health");
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
        this.effectsManager.applyEffects(player);
    }

    @Override
    public void onRemoval(Player player) {
        if (this.health != -1) {
            player.setMaxHealth(20.0);
        }
        this.effectsManager.removeEffects(player);
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

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public SetStatusEffectsManager getEffectsManager() {
        return effectsManager;
    }

    public void setEffectsManager(SetStatusEffectsManager effectsManager) {
        this.effectsManager = effectsManager;
    }
}
