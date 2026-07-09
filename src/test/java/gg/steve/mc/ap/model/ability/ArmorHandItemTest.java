package gg.steve.mc.ap.model.ability;

import gg.steve.mc.ap.model.id.DamageCauseId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ArmorHandItemTest {

    private static final DamageCauseId ENTITY_ATTACK = DamageCauseId.of("ENTITY_ATTACK");
    private static final DamageCauseId PROJECTILE = DamageCauseId.of("PROJECTILE");

    @Test
    void builderCreatesExpectedValues() {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(2.5)
                .requireSet(true)
                .damageCause(ENTITY_ATTACK)
                .build();

        assertEquals(2.5, item.getIncrease());
        assertTrue(item.isRequireSet());
        assertEquals(ENTITY_ATTACK, item.getDamageCause());
    }

    @Test
    void calculateFinalDamage_withSetIncrease_combinesBothMultipliers() {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(1.5)
                .requireSet(false)
                .damageCause(ENTITY_ATTACK)
                .build();

        assertEquals(25.0, item.calculateFinalDamage(10.0, 2.0), 0.0001);
    }

    @Test
    void calculateFinalDamage_withoutSetIncrease_usesHandMultiplierOnly() {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(1.5)
                .requireSet(false)
                .damageCause(ENTITY_ATTACK)
                .build();

        assertEquals(15.0, item.calculateFinalDamage(10.0, -1), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 1.5, 3.0, 35.0",
            "5.0,  2.0, 1.0, 10.0",
            "8.0,  1.0, 2.0, 16.0",
            "0.0,  1.5, 2.0, 0.0",
    })
    void calculateFinalDamage_withSetIncrease_parametrized(
            double damage, double increase, double setIncrease, double expected) {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(increase)
                .requireSet(false)
                .damageCause(ENTITY_ATTACK)
                .build();

        assertEquals(expected, item.calculateFinalDamage(damage, setIncrease), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 1.5, 15.0",
            "5.0,  2.0, 10.0",
            "0.0,  3.0, 0.0",
            "8.0,  0.5, 4.0",
    })
    void calculateFinalDamage_withoutSetIncrease_parametrized(
            double damage, double increase, double expected) {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(increase)
                .requireSet(false)
                .damageCause(ENTITY_ATTACK)
                .build();

        assertEquals(expected, item.calculateFinalDamage(damage, -1), 0.0001);
    }

    @Test
    void calculateFinalDamage_setIncreaseOfOne_yieldsHandMultiplierOnly() {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(2.0)
                .requireSet(true)
                .damageCause(PROJECTILE)
                .build();

        assertEquals(20.0, item.calculateFinalDamage(10.0, 1.0), 0.0001);
    }

    @Test
    void equalsAndHashCode() {
        ArmorHandItem a = ArmorHandItem.builder()
                .increase(1.5).requireSet(true).damageCause(ENTITY_ATTACK).build();
        ArmorHandItem b = ArmorHandItem.builder()
                .increase(1.5).requireSet(true).damageCause(ENTITY_ATTACK).build();
        ArmorHandItem c = ArmorHandItem.builder()
                .increase(2.0).requireSet(false).damageCause(PROJECTILE).build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void toStringContainsFields() {
        ArmorHandItem item = ArmorHandItem.builder()
                .increase(1.5).requireSet(true).damageCause(ENTITY_ATTACK).build();
        String s = item.toString();
        assertTrue(s.contains("1.5"));
        assertTrue(s.contains("ENTITY_ATTACK"));
    }
}
