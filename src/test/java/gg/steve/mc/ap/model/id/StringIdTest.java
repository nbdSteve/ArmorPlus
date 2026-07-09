package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringIdTest {

    @Test
    void ofEmptyThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ArmorSetId.of(""));
    }
}
