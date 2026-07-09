package gg.steve.mc.ap.model.id;

public class PotionEffectId extends StringId {
    private PotionEffectId(String value) { super(value); }
    public static PotionEffectId of(String value) { return new PotionEffectId(value); }
}
