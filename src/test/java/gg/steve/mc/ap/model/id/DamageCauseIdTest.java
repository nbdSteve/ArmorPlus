package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DamageCauseIdTest {

    @Test
    void ofCreatesExpectedValue() {
        DamageCauseId id = DamageCauseId.of("ENTITY_ATTACK");
        assertEquals("ENTITY_ATTACK", id.toString());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> DamageCauseId.of(null));
    }

    @Test
    void ofEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> DamageCauseId.of(""));
    }

    @Test
    void equalsAndHashCode() {
        DamageCauseId a = DamageCauseId.of("ENTITY_ATTACK");
        DamageCauseId b = DamageCauseId.of("ENTITY_ATTACK");
        DamageCauseId c = DamageCauseId.of("PROJECTILE");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
