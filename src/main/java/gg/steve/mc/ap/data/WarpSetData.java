package gg.steve.mc.ap.data;

import gg.steve.mc.ap.data.utils.WarpUtil;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class WarpSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private double chance;
    private double distance;

    public WarpSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.WARP;
        this.section = section;
        this.entry = entry;
        this.chance = section.getDouble(entry + ".chance");
        this.distance = section.getDouble(entry + ".distance");
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
        if (Math.random() * 1 > this.chance) return;
        WarpUtil.warp((Player) event.getEntity(), event.getDamager(), this.distance, this.section, this.entry);
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

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public SetDataType getType() {
        return type;
    }

    public void setType(SetDataType type) {
        this.type = type;
    }
}
