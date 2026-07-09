package gg.steve.mc.ap.model.id;

import lombok.Value;

import java.util.Objects;

@Value
public class SoundId {
    String value;

    public static SoundId of(String value) {
        return new SoundId(Objects.requireNonNull(value, "value must not be null"));
    }
}
