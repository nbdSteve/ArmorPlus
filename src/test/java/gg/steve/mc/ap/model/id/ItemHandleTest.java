package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemHandleTest {

    @Test
    void ofCreatesExpectedValue() {
        ItemHandle handle = ItemHandle.of("item-123");
        assertEquals("item-123", handle.toString());
    }

    @Test
    void ofNullThrows() {
        assertThrows(NullPointerException.class, () -> ItemHandle.of(null));
    }

    @Test
    void ofEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> ItemHandle.of(""));
    }

    @Test
    void equalsAndHashCode() {
        ItemHandle a = ItemHandle.of("item-1");
        ItemHandle b = ItemHandle.of("item-1");
        ItemHandle c = ItemHandle.of("item-2");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void differentTypedStringsWithSameValueAreNotEqual() {
        ItemHandle handle = ItemHandle.of("shared-value");
        ArmorSetId setId = ArmorSetId.of("shared-value");
        assertNotEquals(handle, setId);
    }
}
