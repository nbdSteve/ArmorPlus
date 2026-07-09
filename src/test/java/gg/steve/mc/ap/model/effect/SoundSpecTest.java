package gg.steve.mc.ap.model.effect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundSpecTest {

    @Test
    void constructsWithExpectedValues() {
        SoundSpec spec = new SoundSpec("ENTITY_PLAYER_LEVELUP", 1.0f, 0.5f);
        assertEquals("ENTITY_PLAYER_LEVELUP", spec.getName());
        assertEquals(1.0f, spec.getVolume());
        assertEquals(0.5f, spec.getPitch());
    }

    @Test
    void equalsAndHashCode() {
        SoundSpec a = new SoundSpec("DING", 1.0f, 1.0f);
        SoundSpec b = new SoundSpec("DING", 1.0f, 1.0f);
        SoundSpec c = new SoundSpec("BOOM", 0.5f, 2.0f);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
