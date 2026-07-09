package gg.steve.mc.ap.model.ability;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArmorSetBasicStats {
    double increase;
    double reduction;
    double knockback;
    double health;
    float walkSpeed;
    float walkSpeedDefault;
    float flySpeed;
    float flySpeedDefault;
}
