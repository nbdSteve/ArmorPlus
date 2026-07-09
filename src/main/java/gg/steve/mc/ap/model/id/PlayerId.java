package gg.steve.mc.ap.model.id;

import lombok.Value;

import java.util.UUID;

@Value
public class PlayerId {
    UUID value;

    public static PlayerId of(UUID value) {
        return new PlayerId(value);
    }
}
