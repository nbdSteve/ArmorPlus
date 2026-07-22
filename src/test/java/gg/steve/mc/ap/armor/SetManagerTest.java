package gg.steve.mc.ap.armor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Characterization tests for {@link SetManager#getSet(String)} pinning the exact
 * null contract that the 4E registry extraction had to preserve.
 * <p>
 * Real callers (GiveCmd, PlayerEquipListener, ArmorPlusExpansion) treat a null return as
 * "no such set" and must never see an exception for a missing/blank name. Because the new
 * {@code ArmorSetRegistry} keys on {@code ArmorSetId} - which rejects null/empty via
 * commons-lang3 {@code Validate} - {@code getSet} guards those inputs before constructing the id.
 * These tests prove that guard preserves the original {@code map.get()} behavior.
 */
class SetManagerTest {

    @Test
    void getSet_nullName_returnsNullNotThrows() {
        assertNull(SetManager.getSet(null));
    }

    @Test
    void getSet_emptyName_returnsNullNotThrows() {
        assertNull(SetManager.getSet(""));
    }

    @Test
    void getSet_unknownName_returnsNull() {
        assertNull(SetManager.getSet("no-such-set"));
    }
}
