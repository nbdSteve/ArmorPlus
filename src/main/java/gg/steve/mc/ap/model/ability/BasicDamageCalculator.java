package gg.steve.mc.ap.model.ability;

import gg.steve.mc.ap.model.combat.CombatDamageModification;

/**
 * Pure damage calculations for the basic set ability; BasicSetData.onHit/onDamage delegate here.
 */
public final class BasicDamageCalculator {

    private BasicDamageCalculator() {}

    /**
     * Attack damage multiplier. When increase is -1, the multiplier is disabled
     * and the base damage is returned unchanged.
     */
    public static double calculateAttackDamage(double baseDamage, double increase) {
        if (increase != -1) {
            return baseDamage * increase;
        }
        return baseDamage;
    }

    /**
     * Defense reduction plus knockback multiplier. When reduction is -1, the damage
     * is unchanged; the knockback value is passed through (-1 means disabled).
     */
    public static CombatDamageModification calculateDefense(double baseDamage,
                                                            double reduction, double knockback) {
        double newDamage = (reduction != -1) ? baseDamage * reduction : baseDamage;
        return new CombatDamageModification(newDamage, knockback);
    }
}
