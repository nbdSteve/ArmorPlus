package gg.steve.mc.ap.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDamageEvent;

public class HandSetData implements HandItemData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private double increase;
    private EntityDamageEvent.DamageCause activeCause;

    public HandSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.HAND;
        this.section = section;
        this.entry = entry;
        this.increase = this.section.getDouble(this.entry + ".damage-increase");
        this.activeCause = EntityDamageEvent.DamageCause.valueOf(this.section.getString(this.entry + ".damage-cause").toUpperCase());
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
}
