package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundIdTest {

    @Test
    void ofCreatesExpectedValue() {
        SoundId id = SoundId.of("ENTITY_PLAYER_LEVELUP");
        assertEquals("ENTITY_PLAYER_LEVELUP", id.toString());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> SoundId.of(null));
    }

    @Test
    void ofEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> SoundId.of(""));
    }

    @Test
    void equalsAndHashCode() {
        SoundId a = SoundId.of("DING");
        SoundId b = SoundId.of("DING");
        SoundId c = SoundId.of("BOOM");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
