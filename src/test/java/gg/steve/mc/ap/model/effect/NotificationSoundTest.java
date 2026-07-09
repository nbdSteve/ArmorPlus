package gg.steve.mc.ap.model.effect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationSoundTest {

    @Test
    void constructsWithExpectedValues() {
        NotificationSound sound = new NotificationSound("ENTITY_PLAYER_LEVELUP", 1.0f, 0.5f);
        assertEquals("ENTITY_PLAYER_LEVELUP", sound.getName());
        assertEquals(1.0f, sound.getVolume());
        assertEquals(0.5f, sound.getPitch());
    }

    @Test
    void equalsAndHashCode() {
        NotificationSound a = new NotificationSound("DING", 1.0f, 1.0f);
        NotificationSound b = new NotificationSound("DING", 1.0f, 1.0f);
        NotificationSound c = new NotificationSound("BOOM", 0.5f, 2.0f);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
