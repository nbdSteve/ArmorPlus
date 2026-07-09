package gg.steve.mc.ap.model.effect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionEffectTest {

    @Test
    void constructsWithExpectedValues() {
        PotionEffect effect = new PotionEffect("SPEED", 200, 1);
        assertEquals("SPEED", effect.getType());
        assertEquals(200, effect.getDuration());
        assertEquals(1, effect.getAmplifier());
    }

    @Test
    void equalsAndHashCode() {
        PotionEffect a = new PotionEffect("SPEED", 200, 1);
        PotionEffect b = new PotionEffect("SPEED", 200, 1);
        PotionEffect c = new PotionEffect("JUMP", 100, 2);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
