package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetIdTest {

    @Test
    void ofCreatesExpectedValue() {
        ArmorSetId id = ArmorSetId.of("dragon");
        assertEquals("dragon", id.toString());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> ArmorSetId.of(null));
    }

    @Test
    void ofEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> ArmorSetId.of(""));
    }

    @Test
    void equalsAndHashCode() {
        ArmorSetId a = ArmorSetId.of("dragon");
        ArmorSetId b = ArmorSetId.of("dragon");
        ArmorSetId c = ArmorSetId.of("knight");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
