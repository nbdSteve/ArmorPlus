package gg.steve.mc.ap.model.ability;

import gg.steve.mc.ap.model.combat.CombatDamageModification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicDamageCalculatorTest {

    // --- calculateAttackDamage ---

    @Test
    void calculateAttackDamage_increaseEnabled_multipliesDamage() {
        double result = BasicDamageCalculator.calculateAttackDamage(10.0, 2.0);
        assertEquals(20.0, result, 0.0001);
    }

    @Test
    void calculateAttackDamage_increaseDisabled_returnsBaseDamage() {
        double result = BasicDamageCalculator.calculateAttackDamage(10.0, -1);
        assertEquals(10.0, result, 0.0001);
    }

    @Test
    void calculateAttackDamage_zeroDamage_returnsZero() {
        double result = BasicDamageCalculator.calculateAttackDamage(0.0, 2.0);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void calculateAttackDamage_fractionalIncrease() {
        double result = BasicDamageCalculator.calculateAttackDamage(10.0, 0.5);
        assertEquals(5.0, result, 0.0001);
    }

    // --- calculateDefense ---

    @Test
    void calculateDefense_reductionAndKnockbackEnabled() {
        CombatDamageModification mod = BasicDamageCalculator.calculateDefense(10.0, 0.5, 0.8);
        assertEquals(5.0, mod.getNewDamage(), 0.0001);
        assertEquals(0.8, mod.getKnockbackMultiplier(), 0.0001);
    }

    @Test
    void calculateDefense_reductionDisabled() {
        CombatDamageModification mod = BasicDamageCalculator.calculateDefense(10.0, -1, 0.8);
        assertEquals(10.0, mod.getNewDamage(), 0.0001);
        assertEquals(0.8, mod.getKnockbackMultiplier(), 0.0001);
    }

    @Test
    void calculateDefense_knockbackDisabled() {
        CombatDamageModification mod = BasicDamageCalculator.calculateDefense(10.0, 0.5, -1);
        assertEquals(5.0, mod.getNewDamage(), 0.0001);
        assertEquals(-1, mod.getKnockbackMultiplier(), 0.0001);
    }

    @Test
    void calculateDefense_bothDisabled() {
        CombatDamageModification mod = BasicDamageCalculator.calculateDefense(10.0, -1, -1);
        assertEquals(10.0, mod.getNewDamage(), 0.0001);
        assertEquals(-1, mod.getKnockbackMultiplier(), 0.0001);
    }

    @Test
    void calculateDefense_zeroDamage() {
        CombatDamageModification mod = BasicDamageCalculator.calculateDefense(0.0, 0.5, 0.8);
        assertEquals(0.0, mod.getNewDamage(), 0.0001);
        assertEquals(0.8, mod.getKnockbackMultiplier(), 0.0001);
    }
}
