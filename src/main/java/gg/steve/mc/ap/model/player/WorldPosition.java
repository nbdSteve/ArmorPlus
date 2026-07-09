package gg.steve.mc.ap.model.player;

import lombok.Value;

/**
 * A position in the world, independent of any Bukkit Location type.
 */
@Value
public class WorldPosition {
    String world;
    double x;
    double y;
    double z;
    float yaw;
    float pitch;
}
