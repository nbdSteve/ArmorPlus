package gg.steve.mc.ap.armor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void allValues_present() {
        Piece[] values = Piece.values();
        assertEquals(5, values.length);
        assertEquals(Piece.HELMET, values[0]);
        assertEquals(Piece.CHESTPLATE, values[1]);
        assertEquals(Piece.LEGGINGS, values[2]);
        assertEquals(Piece.BOOTS, values[3]);
        assertEquals(Piece.HAND, values[4]);
    }

    @Test
    void valueOf_roundTrips() {
        for (Piece p : Piece.values()) {
            assertEquals(p, Piece.valueOf(p.name()));
        }
    }
}
