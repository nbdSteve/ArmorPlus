package gg.steve.mc.ap.model.id;

import lombok.NonNull;
import lombok.Value;

@Value
public class DamageCauseId {
    @NonNull String value;

    public static DamageCauseId of(String value) {
        return new DamageCauseId(value);
    }
}
