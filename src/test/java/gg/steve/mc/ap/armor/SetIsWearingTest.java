package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Characterization tests for Set.isWearingSet() and Set.verifyPiece().
 * Uses MockedConstruction to intercept NBTItem creation (vendored NBT does reflection).
 * Pins: the armor-slot checking logic, the changedItem skip, the ArmorType skip.
 */
@ExtendWith(MockitoExtension.class)
class SetIsWearingTest {

    @Mock private Player player;
    @Mock private PlayerInventory inventory;
    @Mock private ItemStack helmet;
    @Mock private ItemStack chestplate;
    @Mock private ItemStack leggings;
    @Mock private ItemStack boots;

    private Set armorSet;

    @BeforeEach
    void setUp() {
        armorSet = mock(Set.class, CALLS_REAL_METHODS);
        armorSet.setName("warrior");

        Map<Piece, ItemStack> pieces = new LinkedHashMap<>();
        pieces.put(Piece.HELMET, helmet);
        pieces.put(Piece.CHESTPLATE, chestplate);
        pieces.put(Piece.LEGGINGS, leggings);
        pieces.put(Piece.BOOTS, boots);
        armorSet.setSetPieces(pieces);

        lenient().when(player.getInventory()).thenReturn(inventory);
    }

    @Test
    void verifyPiece_nullItem_returnsFalse() {
        assertFalse(armorSet.verifyPiece(null));
    }

    @Test
    void verifyPiece_airItem_returnsFalse() {
        ItemStack air = mock(ItemStack.class);
        when(air.getType()).thenReturn(Material.AIR);
        assertFalse(armorSet.verifyPiece(air));
    }

    @Test
    void verifyPiece_validItemMatchingSet_returnsTrue() {
        ItemStack item = mock(ItemStack.class);
        when(item.getType()).thenReturn(Material.DIAMOND_HELMET);

        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> when(mock.getString("armor+.set")).thenReturn("warrior"))) {

            assertTrue(armorSet.verifyPiece(item));
        }
    }

    @Test
    void verifyPiece_validItemDifferentSet_returnsFalse() {
        ItemStack item = mock(ItemStack.class);
        when(item.getType()).thenReturn(Material.DIAMOND_HELMET);

        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> when(mock.getString("armor+.set")).thenReturn("mage"))) {

            assertFalse(armorSet.verifyPiece(item));
        }
    }

    @Test
    void verifyPiece_emptySetTag_returnsFalse() {
        ItemStack item = mock(ItemStack.class);
        when(item.getType()).thenReturn(Material.DIAMOND_HELMET);

        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> when(mock.getString("armor+.set")).thenReturn(""))) {

            assertFalse(armorSet.verifyPiece(item));
        }
    }

    @Test
    void isWearingSet_changedItemNotFromSet_returnsFalse() {
        ItemStack changedItem = mock(ItemStack.class);
        when(changedItem.getType()).thenReturn(Material.DIAMOND_HELMET);

        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> when(mock.getString("armor+.set")).thenReturn("mage"))) {

            assertFalse(armorSet.isWearingSet(player, null, changedItem));
        }
    }

    @Test
    void isWearingSet_allSlotsFilled_allMatchingSet_returnsTrue() {
        when(inventory.getHelmet()).thenReturn(helmet);
        when(inventory.getChestplate()).thenReturn(chestplate);
        when(inventory.getLeggings()).thenReturn(leggings);
        when(inventory.getBoots()).thenReturn(boots);

        when(helmet.getType()).thenReturn(Material.DIAMOND_HELMET);
        when(chestplate.getType()).thenReturn(Material.DIAMOND_CHESTPLATE);
        when(leggings.getType()).thenReturn(Material.DIAMOND_LEGGINGS);
        when(boots.getType()).thenReturn(Material.DIAMOND_BOOTS);

        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> when(mock.getString("armor+.set")).thenReturn("warrior"))) {

            assertTrue(armorSet.isWearingSet(player, null, null));
        }
    }

    @Test
    void isWearingSet_oneSlotEmpty_returnsFalse() {
        when(inventory.getHelmet()).thenReturn(helmet);
        when(inventory.getChestplate()).thenReturn(null); // missing

        when(helmet.getType()).thenReturn(Material.DIAMOND_HELMET);

        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> when(mock.getString("armor+.set")).thenReturn("warrior"))) {

            assertFalse(armorSet.isWearingSet(player, null, null));
        }
    }

    @Test
    void isWearingSet_oneSlotWrongSet_returnsFalse() {
        when(inventory.getHelmet()).thenReturn(helmet);
        when(inventory.getChestplate()).thenReturn(chestplate);
        when(inventory.getLeggings()).thenReturn(leggings);
        when(inventory.getBoots()).thenReturn(boots);

        when(helmet.getType()).thenReturn(Material.DIAMOND_HELMET);
        when(chestplate.getType()).thenReturn(Material.DIAMOND_CHESTPLATE);
        when(leggings.getType()).thenReturn(Material.DIAMOND_LEGGINGS);
        when(boots.getType()).thenReturn(Material.DIAMOND_BOOTS);

        // First 3 NBTItem instances return "warrior", 4th returns "mage"
        final int[] callCount = {0};
        try (MockedConstruction<NBTItem> nbt = mockConstruction(NBTItem.class,
                (mock, ctx) -> {
                    callCount[0]++;
                    if (callCount[0] <= 3) {
                        when(mock.getString("armor+.set")).thenReturn("warrior");
                    } else {
                        when(mock.getString("armor+.set")).thenReturn("mage");
                    }
                })) {

            assertFalse(armorSet.isWearingSet(player, null, null));
        }
    }
}
