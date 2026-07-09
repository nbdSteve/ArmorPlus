package gg.steve.mc.ap.armor;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Characterization tests for SetStatusEffectsManager.potionCheck().
 * Pins: removes existing effect if its amplifier <= the new amplifier.
 */
@ExtendWith(MockitoExtension.class)
class SetStatusEffectsManagerTest {

    @Mock private Player player;

    @Test
    void potionCheck_playerHasLowerAmplifier_removesEffect() {
        PotionEffect active = new PotionEffect(PotionEffectType.SPEED, 100, 1); // amplifier 1
        Collection<PotionEffect> effects = Collections.singletonList(active);
        when(player.getActivePotionEffects()).thenReturn(effects);

        // New effect has amplifier 2, which is > active's 1
        SetStatusEffectsManager.potionCheck(player, PotionEffectType.SPEED, 2);

        verify(player).removePotionEffect(PotionEffectType.SPEED);
    }

    @Test
    void potionCheck_playerHasEqualAmplifier_removesEffect() {
        PotionEffect active = new PotionEffect(PotionEffectType.SPEED, 100, 2); // amplifier 2
        Collection<PotionEffect> effects = Collections.singletonList(active);
        when(player.getActivePotionEffects()).thenReturn(effects);

        // Equal amplifier: active.amplifier(2) <= new amplifier(2) -> removes
        SetStatusEffectsManager.potionCheck(player, PotionEffectType.SPEED, 2);

        verify(player).removePotionEffect(PotionEffectType.SPEED);
    }

    @Test
    void potionCheck_playerHasHigherAmplifier_doesNotRemove() {
        PotionEffect active = new PotionEffect(PotionEffectType.SPEED, 100, 3); // amplifier 3
        Collection<PotionEffect> effects = Collections.singletonList(active);
        when(player.getActivePotionEffects()).thenReturn(effects);

        // New effect has amplifier 2, lower than active's 3 -> does not remove
        SetStatusEffectsManager.potionCheck(player, PotionEffectType.SPEED, 2);

        verify(player, never()).removePotionEffect(any(PotionEffectType.class));
    }

    @Test
    void potionCheck_differentEffectType_doesNotRemove() {
        PotionEffect active = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);
        Collection<PotionEffect> effects = Collections.singletonList(active);
        when(player.getActivePotionEffects()).thenReturn(effects);

        // Checking SPEED, player has REGENERATION -> skip
        SetStatusEffectsManager.potionCheck(player, PotionEffectType.SPEED, 2);

        verify(player, never()).removePotionEffect(any(PotionEffectType.class));
    }

    @Test
    void potionCheck_multipleEffects_onlyRemovesMatching() {
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100, 1);
        PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 100, 5);
        Collection<PotionEffect> effects = Arrays.asList(speed, regen);
        when(player.getActivePotionEffects()).thenReturn(effects);

        SetStatusEffectsManager.potionCheck(player, PotionEffectType.SPEED, 2);

        verify(player).removePotionEffect(PotionEffectType.SPEED);
        verify(player, never()).removePotionEffect(PotionEffectType.REGENERATION);
    }

    @Test
    void potionCheck_noActiveEffects_doesNothing() {
        when(player.getActivePotionEffects()).thenReturn(Collections.emptyList());

        SetStatusEffectsManager.potionCheck(player, PotionEffectType.SPEED, 2);

        verify(player, never()).removePotionEffect(any(PotionEffectType.class));
    }
}
