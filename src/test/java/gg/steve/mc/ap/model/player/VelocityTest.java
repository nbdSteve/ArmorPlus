package gg.steve.mc.ap.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VelocityTest {

    @Test
    void fieldsAreAccessible() {
        Velocity v = new Velocity(1.5, -2.0, 3.7);
        assertEquals(1.5, v.getX());
        assertEquals(-2.0, v.getY());
        assertEquals(3.7, v.getZ());
    }

    @Test
    void equalsAndHashCode() {
        Velocity a = new Velocity(1.0, 2.0, 3.0);
        Velocity b = new Velocity(1.0, 2.0, 3.0);
        Velocity c = new Velocity(0.0, 2.0, 3.0);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
