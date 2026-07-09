package gg.steve.mc.ap.model.combat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DamageModificationTest {

    @Test
    void constructsWithExpectedValues() {
        DamageModification mod = new DamageModification(15.0, 0.5);
        assertEquals(15.0, mod.getNewDamage());
        assertEquals(0.5, mod.getKnockbackMultiplier());
    }

    @Test
    void equalsAndHashCode() {
        DamageModification a = new DamageModification(10.0, 1.0);
        DamageModification b = new DamageModification(10.0, 1.0);
        DamageModification c = new DamageModification(10.0, 2.0);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
