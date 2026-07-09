package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionEffectIdTest {

    @Test
    void ofCreatesExpectedValue() {
        PotionEffectId id = PotionEffectId.of("SPEED");
        assertEquals("SPEED", id.getValue());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> PotionEffectId.of(null));
    }

    @Test
    void equalsAndHashCode() {
        PotionEffectId a = PotionEffectId.of("SPEED");
        PotionEffectId b = PotionEffectId.of("SPEED");
        PotionEffectId c = PotionEffectId.of("JUMP");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
