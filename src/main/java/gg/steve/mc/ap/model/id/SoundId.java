package gg.steve.mc.ap.model.id;

import lombok.Value;

@Value
public class SoundId {
    String value;

    public static SoundId of(String value) {
        return new SoundId(value);
    }
}
