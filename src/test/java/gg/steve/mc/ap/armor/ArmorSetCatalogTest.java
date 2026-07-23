package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

// getSet must return null for null/blank names: callers rely on it, but ArmorSetId.of rejects them.
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
