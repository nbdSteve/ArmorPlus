package gg.steve.mc.ap.model.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseResultTest {

    @Test
    void allValuesPresent() {
        PurchaseResult[] values = PurchaseResult.values();
        assertEquals(3, values.length);
    }

    @Test
    void valueOfRoundTrips() {
        for (PurchaseResult result : PurchaseResult.values()) {
            assertEquals(result, PurchaseResult.valueOf(result.name()));
        }
    }
}
