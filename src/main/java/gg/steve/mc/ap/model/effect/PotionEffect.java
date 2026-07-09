package gg.steve.mc.ap.model.effect;

import gg.steve.mc.ap.model.id.PotionEffectId;
import lombok.Value;

@Value
public class PotionEffect {
    PotionEffectId type;
    int duration;
    int amplifier;
}
