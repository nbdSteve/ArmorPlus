package gg.steve.mc.ap.model.effect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectSpecTest {

    @Test
    void constructsWithExpectedValues() {
        EffectSpec spec = new EffectSpec("SPEED", 200, 1);
        assertEquals("SPEED", spec.getType());
        assertEquals(200, spec.getDuration());
        assertEquals(1, spec.getAmplifier());
    }

    @Test
    void equalsAndHashCode() {
        EffectSpec a = new EffectSpec("SPEED", 200, 1);
        EffectSpec b = new EffectSpec("SPEED", 200, 1);
        EffectSpec c = new EffectSpec("JUMP", 100, 2);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
