package gg.steve.mc.ap.model.combat;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DamageContextTest {

    @Test
    void builderCreatesExpectedValues() {
        UUID attacker = UUID.randomUUID();
        UUID target = UUID.randomUUID();

        DamageContext ctx = DamageContext.builder()
                .attacker(attacker)
                .target(target)
                .baseDamage(10.0)
                .cause("ENTITY_ATTACK")
                .projectile(false)
                .build();

        assertEquals(attacker, ctx.getAttacker());
        assertEquals(target, ctx.getTarget());
        assertEquals(10.0, ctx.getBaseDamage());
        assertEquals("ENTITY_ATTACK", ctx.getCause());
        assertFalse(ctx.isProjectile());
    }

    @Test
    void equalsAndHashCode() {
        UUID attacker = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID target = UUID.fromString("00000000-0000-0000-0000-000000000002");

        DamageContext a = DamageContext.builder()
                .attacker(attacker).target(target)
                .baseDamage(5.0).cause("PROJECTILE").projectile(true).build();
        DamageContext b = DamageContext.builder()
                .attacker(attacker).target(target)
                .baseDamage(5.0).cause("PROJECTILE").projectile(true).build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
