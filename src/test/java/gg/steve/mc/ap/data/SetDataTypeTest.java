package gg.steve.mc.ap.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetDataTypeTest {

    @Test
    void allValues_present() {
        SetDataType[] values = SetDataType.values();
        assertEquals(12, values.length);
        assertEquals(SetDataType.BASIC, values[0]);
        assertEquals(SetDataType.LIGHTNING, values[1]);
        assertEquals(SetDataType.WARP, values[2]);
        assertEquals(SetDataType.POTION, values[3]);
        assertEquals(SetDataType.FALL, values[4]);
        assertEquals(SetDataType.HUNGER, values[5]);
        assertEquals(SetDataType.TRAVELLER, values[6]);
        assertEquals(SetDataType.STUN, values[7]);
        assertEquals(SetDataType.HAND, values[8]);
        assertEquals(SetDataType.ENGINEER, values[9]);
        assertEquals(SetDataType.COLOR_WAY, values[10]);
        assertEquals(SetDataType.EXPERIENCE, values[11]);
    }

    @Test
    void valueOf_roundTrips() {
        for (SetDataType t : SetDataType.values()) {
            assertEquals(t, SetDataType.valueOf(t.name()));
        }
    }
}
