package gg.steve.mc.ap.model.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmorPieceSlotTest {

    @Test
    void allValuesPresent() {
        ArmorPieceSlot[] values = ArmorPieceSlot.values();
        assertEquals(5, values.length);
    }

    @Test
    void valueOfRoundTrips() {
        for (ArmorPieceSlot slot : ArmorPieceSlot.values()) {
            assertEquals(slot, ArmorPieceSlot.valueOf(slot.name()));
        }
    }

    @Test
    void expectedSlots() {
        assertNotNull(ArmorPieceSlot.valueOf("HELMET"));
        assertNotNull(ArmorPieceSlot.valueOf("CHESTPLATE"));
        assertNotNull(ArmorPieceSlot.valueOf("LEGGINGS"));
        assertNotNull(ArmorPieceSlot.valueOf("BOOTS"));
        assertNotNull(ArmorPieceSlot.valueOf("HAND"));
    }
}
