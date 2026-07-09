package gg.steve.mc.ap.model.id;

public class ArmorSetId extends StringId {
    private ArmorSetId(String value) { super(value); }
    public static ArmorSetId of(String value) { return new ArmorSetId(value); }
}
