package gg.steve.mc.ap.model.notification;

import gg.steve.mc.ap.model.effect.SoundSpec;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void builderCreatesExpectedValues() {
        SoundSpec sound = new SoundSpec("DING", 1.0f, 1.0f);
        List<String> messages = Arrays.asList("Welcome!", "Enjoy your armor.");
        List<String> commands = Collections.singletonList("give %player% diamond 1");

        Notification notification = Notification.builder()
                .messages(messages)
                .sound(sound)
                .commands(commands)
                .build();

        assertEquals(messages, notification.getMessages());
        assertEquals(sound, notification.getSound());
        assertEquals(commands, notification.getCommands());
    }

    @Test
    void equalsAndHashCode() {
        SoundSpec sound = new SoundSpec("DING", 1.0f, 1.0f);
        Notification a = Notification.builder()
                .messages(Collections.singletonList("Hi"))
                .sound(sound)
                .commands(Collections.emptyList())
                .build();
        Notification b = Notification.builder()
                .messages(Collections.singletonList("Hi"))
                .sound(sound)
                .commands(Collections.emptyList())
                .build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void nullFieldsPermitted() {
        Notification notification = Notification.builder()
                .messages(null)
                .sound(null)
                .commands(null)
                .build();

        assertNull(notification.getMessages());
        assertNull(notification.getSound());
        assertNull(notification.getCommands());
    }
}
