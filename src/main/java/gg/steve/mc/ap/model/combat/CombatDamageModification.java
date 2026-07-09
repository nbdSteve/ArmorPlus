package gg.steve.mc.ap.model.combat;

import lombok.Value;

/**
 * Represents a modification to damage and knockback.
 * A null CombatDamageModification at call sites means "no change" - callers should use
 * Optional<CombatDamageModification> to represent the absence of modification.
 */
@Value
public class CombatDamageModification {
    double newDamage;
    double knockbackMultiplier;
}
