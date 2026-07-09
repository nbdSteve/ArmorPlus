package gg.steve.mc.ap.model.player;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetWearerTest {

    @Test
    void constructsWithExpectedValues() {
        UUID id = UUID.randomUUID();
        ArmorSetWearer wearer = new ArmorSetWearer(id, "dragon");

        assertEquals(id, wearer.getPlayerId());
        assertEquals("dragon", wearer.getSetName());
    }

    @Test
    void equalsAndHashCode() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        ArmorSetWearer a = new ArmorSetWearer(id, "dragon");
        ArmorSetWearer b = new ArmorSetWearer(id, "dragon");
        ArmorSetWearer c = new ArmorSetWearer(id, "knight");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
