package gg.steve.mc.ap.model.effect;

import lombok.Value;

@Value
public class PotionEffectSpec {
    String type;
    int duration;
    int amplifier;
}
