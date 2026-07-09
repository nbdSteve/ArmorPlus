package gg.steve.mc.ap.model.id;

import lombok.NonNull;
import lombok.Value;

@Value
public class ArmorSetId {
    @NonNull String value;

    public static ArmorSetId of(String value) {
        return new ArmorSetId(value);
    }
}
