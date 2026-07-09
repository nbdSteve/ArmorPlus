package gg.steve.mc.ap.model.id;

import lombok.Value;

@Value
public class PotionEffectId {
    String value;

    public static PotionEffectId of(String value) {
        return new PotionEffectId(value);
    }
}
