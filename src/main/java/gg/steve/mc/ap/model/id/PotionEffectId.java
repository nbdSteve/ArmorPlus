package gg.steve.mc.ap.model.id;

import lombok.NonNull;
import lombok.Value;

@Value
public class PotionEffectId {
    @NonNull String value;

    public static PotionEffectId of(String value) {
        return new PotionEffectId(value);
    }
}
