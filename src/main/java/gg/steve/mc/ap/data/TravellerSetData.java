package gg.steve.mc.ap.data;

import gg.steve.mc.ap.data.utils.TravellerAttackUtil;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class TravellerSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private double chance;
    private int size;
    private double damage;
    private double height;

    public TravellerSetData(ConfigurationSection section, String entry) {
        this.type = SetDataType.TRAVELLER;
        this.section = section;
        this.entry = entry;
        this.chance = section.getDouble(entry + ".chance");
        this.size = section.getInt(entry + ".size");
        this.damage = section.getDouble(entry + ".damage");
        this.height = section.getDouble(entry + ".height-above-player");
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
        if (!(event.getDamager() instanceof Player)) return;
//        if (Math.random() * 1 > this.chance) return;
        TravellerAttackUtil.attack((Player) event.getDamager(), (Player) event.getEntity(), this.size, this.damage, this.height, this.section, this.entry);
        SoundUtil.playSound(this.section, this.entry, (Player) event.getEntity());
        MessageType.doAttackerMessage(this.section, this.entry, (Player) event.getEntity());
    }

    @Override
    public void onFall(EntityDamageEvent event) {

    }

    @Override
    public void onHungerDeplete(FoodLevelChangeEvent event) {

    }
}
