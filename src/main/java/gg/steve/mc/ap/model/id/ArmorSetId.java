package gg.steve.mc.ap.model.id;

import lombok.Value;

@Value
public class ArmorSetId {
    String value;

    public static ArmorSetId of(String value) {
        return new ArmorSetId(value);
    }
}
