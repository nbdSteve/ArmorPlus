package gg.steve.mc.ap.model.combat;

import gg.steve.mc.ap.model.id.DamageCauseId;
import gg.steve.mc.ap.model.id.PlayerId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CombatDamageContext {
    PlayerId attacker;
    PlayerId target;
    double baseDamage;
    DamageCauseId cause;
    boolean projectile;
}
