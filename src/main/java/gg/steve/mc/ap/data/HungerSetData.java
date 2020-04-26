package gg.steve.mc.ap.data;

import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerSetData implements SetData {
    private ConfigurationSection section;
    private String entry;

    public HungerSetData(ConfigurationSection section, String entry) {
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
    public void onHit(EntityDamageByEntityEvent event) {

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {

    }

    @Override
    public void onFall(EntityDamageEvent event) {

    }

    @Override
    public void onHungerDeplete(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        ((Player) event.getEntity()).setFoodLevel(20);
        ((Player) event.getEntity()).setSaturation(20f);
        SoundUtil.playSound(this.section, this.entry, (Player) event.getEntity());
        MessageType.doProcMessage(this.section, this.entry, (Player) event.getEntity());
    }

    // <-- Getters and Setters from this point on -->
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
