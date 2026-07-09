package gg.steve.mc.ap.model.ability;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HandItemSpec {
    double increase;
    boolean requireSet;
    String damageCause;

    /**
     * Pure damage calculation mirroring HandSetData.calculateFinalDamage.
     * When setIncrease is -1, the set bonus is not applied.
     */
    public double calculateFinalDamage(double damage, double setIncrease) {
        if (setIncrease != -1) {
            double set = setIncrease - 1;
            return damage * (set + this.increase);
        }
        return damage * this.increase;
    }
}
