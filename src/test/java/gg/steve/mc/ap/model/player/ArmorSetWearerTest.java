package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSetWearerTest {

    @Test
    void constructsWithExpectedValues() {
        PlayerId id = PlayerId.of(UUID.randomUUID());
        ArmorSetId setId = ArmorSetId.of("dragon");
        ArmorSetWearer wearer = new ArmorSetWearer(id, setId);

        assertEquals(id, wearer.getPlayerId());
        assertEquals(setId, wearer.getSetId());
    }

    @Test
    void equalsAndHashCode() {
        PlayerId id = PlayerId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        ArmorSetWearer a = new ArmorSetWearer(id, ArmorSetId.of("dragon"));
        ArmorSetWearer b = new ArmorSetWearer(id, ArmorSetId.of("dragon"));
        ArmorSetWearer c = new ArmorSetWearer(id, ArmorSetId.of("knight"));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
