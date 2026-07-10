package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskHandleTest {

    @Test
    void ofCreatesExpectedValue() {
        TaskHandle handle = TaskHandle.of("task-42");
        assertEquals("task-42", handle.toString());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> TaskHandle.of(null));
    }

    @Test
    void ofEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> TaskHandle.of(""));
    }

    @Test
    void equalsAndHashCode() {
        TaskHandle a = TaskHandle.of("task-1");
        TaskHandle b = TaskHandle.of("task-1");
        TaskHandle c = TaskHandle.of("task-2");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
