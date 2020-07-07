package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.data.AbstractSetData;
import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.data.SetDataType;
import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Random;

public class ExperienceSetData extends AbstractSetData implements SetData {
    private boolean randomMultiplier;
    private double chance, minMultiplier, maxMultiplier;
    private Random random = new Random();

    public ExperienceSetData(ConfigurationSection section, String entry, Set set) {
        super(SetDataType.EXPERIENCE, section, entry, set);
        this.randomMultiplier = this.section.getBoolean(this.entry + ".multiplier.random");
        this.chance = this.section.getDouble(this.entry + ".chance");
        this.minMultiplier = this.section.getDouble(this.entry + ".multiplier.minimum");
        this.maxMultiplier = this.section.getDouble(this.entry + ".multiplier.maximum");
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
        if (Math.random() * 1 > this.chance) return;
        double multiplier = this.minMultiplier;
        if (randomMultiplier) {
            multiplier = this.minMultiplier + (this.maxMultiplier - this.minMultiplier) * this.random.nextDouble();
        }
        event.setDroppedExp((int) Math.floor(event.getDroppedExp() * multiplier));
    }
}
