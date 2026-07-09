package gg.steve.mc.ap.model.id;

import java.util.UUID;

public final class PlayerId extends TypedString {
    private final UUID uuid;

    private PlayerId(UUID value) {
        super(value.toString());
        this.uuid = value;
    }

    public UUID getValue() { return uuid; }

    public static PlayerId of(UUID value) {
        if (value == null) throw new NullPointerException("value must not be null");
        return new PlayerId(value);
    }
}
