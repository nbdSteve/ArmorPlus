package gg.steve.mc.ap.model.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetPurchaseResultTest {

    @Test
    void allValuesPresent() {
        ArmorSetPurchaseResult[] values = ArmorSetPurchaseResult.values();
        assertEquals(3, values.length);
    }

    @Test
    void valueOfRoundTrips() {
        for (ArmorSetPurchaseResult result : ArmorSetPurchaseResult.values()) {
            assertEquals(result, ArmorSetPurchaseResult.valueOf(result.name()));
        }
    }
}
