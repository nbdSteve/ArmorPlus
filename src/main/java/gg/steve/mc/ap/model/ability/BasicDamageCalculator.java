package gg.steve.mc.ap.model.ability;

import gg.steve.mc.ap.model.combat.CombatDamageModification;

public final class BasicDamageCalculator {

    private BasicDamageCalculator() {}

    public static double calculateAttackDamage(double baseDamage, double increase) {
        if (increase != -1) {
            return baseDamage * increase;
        }
        return baseDamage;
    }

    public static CombatDamageModification calculateDefense(double baseDamage,
                                                            double reduction, double knockback) {
        double newDamage = (reduction != -1) ? baseDamage * reduction : baseDamage;
        double kb = (knockback != -1) ? knockback : -1;
        return new CombatDamageModification(newDamage, kb);
    }
}
