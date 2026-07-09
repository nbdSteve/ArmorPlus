package gg.steve.mc.ap.model.effect;

import gg.steve.mc.ap.model.id.PotionEffectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionEffectTest {

    @Test
    void constructsWithExpectedValues() {
        PotionEffect effect = new PotionEffect(PotionEffectId.of("SPEED"), 200, 1);
        assertEquals(PotionEffectId.of("SPEED"), effect.getType());
        assertEquals(200, effect.getDuration());
        assertEquals(1, effect.getAmplifier());
    }

    @Test
    void equalsAndHashCode() {
        PotionEffect a = new PotionEffect(PotionEffectId.of("SPEED"), 200, 1);
        PotionEffect b = new PotionEffect(PotionEffectId.of("SPEED"), 200, 1);
        PotionEffect c = new PotionEffect(PotionEffectId.of("JUMP"), 100, 2);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
