package gg.steve.mc.ap.model.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceSlotTest {

    @Test
    void allValuesPresent() {
        PieceSlot[] values = PieceSlot.values();
        assertEquals(5, values.length);
    }

    @Test
    void valueOfRoundTrips() {
        for (PieceSlot slot : PieceSlot.values()) {
            assertEquals(slot, PieceSlot.valueOf(slot.name()));
        }
    }

    @Test
    void expectedSlots() {
        assertNotNull(PieceSlot.valueOf("HELMET"));
        assertNotNull(PieceSlot.valueOf("CHESTPLATE"));
        assertNotNull(PieceSlot.valueOf("LEGGINGS"));
        assertNotNull(PieceSlot.valueOf("BOOTS"));
        assertNotNull(PieceSlot.valueOf("HAND"));
    }
}
