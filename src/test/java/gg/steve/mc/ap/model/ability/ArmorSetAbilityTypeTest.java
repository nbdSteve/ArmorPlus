package gg.steve.mc.ap.model.ability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetAbilityTypeTest {

    @Test
    void allValuesPresent() {
        ArmorSetAbilityType[] values = ArmorSetAbilityType.values();
        assertEquals(12, values.length);
    }

    @Test
    void valueOfRoundTrips() {
        for (ArmorSetAbilityType type : ArmorSetAbilityType.values()) {
            assertEquals(type, ArmorSetAbilityType.valueOf(type.name()));
        }
    }
}
