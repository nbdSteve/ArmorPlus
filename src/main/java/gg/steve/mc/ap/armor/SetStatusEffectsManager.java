package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SetStatusEffectsManager {
    private List<PotionEffect> effects;

    public SetStatusEffectsManager(ConfigurationSection section, String entry, Set set) {
        effects = new ArrayList<>();
        if (section.getConfigurationSection(entry + ".status-effects") == null) return;
        ConfigurationSection effectList = section.getConfigurationSection(entry + ".status-effects");
        if (effectList.getKeys(false).isEmpty()) return;
        for (String key : effectList.getKeys(false)) {
            try {
                PotionEffectType.getByName(key.toUpperCase());
            } catch (Exception e) {
                LogUtil.info("Error enabling the " + set.getName() + " armor set, the potion effect: " + key.toUpperCase() + " does not exist");
                continue;
            }
            effects.add(new PotionEffect(PotionEffectType.getByName(key.toUpperCase()), effectList.getInt(key + ".duration"), effectList.getInt(key + ".level") - 1));
        }
    }

    public void applyEffects(Player player) {
        if (this.effects.isEmpty()) return;
        for (PotionEffect effect : this.effects) {
            potionCheck(player, effect.getType(), effect.getAmplifier());
            player.addPotionEffect(effect);
        }
    }

    public void removeEffects(Player player) {
        if (this.effects.isEmpty()) return;
        for (PotionEffect effect : this.effects) {
            potionCheck(player, effect.getType(), effect.getAmplifier());
        }
    }

    /**
     * If the player has that potion effect but the amplifier is less that the level, remove it
     *
     * @param p     the player being checked
     * @param type  the effect to check
     * @param level the amplifier of the new effect
     */
    public static void potionCheck(Player p, PotionEffectType type, int level) {
        if (p.getActivePotionEffects().size() > 0) {
            if (p.hasPotionEffect(type)) {
                if (p.getActivePotionEffects().iterator().next().getType().equals(type)) {
                    if (p.getActivePotionEffects().iterator().next().getAmplifier() <= level) {
                        p.removePotionEffect(type);
                    }
                }
            }
        }
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }
}
