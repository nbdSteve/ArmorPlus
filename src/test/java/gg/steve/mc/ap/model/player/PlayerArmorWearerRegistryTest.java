package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerArmorWearerRegistryTest {

    private final PlayerId player = PlayerId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    private final PlayerId other = PlayerId.of(UUID.fromString("00000000-0000-0000-0000-000000000002"));
    private final ArmorSetId dragon = ArmorSetId.of("dragon");
    private final ArmorSetId knight = ArmorSetId.of("knight");

    @Test
    void freshRegistryHasNoWearers() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();

        assertEquals(0, registry.count());
        assertFalse(registry.isWearing(player));
        assertEquals(Optional.empty(), registry.get(player));
    }

    @Test
    void addFromParts_recordsWearer() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();
        registry.add(player, dragon);

        assertTrue(registry.isWearing(player));
        assertEquals(1, registry.count());
        assertEquals(Optional.of(new ArmorSetWearer(player, dragon)), registry.get(player));
    }

    @Test
    void addWearer_recordsWearer() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();
        ArmorSetWearer wearer = new ArmorSetWearer(player, dragon);
        registry.add(wearer);

        assertTrue(registry.isWearing(player));
        assertEquals(Optional.of(wearer), registry.get(player));
    }

    @Test
    void add_samePlayerTwice_replacesExisting() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();
        registry.add(player, dragon);
        registry.add(player, knight);

        assertEquals(1, registry.count());
        assertEquals(Optional.of(new ArmorSetWearer(player, knight)), registry.get(player));
    }

    @Test
    void remove_forgetsWearer() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();
        registry.add(player, dragon);
        registry.remove(player);

        assertFalse(registry.isWearing(player));
        assertEquals(Optional.empty(), registry.get(player));
        assertEquals(0, registry.count());
    }

    @Test
    void remove_absentPlayer_isNoOp() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();

        assertDoesNotThrow(() -> registry.remove(player));
        assertEquals(0, registry.count());
    }

    @Test
    void multiplePlayers_trackedIndependently() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();
        registry.add(player, dragon);
        registry.add(other, knight);

        assertEquals(2, registry.count());
        registry.remove(player);

        assertFalse(registry.isWearing(player));
        assertTrue(registry.isWearing(other));
        assertEquals(Optional.of(new ArmorSetWearer(other, knight)), registry.get(other));
    }

    @Test
    void clear_dropsEveryWearer() {
        PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();
        registry.add(player, dragon);
        registry.add(other, knight);
        registry.clear();

        assertEquals(0, registry.count());
        assertFalse(registry.isWearing(player));
        assertFalse(registry.isWearing(other));
    }
}
