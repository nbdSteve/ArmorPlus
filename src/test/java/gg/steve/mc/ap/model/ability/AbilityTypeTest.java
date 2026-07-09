package gg.steve.mc.ap.model.ability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbilityTypeTest {

    @Test
    void allValuesPresent() {
        AbilityType[] values = AbilityType.values();
        assertEquals(12, values.length);
    }

    @Test
    void valueOfRoundTrips() {
        for (AbilityType type : AbilityType.values()) {
            assertEquals(type, AbilityType.valueOf(type.name()));
        }
    }
}
