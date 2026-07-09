package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.data.SetDataType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Characterization tests for HandSetData.calculateFinalDamage().
 * Pins the current pure-math behavior: phases 4D+ extract this into domain.
 */
@ExtendWith(MockitoExtension.class)
class HandSetDataTest {

    @Mock private ConfigurationSection section;
    @Mock private Set set;

    private HandSetData handSetData;

    @BeforeEach
    void setUp() {
        when(section.getDouble("test-entry.damage-increase")).thenReturn(1.5);
        when(section.getBoolean("test-entry.require-set")).thenReturn(false);
        when(section.getString("test-entry.damage-cause")).thenReturn("ENTITY_ATTACK");
        handSetData = new HandSetData(section, "test-entry", set);
    }

    // --- calculateFinalDamage: pure math characterization ---

    @Test
    void calculateFinalDamage_withoutSetIncrease_multipliesByOwnIncrease() {
        // When setIncrease == -1, formula is: damage * this.increase
        double result = handSetData.calculateFinalDamage(10.0, -1);
        assertEquals(15.0, result, 0.0001);
    }

    @Test
    void calculateFinalDamage_withSetIncrease_combinesMultipliers() {
        // When setIncrease != -1, formula is: damage * ((setIncrease - 1) + this.increase)
        // With setIncrease=2.0: damage * ((2.0-1) + 1.5) = 10 * 2.5 = 25.0
        double result = handSetData.calculateFinalDamage(10.0, 2.0);
        assertEquals(25.0, result, 0.0001);
    }

    @Test
    void calculateFinalDamage_withSetIncreaseOfOne_usesOnlyHandIncrease() {
        // setIncrease=1.0: damage * ((1.0-1) + 1.5) = 10 * 1.5 = 15.0
        // NOTE: same result as -1 case by coincidence of the formula
        double result = handSetData.calculateFinalDamage(10.0, 1.0);
        assertEquals(15.0, result, 0.0001);
    }

    @Test
    void calculateFinalDamage_zeroDamage_returnsZero() {
        assertEquals(0.0, handSetData.calculateFinalDamage(0.0, -1), 0.0001);
        assertEquals(0.0, handSetData.calculateFinalDamage(0.0, 2.0), 0.0001);
    }

    @Test
    void calculateFinalDamage_setIncreaseZero_appliesNegativeComponent() {
        // setIncrease=0: damage * ((0-1) + 1.5) = 10 * 0.5 = 5.0
        // Quirk: setIncrease < 1 subtracts from the multiplier
        double result = handSetData.calculateFinalDamage(10.0, 0.0);
        assertEquals(5.0, result, 0.0001);
    }

    @Test
    void calculateFinalDamage_setIncreaseNegative_allowsNegativeMultiplier() {
        // setIncrease=-1 is treated as "no set" by the guard (returns damage*increase)
        // But setIncrease=-0.5 (any value != -1) goes through the formula:
        // 10 * ((-0.5 - 1) + 1.5) = 10 * 0.0 = 0.0
        double result = handSetData.calculateFinalDamage(10.0, -0.5);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void calculateFinalDamage_largeDamageValues() {
        double result = handSetData.calculateFinalDamage(100.0, 3.0);
        // 100 * ((3-1) + 1.5) = 100 * 3.5 = 350.0
        assertEquals(350.0, result, 0.0001);
    }

    // --- hitWithoutSetBuff characterization ---

    @Test
    void hitWithoutSetBuff_matchingCause_noRequireSet_modifiesDamage(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager) {
        when(event.getCause()).thenReturn(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        when(event.getDamage()).thenReturn(8.0);

        handSetData.hitWithoutSetBuff(event, damager);

        // damage * increase = 8 * 1.5 = 12.0
        verify(event).setDamage(EntityDamageEvent.DamageModifier.BASE, 12.0);
    }

    @Test
    void hitWithoutSetBuff_mismatchedCause_doesNotModify(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager) {
        when(event.getCause()).thenReturn(EntityDamageEvent.DamageCause.PROJECTILE);

        handSetData.hitWithoutSetBuff(event, damager);

        verify(event, never()).setDamage(any(EntityDamageEvent.DamageModifier.class), anyDouble());
    }

    @Test
    void hitWithoutSetBuff_requireSet_doesNotModify(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager) {
        // Rebuild with requireSet=true
        when(section.getBoolean("test-entry.require-set")).thenReturn(true);
        HandSetData requireSetData = new HandSetData(section, "test-entry", set);

        when(event.getCause()).thenReturn(EntityDamageEvent.DamageCause.ENTITY_ATTACK);

        requireSetData.hitWithoutSetBuff(event, damager);

        verify(event, never()).setDamage(any(EntityDamageEvent.DamageModifier.class), anyDouble());
    }

    // --- Getter/type characterization ---

    @Test
    void constructor_setsTypeToHand() {
        assertEquals(SetDataType.HAND, handSetData.getType());
    }

    @Test
    void getIncrease_returnsConfiguredValue() {
        assertEquals(1.5, handSetData.getIncrease(), 0.0001);
    }

    @Test
    void getReduction_alwaysZero() {
        assertEquals(0.0, handSetData.getReduction(), 0.0001);
    }

    @Test
    void requiresFullSet_returnsFalseWhenConfigured() {
        assertFalse(handSetData.requiresFullSet());
    }

    @Test
    void getActiveCause_returnsConfiguredCause() {
        assertEquals(EntityDamageEvent.DamageCause.ENTITY_ATTACK, handSetData.getActiveCause());
    }
}
