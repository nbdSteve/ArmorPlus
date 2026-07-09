package gg.steve.mc.ap.model.id;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypedStringTest {

    @Test
    void differentSubtypesWithSameStringAreNotEqual() {
        ArmorSetId armorSetId = ArmorSetId.of("SPEED");
        PotionEffectId potionEffectId = PotionEffectId.of("SPEED");

        assertNotEquals(armorSetId, potionEffectId);
    }

    @Test
    void toStringsReturnsRawStrings() {
        List<ArmorSetId> ids = Arrays.asList(
                ArmorSetId.of("dragon"),
                ArmorSetId.of("knight")
        );

        List<String> result = TypedString.toStrings(ids);

        assertEquals(Arrays.asList("dragon", "knight"), result);
    }

    @Test
    void toStringReturnsRawIdString() {
        ArmorSetId id = ArmorSetId.of("dragon");
        assertEquals("dragon", id.toString());
    }

    @Test
    void nullConstructorThrows() {
        assertThrows(NullPointerException.class, () -> ArmorSetId.of(null));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    void equalsNullReturnsFalse() {
        ArmorSetId id = ArmorSetId.of("dragon");
        assertFalse(id.equals(null));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    void equalsSelfReturnsTrue() {
        ArmorSetId id = ArmorSetId.of("dragon");
        assertTrue(id.equals(id));
    }

    @Test
    void sameTypeDifferentValueNotEqual() {
        ArmorSetId a = ArmorSetId.of("dragon");
        ArmorSetId b = ArmorSetId.of("knight");
        assertNotEquals(a, b);
    }

    @Test
    void sameTypeSameValueEqual() {
        ArmorSetId a = ArmorSetId.of("dragon");
        ArmorSetId b = ArmorSetId.of("dragon");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
