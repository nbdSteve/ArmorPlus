package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerIdTest {

    @Test
    void ofCreatesExpectedValue() {
        UUID uuid = UUID.randomUUID();
        PlayerId id = PlayerId.of(uuid);
        assertEquals(uuid, id.getValue());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> PlayerId.of(null));
    }

    @Test
    void equalsAndHashCode() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        PlayerId a = PlayerId.of(uuid);
        PlayerId b = PlayerId.of(uuid);
        PlayerId c = PlayerId.of(UUID.fromString("00000000-0000-0000-0000-000000000002"));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
