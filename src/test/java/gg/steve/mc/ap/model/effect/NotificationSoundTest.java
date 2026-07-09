package gg.steve.mc.ap.model.effect;

import gg.steve.mc.ap.model.id.SoundId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationSoundTest {

    @Test
    void constructsWithExpectedValues() {
        NotificationSound sound = new NotificationSound(SoundId.of("ENTITY_PLAYER_LEVELUP"), 1.0f, 0.5f);
        assertEquals(SoundId.of("ENTITY_PLAYER_LEVELUP"), sound.getName());
        assertEquals(1.0f, sound.getVolume());
        assertEquals(0.5f, sound.getPitch());
    }

    @Test
    void equalsAndHashCode() {
        NotificationSound a = new NotificationSound(SoundId.of("DING"), 1.0f, 1.0f);
        NotificationSound b = new NotificationSound(SoundId.of("DING"), 1.0f, 1.0f);
        NotificationSound c = new NotificationSound(SoundId.of("BOOM"), 0.5f, 2.0f);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
