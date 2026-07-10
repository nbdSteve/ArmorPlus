package gg.steve.mc.ap.model.player;

import lombok.Value;
import org.apache.commons.lang3.Validate;

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

    public WorldPosition(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = Validate.notNull(world, "world must not be null");
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
