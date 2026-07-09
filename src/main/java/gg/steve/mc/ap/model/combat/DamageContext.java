package gg.steve.mc.ap.model.combat;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class DamageContext {
    UUID attacker;
    UUID target;
    double baseDamage;
    String cause;
    boolean projectile;
}
