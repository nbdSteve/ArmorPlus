package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Characterization tests for {@link ArmorSetCatalog#getSet(String)} pinning the exact
 * null contract that the registry extraction had to preserve.
 * <p>
 * Real callers (GiveCmd, PlayerEquipListener, ArmorPlusExpansion) treat a null return as
 * "no such set" and must never see an exception for a missing/blank name. Because the
 * {@code ArmorSetRegistry} keys on {@code ArmorSetId} - which rejects null/empty via
 * commons-lang3 {@code Validate} - {@code getSet} guards those inputs before constructing the id.
 * These tests prove that guard preserves the original {@code map.get()} behavior on a catalog
 * built from an empty registry.
 */
@ExtendWith(MockitoExtension.class)
class ArmorSetCatalogTest {

    @Mock private ArmorPlus plugin;

    private ArmorSetCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new ArmorSetCatalog(new ArmorSetRegistry<>(), plugin);
    }

    @Test
    void getSet_nullName_returnsNullNotThrows() {
        assertNull(catalog.getSet(null));
    }

    @Test
    void getSet_emptyName_returnsNullNotThrows() {
        assertNull(catalog.getSet(""));
    }

    @Test
    void getSet_unknownName_returnsNull() {
        assertNull(catalog.getSet("no-such-set"));
    }
}
