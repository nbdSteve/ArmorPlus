package gg.steve.mc.ap.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDirectionUtilTest {

    @ParameterizedTest
    @CsvSource({
        "0.0, false, false, true, false",
        "360.0, false, false, true, false",
        "22.5, false, false, true, false",
        "337.6, false, false, true, false",
        "-360.0, false, false, true, false",
        "720.0, false, false, true, false"
    })
    void south_positiveZOnly(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "45.0, false, true, true, false",
        "22.6, false, true, true, false",
        "67.5, false, true, true, false"
    })
    void southWest_negativeXAndPositiveZ(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "90.0, false, true, false, false",
        "67.6, false, true, false, false",
        "112.5, false, true, false, false",
        "-270.0, false, true, false, false"
    })
    void west_negativeXOnly(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "135.0, false, true, false, true",
        "112.6, false, true, false, true",
        "157.5, false, true, false, true"
    })
    void northWest_negativeXAndNegativeZ(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "180.0, false, false, false, true",
        "157.6, false, false, false, true",
        "202.5, false, false, false, true",
        "-180.0, false, false, false, true"
    })
    void north_negativeZOnly(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "225.0, true, false, false, true",
        "202.6, true, false, false, true",
        "247.5, true, false, false, true"
    })
    void northEast_positiveXAndNegativeZ(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "270.0, true, false, false, false",
        "247.6, true, false, false, false",
        "292.5, true, false, false, false",
        "-90.0, true, false, false, false"
    })
    void east_positiveXOnly(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "315.0, true, false, true, false",
        "292.6, true, false, true, false",
        "337.5, true, false, true, false"
    })
    void southEast_positiveXAndPositiveZ(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }

    @ParameterizedTest
    @CsvSource({
        "450.0, false, true, false, false"
    })
    void largePositiveYaw_normalizesCorrectly(double yaw, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        PlayerDirectionUtil dir = new PlayerDirectionUtil(yaw);
        assertEquals(posX, dir.positiveX(), "positiveX at yaw " + yaw);
        assertEquals(negX, dir.negativeX(), "negativeX at yaw " + yaw);
        assertEquals(posZ, dir.positiveZ(), "positiveZ at yaw " + yaw);
        assertEquals(negZ, dir.negativeZ(), "negativeZ at yaw " + yaw);
    }
}
