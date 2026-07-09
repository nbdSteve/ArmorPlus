package gg.steve.mc.ap.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDirectionUtilTest {

    // --- Octant 1: South (facing <= 22.5 || facing > 337.5) -> positiveZ only ---

    @Test
    void south_yaw0() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(0.0);
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void south_yaw360_wrapsToZero() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(360.0);
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @Test
    void south_boundaryLow_22point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(22.5);
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @Test
    void south_boundaryHigh_337point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(337.6);
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    // --- Octant 2: South-West (22.5 < facing <= 67.5) -> negativeX + positiveZ ---

    @Test
    void southWest_yaw45() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(45.0);
        assertFalse(dir.positiveX());
        assertTrue(dir.negativeX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void southWest_boundaryLow_22point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(22.6);
        assertTrue(dir.negativeX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeZ());
    }

    @Test
    void southWest_boundaryHigh_67point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(67.5);
        assertTrue(dir.negativeX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeZ());
    }

    // --- Octant 3: West (67.5 < facing <= 112.5) -> negativeX only ---

    @Test
    void west_yaw90() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(90.0);
        assertFalse(dir.positiveX());
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void west_boundaryLow_67point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(67.6);
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void west_boundaryHigh_112point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(112.5);
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    // --- Octant 4: North-West (112.5 < facing <= 157.5) -> negativeX + negativeZ ---

    @Test
    void northWest_yaw135() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(135.0);
        assertFalse(dir.positiveX());
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertTrue(dir.negativeZ());
    }

    @Test
    void northWest_boundaryLow_112point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(112.6);
        assertTrue(dir.negativeX());
        assertTrue(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
    }

    @Test
    void northWest_boundaryHigh_157point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(157.5);
        assertTrue(dir.negativeX());
        assertTrue(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
    }

    // --- Octant 5: North (157.5 < facing <= 202.5) -> negativeZ only ---

    @Test
    void north_yaw180() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(180.0);
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertTrue(dir.negativeZ());
    }

    @Test
    void north_boundaryLow_157point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(157.6);
        assertTrue(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
    }

    @Test
    void north_boundaryHigh_202point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(202.5);
        assertTrue(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
    }

    // --- Octant 6: North-East (202.5 < facing <= 247.5) -> positiveX + negativeZ ---

    @Test
    void northEast_yaw225() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(225.0);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertTrue(dir.negativeZ());
    }

    @Test
    void northEast_boundaryLow_202point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(202.6);
        assertTrue(dir.positiveX());
        assertTrue(dir.negativeZ());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
    }

    @Test
    void northEast_boundaryHigh_247point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(247.5);
        assertTrue(dir.positiveX());
        assertTrue(dir.negativeZ());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
    }

    // --- Octant 7: East (247.5 < facing <= 292.5) -> positiveX only ---

    @Test
    void east_yaw270() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(270.0);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void east_boundaryLow_247point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(247.6);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void east_boundaryHigh_292point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(292.5);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    // --- Octant 8: South-East (292.5 < facing <= 337.5) -> positiveX + positiveZ ---

    @Test
    void southEast_yaw315() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(315.0);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void southEast_boundaryLow_292point6() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(292.6);
        assertTrue(dir.positiveX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeX());
        assertFalse(dir.negativeZ());
    }

    @Test
    void southEast_boundaryHigh_337point5() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(337.5);
        assertTrue(dir.positiveX());
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeX());
        assertFalse(dir.negativeZ());
    }

    // --- Negative yaw normalization ---

    @Test
    void negativeYaw_minus90_normalizesToEast() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(-90.0);
        assertTrue(dir.positiveX());
        assertFalse(dir.negativeX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void negativeYaw_minus180_normalizesToNorth() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(-180.0);
        assertTrue(dir.negativeZ());
        assertFalse(dir.positiveZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @Test
    void negativeYaw_minus270_normalizesToWest() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(-270.0);
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }

    @Test
    void negativeYaw_minus360_normalizesToSouth() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(-360.0);
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    // --- Large yaw values beyond 360 ---

    @Test
    void largePositiveYaw_720_normalizesToSouth() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(720.0);
        assertTrue(dir.positiveZ());
        assertFalse(dir.negativeZ());
        assertFalse(dir.positiveX());
        assertFalse(dir.negativeX());
    }

    @Test
    void largePositiveYaw_450_normalizesToWest() {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(450.0);
        assertTrue(dir.negativeX());
        assertFalse(dir.positiveX());
        assertFalse(dir.positiveZ());
        assertFalse(dir.negativeZ());
    }
}
