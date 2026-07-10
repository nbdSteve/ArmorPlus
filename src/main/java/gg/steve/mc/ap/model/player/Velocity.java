package gg.steve.mc.ap.model.player;

import lombok.Value;

/**
 * A 3D velocity vector, independent of any Bukkit Vector type.
 */
@Value
public class Velocity {
    double x;
    double y;
    double z;
}
