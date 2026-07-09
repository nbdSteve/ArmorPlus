package gg.steve.mc.ap.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldPositionTest {

    @Test
    void fieldsAreAccessible() {
        WorldPosition pos = new WorldPosition("world", 1.0, 2.0, 3.0, 90.0f, 45.0f);
        assertEquals("world", pos.getWorld());
        assertEquals(1.0, pos.getX());
        assertEquals(2.0, pos.getY());
        assertEquals(3.0, pos.getZ());
        assertEquals(90.0f, pos.getYaw());
        assertEquals(45.0f, pos.getPitch());
    }

    @Test
    void equalsAndHashCode() {
        WorldPosition a = new WorldPosition("world", 1.0, 2.0, 3.0, 0f, 0f);
        WorldPosition b = new WorldPosition("world", 1.0, 2.0, 3.0, 0f, 0f);
        WorldPosition c = new WorldPosition("nether", 1.0, 2.0, 3.0, 0f, 0f);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
