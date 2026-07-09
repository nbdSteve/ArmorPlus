package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.data.types.HandSetData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Characterization tests for BasicSetData.onHit() and onDamage().
 * Pins current damage-calculation and knockback behavior before 4D extraction.
 */
@ExtendWith(MockitoExtension.class)
class BasicSetDataTest {

    @Mock private ConfigurationSection section;
    @Mock private Set set;

    private BasicSetData basicSetData;

    @BeforeEach
    void setUp() {
        // Stub config reads that the constructor performs
        when(section.getDouble("basic.damage-increase")).thenReturn(2.0);
        when(section.getDouble("basic.damage-reduction")).thenReturn(0.5);
        when(section.getDouble("basic.kb")).thenReturn(0.8);
        when(section.getDouble("basic.health")).thenReturn(-1.0);
        when(section.getDouble("basic.speed.walk.set")).thenReturn(-1.0);
        when(section.getDouble("basic.speed.walk.default")).thenReturn(-1.0);
        when(section.getDouble("basic.speed.fly.set")).thenReturn(-1.0);
        when(section.getDouble("basic.speed.fly.default")).thenReturn(-1.0);
        // No status effects section
        when(section.getConfigurationSection("basic.status-effects")).thenReturn(null);

        basicSetData = new BasicSetData(section, "basic", set);
    }

    // --- onHit characterization ---

    @Test
    void onHit_noHandData_increaseNotDisabled_multipliesDamage(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager) {
        when(set.getHandData()).thenReturn(null);
        when(event.getDamage()).thenReturn(10.0);

        basicSetData.onHit(event, damager);

        verify(event).setDamage(20.0); // 10 * 2.0
    }

    @Test
    void onHit_increaseDisabled_doesNotModifyDamage(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager) {
        when(set.getHandData()).thenReturn(null);
        // Rebuild with increase == -1 (disabled)
        when(section.getDouble("basic.damage-increase")).thenReturn(-1.0);
        BasicSetData disabledData = new BasicSetData(section, "basic", set);

        disabledData.onHit(event, damager);

        verify(event, never()).setDamage(anyDouble());
    }

