package gg.steve.mc.ap.model.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GiveResultTest {

    @Test
    void allValuesExist() {
        GiveResult[] values = GiveResult.values();
        assertEquals(3, values.length);
        assertNotNull(GiveResult.valueOf("SUCCESS"));
        assertNotNull(GiveResult.valueOf("SET_NOT_FOUND"));
        assertNotNull(GiveResult.valueOf("PLAYER_NOT_FOUND"));
    }
}
