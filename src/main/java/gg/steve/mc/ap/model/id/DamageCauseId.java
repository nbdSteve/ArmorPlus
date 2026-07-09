package gg.steve.mc.ap.model.id;

import lombok.Value;

@Value
public class DamageCauseId {
    String value;

    public static DamageCauseId of(String value) {
        return new DamageCauseId(value);
    }
}
