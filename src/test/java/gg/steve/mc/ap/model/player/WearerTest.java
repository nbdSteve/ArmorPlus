package gg.steve.mc.ap.model.player;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WearerTest {

    @Test
    void constructsWithExpectedValues() {
        UUID id = UUID.randomUUID();
        Wearer wearer = new Wearer(id, "dragon");

        assertEquals(id, wearer.getPlayerId());
        assertEquals("dragon", wearer.getSetName());
    }

    @Test
    void equalsAndHashCode() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Wearer a = new Wearer(id, "dragon");
        Wearer b = new Wearer(id, "dragon");
        Wearer c = new Wearer(id, "knight");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
