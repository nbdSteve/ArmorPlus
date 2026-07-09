package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.armor.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Characterization tests for ExperienceSetData.onTargetDeath() XP multiplier.
 * Pins: chance-gate behavior, min/max multiplier selection, floor-to-int.
 *
 * NOTE: Cannot mock Math.random() (crashes JVM on JDK 25), so we test
 * deterministic paths only: chance=1.0 never skips (random is [0,1) < 1.0).
 */
@ExtendWith(MockitoExtension.class)
class ExperienceSetDataTest {

    @Mock private ConfigurationSection section;
    @Mock private Set set;
    @Mock private EntityDeathEvent event;
    @Mock private Player killer;

    @BeforeEach
    void setUp() {
        // AbstractSetData takes type enum directly, no config read needed for type
    }

    private ExperienceSetData createWithChance(double chance, boolean random, double min, double max) {
        when(section.getBoolean("xp.multiplier.random")).thenReturn(random);
        when(section.getDouble("xp.chance")).thenReturn(chance);
        when(section.getDouble("xp.multiplier.minimum")).thenReturn(min);
        when(section.getDouble("xp.multiplier.maximum")).thenReturn(max);
        return new ExperienceSetData(section, "xp", set);
    }

    @Test
    void onTargetDeath_chanceOne_fixedMultiplier_multipliesXp() {
        // chance=1.0: Math.random()*1 is in [0,1), which is NEVER > 1.0
        // So the early return never triggers, and XP is always modified.
        // randomMultiplier=false means it uses minMultiplier directly.
        ExperienceSetData data = createWithChance(1.0, false, 2.0, 5.0);
        when(event.getDroppedExp()).thenReturn(10);

        data.onTargetDeath(event, killer);

        // floor(10 * 2.0) = 20
        verify(event).setDroppedExp(20);
    }

    @Test
    void onTargetDeath_chanceOne_fixedMultiplier_floorsNonRoundResult() {
        ExperienceSetData data = createWithChance(1.0, false, 2.5, 5.0);
        when(event.getDroppedExp()).thenReturn(7);

        data.onTargetDeath(event, killer);

        // floor(7 * 2.5) = floor(17.5) = 17
        verify(event).setDroppedExp(17);
    }

    @Test
    void onTargetDeath_chanceOne_zeroXp_staysZero() {
        ExperienceSetData data = createWithChance(1.0, false, 3.0, 5.0);
        when(event.getDroppedExp()).thenReturn(0);

        data.onTargetDeath(event, killer);

        // floor(0 * 3.0) = 0
        verify(event).setDroppedExp(0);
    }

    @Test
    void onTargetDeath_chanceOne_randomMultiplier_resultInRange() {
        // randomMultiplier=true: multiplier = min + (max-min)*random.nextDouble()
        // Result is in [min, max), so XP is in [floor(xp*min), floor(xp*max))
        ExperienceSetData data = createWithChance(1.0, true, 2.0, 4.0);
        when(event.getDroppedExp()).thenReturn(10);

        data.onTargetDeath(event, killer);

        // We can't control Random, but we can verify setDroppedExp was called
        // with a value in [floor(10*2), floor(10*4)] = [20, 39]
        verify(event).setDroppedExp(intThat(v -> v >= 20 && v <= 39));
    }

    @Test
    void onTargetDeath_chanceOne_multiplierOfOne_xpUnchanged() {
        // Multiplier=1.0 means XP stays the same (trivial identity)
        ExperienceSetData data = createWithChance(1.0, false, 1.0, 1.0);
        when(event.getDroppedExp()).thenReturn(15);

        data.onTargetDeath(event, killer);

        verify(event).setDroppedExp(15);
    }

    @Test
    void onTargetDeath_chanceOne_largeMultiplier_scaledCorrectly() {
        ExperienceSetData data = createWithChance(1.0, false, 10.0, 10.0);
        when(event.getDroppedExp()).thenReturn(5);

        data.onTargetDeath(event, killer);

        // floor(5 * 10.0) = 50
        verify(event).setDroppedExp(50);
    }
}
