package gg.steve.mc.ap.model.ability;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HandItemSpecTest {

    @Test
    void builderCreatesExpectedValues() {
        HandItemSpec spec = HandItemSpec.builder()
                .increase(2.5)
                .requireSet(true)
                .damageCause("ENTITY_ATTACK")
                .build();

        assertEquals(2.5, spec.getIncrease());
        assertTrue(spec.isRequireSet());
        assertEquals("ENTITY_ATTACK", spec.getDamageCause());
    }

    @Test
    void calculateFinalDamage_withSetIncrease_combinesBothMultipliers() {
        HandItemSpec spec = HandItemSpec.builder()
                .increase(1.5)
                .requireSet(false)
                .damageCause("ENTITY_ATTACK")
                .build();

        // setIncrease != -1: result = damage * (setIncrease - 1 + handIncrease)
        // damage=10, setIncrease=2.0 -> 10 * (2.0 - 1 + 1.5) = 10 * 2.5 = 25.0
        assertEquals(25.0, spec.calculateFinalDamage(10.0, 2.0), 0.0001);
    }

    @Test
    void calculateFinalDamage_withoutSetIncrease_usesHandMultiplierOnly() {
        HandItemSpec spec = HandItemSpec.builder()
                .increase(1.5)
                .requireSet(false)
                .damageCause("ENTITY_ATTACK")
                .build();

        // setIncrease == -1: result = damage * handIncrease
        // damage=10, setIncrease=-1 -> 10 * 1.5 = 15.0
        assertEquals(15.0, spec.calculateFinalDamage(10.0, -1), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 1.5, 3.0, 35.0",   // 10 * (3-1 + 1.5) = 10 * 3.5
            "5.0,  2.0, 1.0, 10.0",   // 5 * (1-1 + 2) = 5 * 2
            "8.0,  1.0, 2.0, 16.0",   // 8 * (2-1 + 1) = 8 * 2
            "0.0,  1.5, 2.0, 0.0",    // 0 * anything = 0
    })
    void calculateFinalDamage_withSetIncrease_parametrized(
            double damage, double increase, double setIncrease, double expected) {
        HandItemSpec spec = HandItemSpec.builder()
                .increase(increase)
                .requireSet(false)
                .damageCause("ENTITY_ATTACK")
                .build();

        assertEquals(expected, spec.calculateFinalDamage(damage, setIncrease), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 1.5, 15.0",   // 10 * 1.5
            "5.0,  2.0, 10.0",   // 5 * 2
            "0.0,  3.0, 0.0",    // 0 * anything = 0
            "8.0,  0.5, 4.0",    // 8 * 0.5
    })
    void calculateFinalDamage_withoutSetIncrease_parametrized(
            double damage, double increase, double expected) {
        HandItemSpec spec = HandItemSpec.builder()
                .increase(increase)
                .requireSet(false)
                .damageCause("ENTITY_ATTACK")
                .build();

        assertEquals(expected, spec.calculateFinalDamage(damage, -1), 0.0001);
    }

    @Test
    void calculateFinalDamage_setIncreaseOfOne_yieldsHandMultiplierOnly() {
        // Edge case: setIncrease=1 -> set part = 1-1=0, so result = damage * (0 + increase) = damage * increase
        HandItemSpec spec = HandItemSpec.builder()
                .increase(2.0)
                .requireSet(true)
                .damageCause("PROJECTILE")
                .build();

        assertEquals(20.0, spec.calculateFinalDamage(10.0, 1.0), 0.0001);
    }

    @Test
    void equalsAndHashCode() {
        HandItemSpec a = HandItemSpec.builder()
                .increase(1.5).requireSet(true).damageCause("ENTITY_ATTACK").build();
        HandItemSpec b = HandItemSpec.builder()
                .increase(1.5).requireSet(true).damageCause("ENTITY_ATTACK").build();
        HandItemSpec c = HandItemSpec.builder()
                .increase(2.0).requireSet(false).damageCause("PROJECTILE").build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void toStringContainsFields() {
        HandItemSpec spec = HandItemSpec.builder()
                .increase(1.5).requireSet(true).damageCause("ENTITY_ATTACK").build();
        String s = spec.toString();
        assertTrue(s.contains("1.5"));
        assertTrue(s.contains("ENTITY_ATTACK"));
    }
}
