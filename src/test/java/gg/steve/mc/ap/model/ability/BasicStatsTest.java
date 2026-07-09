package gg.steve.mc.ap.model.ability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicStatsTest {

    @Test
    void builderCreatesExpectedValues() {
        BasicStats stats = BasicStats.builder()
                .increase(1.5)
                .reduction(0.8)
                .knockback(0.3)
                .health(30.0)
                .walkSpeed(0.25f)
                .walkSpeedDefault(0.2f)
                .flySpeed(0.15f)
                .flySpeedDefault(0.1f)
                .build();

        assertEquals(1.5, stats.getIncrease());
        assertEquals(0.8, stats.getReduction());
        assertEquals(0.3, stats.getKnockback());
        assertEquals(30.0, stats.getHealth());
        assertEquals(0.25f, stats.getWalkSpeed());
        assertEquals(0.2f, stats.getWalkSpeedDefault());
        assertEquals(0.15f, stats.getFlySpeed());
        assertEquals(0.1f, stats.getFlySpeedDefault());
    }

    @Test
    void equalsAndHashCode() {
        BasicStats a = BasicStats.builder()
                .increase(1.5).reduction(0.8).knockback(0.3).health(30.0)
                .walkSpeed(0.25f).walkSpeedDefault(0.2f)
                .flySpeed(0.15f).flySpeedDefault(0.1f).build();
        BasicStats b = BasicStats.builder()
                .increase(1.5).reduction(0.8).knockback(0.3).health(30.0)
                .walkSpeed(0.25f).walkSpeedDefault(0.2f)
                .flySpeed(0.15f).flySpeedDefault(0.1f).build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void disabledValuesUseNegativeOne() {
        BasicStats stats = BasicStats.builder()
                .increase(-1).reduction(-1).knockback(-1).health(-1)
                .walkSpeed(-1f).walkSpeedDefault(-1f)
                .flySpeed(-1f).flySpeedDefault(-1f).build();

        assertEquals(-1, stats.getIncrease());
        assertEquals(-1, stats.getReduction());
    }
}
