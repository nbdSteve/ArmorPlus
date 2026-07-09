package gg.steve.mc.ap.model.combat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatDamageModificationTest {

    @Test
    void constructsWithExpectedValues() {
        CombatDamageModification mod = new CombatDamageModification(15.0, 0.5);
        assertEquals(15.0, mod.getNewDamage());
        assertEquals(0.5, mod.getKnockbackMultiplier());
    }

    @Test
    void equalsAndHashCode() {
        CombatDamageModification a = new CombatDamageModification(10.0, 1.0);
        CombatDamageModification b = new CombatDamageModification(10.0, 1.0);
        CombatDamageModification c = new CombatDamageModification(10.0, 2.0);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
