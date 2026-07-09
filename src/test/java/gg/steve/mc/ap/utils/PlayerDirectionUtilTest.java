package gg.steve.mc.ap.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDirectionUtilTest {

    @Test
    void facingSouth_onlyPositiveZ() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(0.0);
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @Test
    void facingEast_onlyPositiveX() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(270.0);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void facingNorth_onlyNegativeZ() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(180.0);
        assertTrue(dir.negativeZ());
        assertFalse(dir.positiveZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @Test
    void facingWest_onlyNegativeX() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(90.0);
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void negativeYaw_normalizesToPositive() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(-90.0);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @ParameterizedTest
    @CsvSource({
        "315.0, true, false, true, false",
        "225.0, true, false, false, true",
        "135.0, false, true, false, true",
        "45.0, false, true, true, false"
    })
    void diagonalDirections(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }
}
