package gg.steve.mc.ap.model.effect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationSoundSpecTest {

    @Test
    void constructsWithExpectedValues() {
        NotificationSoundSpec spec = new NotificationSoundSpec("ENTITY_PLAYER_LEVELUP", 1.0f, 0.5f);
        assertEquals("ENTITY_PLAYER_LEVELUP", spec.getName());
        assertEquals(1.0f, spec.getVolume());
        assertEquals(0.5f, spec.getPitch());
    }

    @Test
    void equalsAndHashCode() {
        NotificationSoundSpec a = new NotificationSoundSpec("DING", 1.0f, 1.0f);
        NotificationSoundSpec b = new NotificationSoundSpec("DING", 1.0f, 1.0f);
        NotificationSoundSpec c = new NotificationSoundSpec("BOOM", 0.5f, 2.0f);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
