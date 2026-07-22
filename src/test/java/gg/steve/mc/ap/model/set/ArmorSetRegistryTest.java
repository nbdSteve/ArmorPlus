package gg.steve.mc.ap.model.set;

import gg.steve.mc.ap.model.id.ArmorSetId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetRegistryTest {

    private final ArmorSetId dragon = ArmorSetId.of("dragon");
    private final ArmorSetId knight = ArmorSetId.of("knight");
    private final ArmorSetId mage = ArmorSetId.of("mage");

    @Test
    void freshRegistryIsEmpty() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();

        assertEquals(0, registry.count());
        assertEquals(Optional.empty(), registry.get(dragon));
        assertTrue(registry.getAll().isEmpty());
    }

    @Test
    void register_thenGet_returnsStoredSet() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();
        registry.register(dragon, "dragon-set");

        assertEquals(Optional.of("dragon-set"), registry.get(dragon));
        assertEquals(1, registry.count());
    }

    @Test
    void get_unknownId_returnsEmpty() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();
        registry.register(dragon, "dragon-set");

        assertEquals(Optional.empty(), registry.get(knight));
    }

    @Test
    void register_sameId_replacesExisting() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();
        registry.register(dragon, "first");
        registry.register(dragon, "second");

        assertEquals(Optional.of("second"), registry.get(dragon));
        assertEquals(1, registry.count());
    }

    @Test
    void getAll_preservesRegistrationOrder() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();
        registry.register(mage, "mage-set");
        registry.register(dragon, "dragon-set");
        registry.register(knight, "knight-set");

        List<ArmorSetId> order = new ArrayList<>(registry.getAll().keySet());
        assertEquals(List.of(mage, dragon, knight), order);
    }

    @Test
    void getAll_isUnmodifiable() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();
        registry.register(dragon, "dragon-set");

        Map<ArmorSetId, String> all = registry.getAll();
        assertThrows(UnsupportedOperationException.class, () -> all.put(knight, "knight-set"));
    }

    @Test
    void clear_dropsEverySet() {
        ArmorSetRegistry<String> registry = new ArmorSetRegistry<>();
        registry.register(dragon, "dragon-set");
        registry.register(knight, "knight-set");
        registry.clear();

        assertEquals(0, registry.count());
        assertEquals(Optional.empty(), registry.get(dragon));
    }
}