    @Test
    void onHit_withHandData_verifiedPiece_matchingCause_usesCalculateFinalDamage(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager,
            @Mock HandSetData handData, @Mock ItemStack handItem) {
        when(set.getHandData()).thenReturn(handData);
        when(damager.getItemInHand()).thenReturn(handItem);
        when(set.verifyPiece(handItem)).thenReturn(true);
        when(handData.getActiveCause()).thenReturn(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        when(event.getCause()).thenReturn(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        when(event.getDamage()).thenReturn(10.0);
        when(handData.calculateFinalDamage(10.0, 2.0)).thenReturn(25.0);

        basicSetData.onHit(event, damager);

        verify(event).setDamage(25.0);
    }

    @Test
    void onHit_withHandData_unverifiedPiece_fallsBackToBasicIncrease(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager,
            @Mock HandSetData handData, @Mock ItemStack handItem) {
        when(set.getHandData()).thenReturn(handData);
        when(damager.getItemInHand()).thenReturn(handItem);
        when(set.verifyPiece(handItem)).thenReturn(false);
        when(event.getDamage()).thenReturn(10.0);

        basicSetData.onHit(event, damager);

        // Falls to else-if branch: damage * increase = 10 * 2.0
        verify(event).setDamage(20.0);
    }

    @Test
    void onHit_withHandData_mismatchedCause_fallsBackToBasicIncrease(
            @Mock EntityDamageByEntityEvent event, @Mock Player damager,
            @Mock HandSetData handData, @Mock ItemStack handItem) {
        when(set.getHandData()).thenReturn(handData);
        when(damager.getItemInHand()).thenReturn(handItem);
        when(set.verifyPiece(handItem)).thenReturn(true);
        when(handData.getActiveCause()).thenReturn(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        when(event.getCause()).thenReturn(EntityDamageEvent.DamageCause.PROJECTILE);
        when(event.getDamage()).thenReturn(10.0);

        basicSetData.onHit(event, damager);

        verify(event).setDamage(20.0);
    }

    // --- onDamage characterization ---

    @Test
    void onDamage_reductionEnabled_reducesDamage(
            @Mock EntityDamageByEntityEvent event, @Mock Entity entity,
            @Mock Entity damagerEntity) {
        when(event.getDamage()).thenReturn(10.0);
        when(event.getEntity()).thenReturn(entity);
        when(event.getDamager()).thenReturn(damagerEntity);
        Location loc = mock(Location.class);
        when(damagerEntity.getLocation()).thenReturn(loc);
        Vector dir = new Vector(1, 0.5, 0);
        when(loc.getDirection()).thenReturn(dir);

        basicSetData.onDamage(event);

        // reduction: 10 * 0.5 = 5.0
        verify(event).setDamage(5.0);
        // kb: direction.setY(0).normalize().multiply(0.8)
        verify(entity).setVelocity(any(Vector.class));
    }

    @Test
    void onDamage_reductionDisabled_doesNotModifyDamage(
            @Mock EntityDamageByEntityEvent event, @Mock Entity entity,
            @Mock Entity damagerEntity) {
        when(section.getDouble("basic.damage-reduction")).thenReturn(-1.0);
        when(section.getDouble("basic.kb")).thenReturn(-1.0);
        BasicSetData noReduction = new BasicSetData(section, "basic", set);

        noReduction.onDamage(event);

        verify(event, never()).setDamage(anyDouble());
        verify(event, never()).getEntity();
    }

    @Test
    void onDamage_kbDisabled_noVelocityChange(
            @Mock EntityDamageByEntityEvent event) {
        when(section.getDouble("basic.kb")).thenReturn(-1.0);
        BasicSetData noKb = new BasicSetData(section, "basic", set);

        when(event.getDamage()).thenReturn(10.0);

        noKb.onDamage(event);

        verify(event).setDamage(5.0);
        verify(event, never()).getEntity();
    }

    // --- onApply / onRemoval characterization ---

    @Test
    void onApply_healthEnabled_setsMaxHealth(@Mock Player player) {
        when(section.getDouble("basic.health")).thenReturn(30.0);
        BasicSetData data = new BasicSetData(section, "basic", set);

        data.onApply(player);

        verify(player).setMaxHealth(30.0);
    }

    @Test
    void onRemoval_healthEnabled_resetsTo20(@Mock Player player) {
        when(section.getDouble("basic.health")).thenReturn(30.0);
        BasicSetData data = new BasicSetData(section, "basic", set);

        data.onRemoval(player);

        verify(player).setMaxHealth(20.0);
    }

    @Test
    void onApply_speedEnabled_setsWalkAndFlySpeed(@Mock Player player) {
        when(section.getDouble("basic.speed.walk.set")).thenReturn(0.3);
        when(section.getDouble("basic.speed.fly.set")).thenReturn(0.2);
        BasicSetData data = new BasicSetData(section, "basic", set);

        data.onApply(player);

        verify(player).setWalkSpeed(0.3f);
        verify(player).setFlySpeed(0.2f);
    }

    @Test
    void onRemoval_speedEnabled_restoresDefaults(@Mock Player player) {
        when(section.getDouble("basic.speed.walk.set")).thenReturn(0.3);
        when(section.getDouble("basic.speed.walk.default")).thenReturn(0.2);
        when(section.getDouble("basic.speed.fly.set")).thenReturn(0.15);
        when(section.getDouble("basic.speed.fly.default")).thenReturn(0.1);
        BasicSetData data = new BasicSetData(section, "basic", set);

        data.onRemoval(player);

        verify(player).setWalkSpeed(0.2f);
        verify(player).setFlySpeed(0.1f);
    }
}
