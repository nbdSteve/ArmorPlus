package gg.steve.mc.ap.model.id;

import lombok.Value;

import java.util.Objects;

@Value
public class ArmorSetId {
    String value;

    public static ArmorSetId of(String value) {
        return new ArmorSetId(Objects.requireNonNull(value, "value must not be null"));
    }
}
