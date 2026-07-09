package gg.steve.mc.ap.player;

import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Characterization tests for SetPlayerManager state transitions.
 * Pins add/remove/isWearing/getPiecesWearing before 4E extracts WearerRegistry.
 */
@ExtendWith(MockitoExtension.class)
class SetPlayerManagerTest {

    @Mock private Player player1;
    @Mock private Player player2;
    @Mock private Set mockSet;

    private final UUID uuid1 = UUID.randomUUID();
    private final UUID uuid2 = UUID.randomUUID();

    private MockedStatic<SetManager> setManagerMock;

    @BeforeEach
    void setUp() {
        when(player1.getUniqueId()).thenReturn(uuid1);
        lenient().when(player2.getUniqueId()).thenReturn(uuid2);

        // SetPlayer constructor calls SetManager.getSet(name) - mock it
        setManagerMock = mockStatic(SetManager.class);
        setManagerMock.when(() -> SetManager.getSet(anyString())).thenReturn(mockSet);

        // Reset the static state by removing any players
        SetPlayerManager.removeSetPlayer(player1);
        SetPlayerManager.removeSetPlayer(player2);
    }

    @AfterEach
    void tearDown() {
        setManagerMock.close();
    }

    @Test
    void addSetPlayer_playerNotWearing_addsSuccessfully() {
        SetPlayerManager.addSetPlayer(player1, "warrior");

        assertTrue(SetPlayerManager.isWearingSet(player1));
    }

    @Test
    void addSetPlayer_playerAlreadyWearing_replacesExisting() {
        SetPlayerManager.addSetPlayer(player1, "warrior");
        SetPlayerManager.addSetPlayer(player1, "mage");

        assertTrue(SetPlayerManager.isWearingSet(player1));
        SetPlayer sp = SetPlayerManager.getSetPlayer(player1);
        assertNotNull(sp);
    }

    @Test
    void removeSetPlayer_playerWearing_removesSuccessfully() {
        SetPlayerManager.addSetPlayer(player1, "warrior");
        SetPlayerManager.removeSetPlayer(player1);

        assertFalse(SetPlayerManager.isWearingSet(player1));
    }

    @Test
    void removeSetPlayer_playerNotWearing_noError() {
        assertDoesNotThrow(() -> SetPlayerManager.removeSetPlayer(player1));
    }

    @Test
    void isWearingSet_uninitializedMap_returnsFalse() {
        assertFalse(SetPlayerManager.isWearingSet(player2));
    }

    @Test
    void multiplePlayers_independentTracking() {
        SetPlayerManager.addSetPlayer(player1, "warrior");
        SetPlayerManager.addSetPlayer(player2, "mage");

        assertTrue(SetPlayerManager.isWearingSet(player1));
        assertTrue(SetPlayerManager.isWearingSet(player2));

        SetPlayerManager.removeSetPlayer(player1);
        assertFalse(SetPlayerManager.isWearingSet(player1));
        assertTrue(SetPlayerManager.isWearingSet(player2));
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

        int result = SetPlayerManager.getPiecesWearing(set, player1);
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

        int result = SetPlayerManager.getPiecesWearing(set, player1);
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

        int result = SetPlayerManager.getPiecesWearing(set, player1);
        assertEquals(2, result);
    }
}
