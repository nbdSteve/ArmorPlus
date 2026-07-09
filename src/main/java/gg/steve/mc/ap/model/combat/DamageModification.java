package gg.steve.mc.ap.model.combat;

import lombok.Value;

/**
 * Represents a modification to damage and knockback.
 * A null DamageModification at call sites means "no change" - callers should use
 * Optional<DamageModification> to represent the absence of modification.
 */
@Value
public class DamageModification {
    double newDamage;
    double knockbackMultiplier;
}
