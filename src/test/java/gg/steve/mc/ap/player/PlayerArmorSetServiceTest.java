package gg.steve.mc.ap.player;

import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.model.player.PlayerArmorWearerRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Characterization tests for PlayerArmorSetService state transitions.
 * Pins add/remove/isWearing/getPiecesWearing behavior. Formerly exercised the static
 * SetPlayerManager with a {@code MockedStatic<SetManager>}; now the service holds an injected
 * wearer registry and catalog, so each test constructs a fresh instance with no static reset.
 */
@ExtendWith(MockitoExtension.class)
class PlayerArmorSetServiceTest {

    @Mock private Player player1;
    @Mock private Player player2;
    @Mock private Set mockSet;
    @Mock private ArmorSetCatalog catalog;

    private final UUID uuid1 = UUID.randomUUID();
    private final UUID uuid2 = UUID.randomUUID();

    private PlayerArmorSetService service;

    @BeforeEach
    void setUp() {
        lenient().when(player1.getUniqueId()).thenReturn(uuid1);
        lenient().when(player2.getUniqueId()).thenReturn(uuid2);

        // getSetPlayer resolves the worn set name through the catalog.
        lenient().when(catalog.getSet(anyString())).thenReturn(mockSet);

        service = new PlayerArmorSetService(new PlayerArmorWearerRegistry(), catalog);
    }

    @Test
    void addSetPlayer_playerNotWearing_addsSuccessfully() {
        service.addSetPlayer(player1, "warrior");

        assertTrue(service.isWearingSet(player1));
    }

    @Test
    void addSetPlayer_playerAlreadyWearing_replacesExisting() {
        service.addSetPlayer(player1, "warrior");
        service.addSetPlayer(player1, "mage");

        assertTrue(service.isWearingSet(player1));
        SetPlayer sp = service.getSetPlayer(player1);
        assertNotNull(sp);
    }

    @Test
    void removeSetPlayer_playerWearing_removesSuccessfully() {
        service.addSetPlayer(player1, "warrior");
        service.removeSetPlayer(player1);

        assertFalse(service.isWearingSet(player1));
    }

    @Test
    void removeSetPlayer_playerNotWearing_noError() {
        assertDoesNotThrow(() -> service.removeSetPlayer(player1));
    }

    @Test
    void isWearingSet_uninitializedMap_returnsFalse() {
        assertFalse(service.isWearingSet(player2));
    }

    @Test
    void multiplePlayers_independentTracking() {
        service.addSetPlayer(player1, "warrior");
        service.addSetPlayer(player2, "mage");

        assertTrue(service.isWearingSet(player1));
        assertTrue(service.isWearingSet(player2));

        service.removeSetPlayer(player1);
        assertFalse(service.isWearingSet(player1));
        assertTrue(service.isWearingSet(player2));
    }

    // --- getPiecesWearing characterization ---

    @Test
    void getPiecesWearing_allPiecesVerified_countsAll(
            @Mock Set set, @Mock PlayerInventory inv,
            @Mock ItemStack helmet, @Mock ItemStack chest,
            @Mock ItemStack legs, @Mock ItemStack boots, @Mock ItemStack hand) {
        Map<Piece, ItemStack> pieces = new LinkedHashMap<>();
        pieces.put(Piece.HELMET, helmet);
        pieces.put(Piece.CHESTPLATE, chest);
        pieces.put(Piece.LEGGINGS, legs);
        pieces.put(Piece.BOOTS, boots);
        pieces.put(Piece.HAND, hand);
        when(set.getSetPieces()).thenReturn(pieces);

        when(player1.getInventory()).thenReturn(inv);
        when(inv.getHelmet()).thenReturn(helmet);
        when(inv.getChestplate()).thenReturn(chest);
        when(inv.getLeggings()).thenReturn(legs);
        when(inv.getBoots()).thenReturn(boots);
        when(inv.getItemInHand()).thenReturn(hand);

        when(set.verifyPiece(helmet)).thenReturn(true);
        when(set.verifyPiece(chest)).thenReturn(true);
        when(set.verifyPiece(legs)).thenReturn(true);
        when(set.verifyPiece(boots)).thenReturn(true);
        when(set.verifyPiece(hand)).thenReturn(true);

        int result = service.getPiecesWearing(set, player1);
        assertEquals(5, result);
    }

    @Test
    void getPiecesWearing_noPiecesVerified_returnsZero(
            @Mock Set set, @Mock PlayerInventory inv,
            @Mock ItemStack helmet, @Mock ItemStack chest,
            @Mock ItemStack legs, @Mock ItemStack boots) {
        Map<Piece, ItemStack> pieces = new LinkedHashMap<>();
        pieces.put(Piece.HELMET, helmet);
        pieces.put(Piece.CHESTPLATE, chest);
        pieces.put(Piece.LEGGINGS, legs);
        pieces.put(Piece.BOOTS, boots);
        when(set.getSetPieces()).thenReturn(pieces);

        when(player1.getInventory()).thenReturn(inv);
        when(inv.getHelmet()).thenReturn(helmet);
        when(inv.getChestplate()).thenReturn(chest);
        when(inv.getLeggings()).thenReturn(legs);
        when(inv.getBoots()).thenReturn(boots);

        when(set.verifyPiece(any(ItemStack.class))).thenReturn(false);

        int result = service.getPiecesWearing(set, player1);
        assertEquals(0, result);
    }

    @Test
    void getPiecesWearing_partialSet_countsOnlyVerified(
            @Mock Set set, @Mock PlayerInventory inv,
            @Mock ItemStack helmet, @Mock ItemStack chest,
            @Mock ItemStack legs, @Mock ItemStack boots) {
        Map<Piece, ItemStack> pieces = new LinkedHashMap<>();
        pieces.put(Piece.HELMET, helmet);
        pieces.put(Piece.CHESTPLATE, chest);
        pieces.put(Piece.LEGGINGS, legs);
        pieces.put(Piece.BOOTS, boots);
        when(set.getSetPieces()).thenReturn(pieces);

        when(player1.getInventory()).thenReturn(inv);
        when(inv.getHelmet()).thenReturn(helmet);
        when(inv.getChestplate()).thenReturn(chest);
        when(inv.getLeggings()).thenReturn(legs);
        when(inv.getBoots()).thenReturn(boots);

        when(set.verifyPiece(helmet)).thenReturn(true);
        when(set.verifyPiece(chest)).thenReturn(false);
        when(set.verifyPiece(legs)).thenReturn(true);
        when(set.verifyPiece(boots)).thenReturn(false);

        int result = service.getPiecesWearing(set, player1);
        assertEquals(2, result);
    }
}
