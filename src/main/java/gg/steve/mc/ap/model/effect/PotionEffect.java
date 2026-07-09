package gg.steve.mc.ap.model.effect;

import lombok.Value;

@Value
public class PotionEffect {
    String type;
    int duration;
    int amplifier;
}
