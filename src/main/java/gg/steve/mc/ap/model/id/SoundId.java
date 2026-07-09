package gg.steve.mc.ap.model.id;

import lombok.NonNull;
import lombok.Value;

@Value
public class SoundId {
    @NonNull String value;

    public static SoundId of(String value) {
        return new SoundId(value);
    }
}
