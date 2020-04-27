package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.data.utils.PotionAttackUtil;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.LogUtil;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionSetData implements SetData {
    private SetDataType type;
    private ConfigurationSection section;
    private String entry;
    private ActivationType activation;
    private double chance;
    private double radius;
    private boolean randomRadius;
    private PotionEffect effect;

    public enum ActivationType {
        PASSIVE,
        AGGRESSIVE;
    }

    public PotionSetData(ConfigurationSection section, String entry, Set set) {
        this.type = SetDataType.POTION;
        this.section = section;
        this.entry = entry;
        this.activation = ActivationType.valueOf(section.getString(entry + ".activation").toUpperCase());
        this.chance = section.getDouble(entry + ".chance");
        this.radius = section.getDouble(entry + ".radius");
        this.randomRadius = section.getBoolean(entry + ".random-radius");
        try {
            PotionEffectType.getByName(section.getString(entry + ".effect.potion").toUpperCase());
        } catch (Exception e) {
            LogUtil.info("Error enabling the " + set.getName() + " armor set, the potion effect: " + section.getString(entry + ".effect.potion").toUpperCase() + " does not exist");
            return;
        }
        this.effect = new PotionEffect(PotionEffectType.getByName(section.getString(entry + ".effect.potion")),
                section.getInt(entry + ".effect.duration"), section.getInt(entry + ".effect.level") - 1);
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void onRemoval(Player player) {

    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, Player damager) {
        if (activation.equals(ActivationType.PASSIVE)) return;
        if (Math.random() * 1 > this.chance) return;
        new PotionAttackUtil(this.effect, this.randomRadius, this.radius, this.activation, this.section, this.entry).applyEffects(event, damager);
        SoundUtil.playSound(this.section, this.entry, damager);
        MessageType.doAttackerMessage(this.section, this.entry, damager);
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (activation.equals(ActivationType.AGGRESSIVE)) return;
        if (Math.random() * 1 > this.chance) return;
        new PotionAttackUtil(this.effect, this.randomRadius, this.radius, this.activation, this.section, this.entry).applyEffects(event, null);
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

    public ActivationType getActivation() {
        return activation;
    }

    public void setActivation(ActivationType activation) {
        this.activation = activation;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isRandomRadius() {
        return randomRadius;
    }

    public void setRandomRadius(boolean randomRadius) {
        this.randomRadius = randomRadius;
    }

    public PotionEffect getEffect() {
        return effect;
    }

    public void setEffect(PotionEffect effect) {
        this.effect = effect;
    }
}
