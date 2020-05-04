package gg.steve.mc.ap.data.utils;

import gg.steve.mc.ap.armor.SetStatusEffectsManager;
import gg.steve.mc.ap.data.types.PotionSetData;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

public class PotionAttackUtil {
    private ConfigurationSection section;
    private String entry;
    private double radius;
    private boolean randomRadius;
    private PotionEffect effect;
    private PotionSetData.ActivationType activation;

    public PotionAttackUtil(PotionEffect effect, boolean randomRadius, double radius, PotionSetData.ActivationType activation, ConfigurationSection section, String entry) {
        this.effect = effect;
        this.randomRadius = randomRadius;
        this.radius = radius;
        this.section = section;
        this.entry = entry;
        this.activation = activation;
    }

    public void applyEffects(EntityDamageByEntityEvent event, Player damager) {
        if (this.randomRadius) {
            this.radius = Math.random() * this.radius + 1;
        }
        Player player = damager;
        if (activation.equals(PotionSetData.ActivationType.PASSIVE)) {
            player = (Player) event.getEntity();
        }
        for (Entity entity : player.getNearbyEntities(this.radius, this.radius, this.radius)) {
            if (entity.getUniqueId().equals(player.getUniqueId())) continue;
            if (!(entity instanceof Player)) continue;
            Player player1 = (Player) entity;
            SetStatusEffectsManager.potionCheck(player1, this.effect.getType(), this.effect.getAmplifier());
            player1.addPotionEffect(this.effect);
            SoundUtil.playSound(this.section, this.entry, player1);
            MessageType.doAttackedMessage(this.section, this.entry, player1);
        }
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

    public PotionSetData.ActivationType getActivation() {
        return activation;
    }

    public void setActivation(PotionSetData.ActivationType activation) {
        this.activation = activation;
    }
}
