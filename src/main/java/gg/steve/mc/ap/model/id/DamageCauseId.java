package gg.steve.mc.ap.model.id;

import lombok.Value;

import java.util.Objects;

@Value
public class DamageCauseId {
    String value;

    public static DamageCauseId of(String value) {
        return new DamageCauseId(Objects.requireNonNull(value, "value must not be null"));
    }
}
