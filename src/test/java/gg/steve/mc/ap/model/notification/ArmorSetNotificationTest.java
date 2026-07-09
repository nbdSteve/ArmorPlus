package gg.steve.mc.ap.model.notification;

import gg.steve.mc.ap.model.effect.NotificationSoundSpec;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetNotificationTest {

    @Test
    void builderCreatesExpectedValues() {
        NotificationSoundSpec sound = new NotificationSoundSpec("DING", 1.0f, 1.0f);

        ArmorSetNotification notification = ArmorSetNotification.builder()
                .message("Welcome!")
                .message("Enjoy your armor.")
                .sound(sound)
                .command("give %player% diamond 1")
                .build();

        assertEquals(Arrays.asList("Welcome!", "Enjoy your armor."), notification.getMessages());
        assertEquals(sound, notification.getSound());
        assertEquals(Arrays.asList("give %player% diamond 1"), notification.getCommands());
    }

    @Test
    void equalsAndHashCode() {
        NotificationSoundSpec sound = new NotificationSoundSpec("DING", 1.0f, 1.0f);
        ArmorSetNotification a = ArmorSetNotification.builder()
                .message("Hi")
                .sound(sound)
                .build();
        ArmorSetNotification b = ArmorSetNotification.builder()
                .message("Hi")
                .sound(sound)
                .build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void emptyListsWhenNoItemsAdded() {
        ArmorSetNotification notification = ArmorSetNotification.builder()
                .sound(null)
                .build();

        assertNotNull(notification.getMessages());
        assertTrue(notification.getMessages().isEmpty());
        assertNotNull(notification.getCommands());
        assertTrue(notification.getCommands().isEmpty());
    }

    @Test
    void bulkMessagesMethodDefensivelyCopies() {
        List<String> original = new ArrayList<>(Arrays.asList("A", "B"));

        ArmorSetNotification notification = ArmorSetNotification.builder()
                .messages(original)
                .command("cmd")
                .build();

        original.add("C");

        assertEquals(Arrays.asList("A", "B"), notification.getMessages());
    }

    @Test
    void bulkCommandsMethodDefensivelyCopies() {
        List<String> original = new ArrayList<>(Arrays.asList("x", "y"));

        ArmorSetNotification notification = ArmorSetNotification.builder()
                .commands(original)
                .message("msg")
                .build();

        original.add("z");

        assertEquals(Arrays.asList("x", "y"), notification.getCommands());
    }

    @Test
    void getMessagesReturnsUnmodifiableList() {
        ArmorSetNotification notification = ArmorSetNotification.builder()
                .message("hello")
                .command("cmd")
                .build();

        assertThrows(UnsupportedOperationException.class,
                () -> notification.getMessages().add("sneaky"));
    }

    @Test
    void getCommandsReturnsUnmodifiableList() {
        ArmorSetNotification notification = ArmorSetNotification.builder()
                .message("msg")
                .command("run")
                .build();

        assertThrows(UnsupportedOperationException.class,
                () -> notification.getCommands().add("sneaky"));
    }
}
