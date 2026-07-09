package gg.steve.mc.ap.model.id;

public final class SoundId extends StringId {
    private SoundId(String value) { super(value); }
    public static SoundId of(String value) { return new SoundId(value); }
}
