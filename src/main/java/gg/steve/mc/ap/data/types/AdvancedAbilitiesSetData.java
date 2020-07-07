package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.data.SetDataType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class AdvancedAbilitiesSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;

    public AdvancedAbilitiesSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.ADVANCED_ABILITY;
        this.section = section;
        this.entry = entry;
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

    public SetDataType getType() {
        return type;
    }
}
