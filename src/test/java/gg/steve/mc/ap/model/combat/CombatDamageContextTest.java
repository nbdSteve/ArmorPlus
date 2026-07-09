package gg.steve.mc.ap.model.combat;

import gg.steve.mc.ap.model.id.DamageCauseId;
import gg.steve.mc.ap.model.id.PlayerId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CombatDamageContextTest {

    @Test
    void builderCreatesExpectedValues() {
        PlayerId attacker = PlayerId.of(UUID.randomUUID());
        PlayerId target = PlayerId.of(UUID.randomUUID());
        DamageCauseId cause = DamageCauseId.of("ENTITY_ATTACK");

        CombatDamageContext ctx = CombatDamageContext.builder()
                .attacker(attacker)
                .target(target)
                .baseDamage(10.0)
                .cause(cause)
                .projectile(false)
                .build();

        assertEquals(attacker, ctx.getAttacker());
        assertEquals(target, ctx.getTarget());
        assertEquals(10.0, ctx.getBaseDamage());
        assertEquals(cause, ctx.getCause());
        assertFalse(ctx.isProjectile());
    }

    @Test
    void equalsAndHashCode() {
        PlayerId attacker = PlayerId.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        PlayerId target = PlayerId.of(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        DamageCauseId cause = DamageCauseId.of("PROJECTILE");

        CombatDamageContext a = CombatDamageContext.builder()
                .attacker(attacker).target(target)
                .baseDamage(5.0).cause(cause).projectile(true).build();
        CombatDamageContext b = CombatDamageContext.builder()
                .attacker(attacker).target(target)
                .baseDamage(5.0).cause(cause).projectile(true).build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
