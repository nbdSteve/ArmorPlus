package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.data.HandItemData;
import gg.steve.mc.ap.data.SetDataType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HandSetData implements HandItemData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private double increase;
    private boolean requireSet;
    private EntityDamageEvent.DamageCause activeCause;
    private Set set;

    public HandSetData(ConfigurationSection section, String entry, Set set) {
        this.type = SetDataType.HAND;
        this.section = section;
        this.entry = entry;
        this.increase = this.section.getDouble(this.entry + ".damage-increase");
        this.requireSet = this.section.getBoolean(this.entry + ".require-set");
        this.activeCause = EntityDamageEvent.DamageCause.valueOf(this.section.getString(this.entry + ".damage-cause").toUpperCase());
        this.set = set;
    }

    @Override
    public void hitWithoutSetBuff(EntityDamageByEntityEvent event, Player damager) {
        if (event.getCause().equals(getActiveCause()) && !this.requireSet) {
            event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * this.increase);
        }
    }

    @Override
    public double calculateFinalDamage(double damage, double setIncrease) {
        if (setIncrease != -1) {
            double set = setIncrease - 1;
            return damage * (set + this.increase);
        }
        return damage * this.increase;
    }

    @Override
    public EntityDamageEvent.DamageCause getActiveCause() {
        return this.activeCause;
    }

    @Override
    public double getIncrease() {
        return increase;
    }

    @Override
    public double getReduction() {
        return 0;
    }

    @Override
    public boolean requiresFullSet() {
        return requireSet;
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

    public void setIncrease(double increase) {
        this.increase = increase;
    }

    public void setActiveCause(EntityDamageEvent.DamageCause activeCause) {
        this.activeCause = activeCause;
    }

    public boolean isRequireSet() {
        return requireSet;
    }

    public void setRequireSet(boolean requireSet) {
        this.requireSet = requireSet;
    }
}
