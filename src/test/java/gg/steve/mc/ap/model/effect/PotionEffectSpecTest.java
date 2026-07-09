package gg.steve.mc.ap.model.effect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionEffectSpecTest {

    @Test
    void constructsWithExpectedValues() {
        PotionEffectSpec spec = new PotionEffectSpec("SPEED", 200, 1);
        assertEquals("SPEED", spec.getType());
        assertEquals(200, spec.getDuration());
        assertEquals(1, spec.getAmplifier());
    }

    @Test
    void equalsAndHashCode() {
        PotionEffectSpec a = new PotionEffectSpec("SPEED", 200, 1);
        PotionEffectSpec b = new PotionEffectSpec("SPEED", 200, 1);
        PotionEffectSpec c = new PotionEffectSpec("JUMP", 100, 2);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
