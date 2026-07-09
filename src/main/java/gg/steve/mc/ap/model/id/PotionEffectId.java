package gg.steve.mc.ap.model.id;

import lombok.Value;

import java.util.Objects;

@Value
public class PotionEffectId {
    String value;

    public static PotionEffectId of(String value) {
        return new PotionEffectId(Objects.requireNonNull(value, "value must not be null"));
    }
}
