package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorPieceSlot;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WornArmorTest {

    @Test
    void emptyWornArmor() {
        WornArmor armor = WornArmor.builder().build();
        assertTrue(armor.isEmpty());
        assertEquals(Optional.empty(), armor.getSlot(ArmorPieceSlot.HELMET));
    }

    @Test
    void getSlotReturnsPopulatedEntry() {
        ArmorSetId dragon = ArmorSetId.of("dragon");
        WornArmor armor = WornArmor.builder()
                .slot(ArmorPieceSlot.HELMET, dragon)
                .slot(ArmorPieceSlot.CHESTPLATE, dragon)
                .build();

        assertFalse(armor.isEmpty());
        assertEquals(Optional.of(dragon), armor.getSlot(ArmorPieceSlot.HELMET));
        assertEquals(Optional.of(dragon), armor.getSlot(ArmorPieceSlot.CHESTPLATE));
        assertEquals(Optional.empty(), armor.getSlot(ArmorPieceSlot.BOOTS));
    }

    @Test
    void slotsMapIsUnmodifiable() {
        WornArmor armor = WornArmor.builder()
                .slot(ArmorPieceSlot.HAND, ArmorSetId.of("knight"))
                .build();

        assertThrows(UnsupportedOperationException.class,
                () -> armor.getSlots().put(ArmorPieceSlot.BOOTS, ArmorSetId.of("x")));
    }
}
