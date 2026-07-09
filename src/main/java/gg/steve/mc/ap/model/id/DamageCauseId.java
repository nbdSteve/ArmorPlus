package gg.steve.mc.ap.model.id;

public class DamageCauseId extends StringId {
    private DamageCauseId(String value) { super(value); }
    public static DamageCauseId of(String value) { return new DamageCauseId(value); }
}
