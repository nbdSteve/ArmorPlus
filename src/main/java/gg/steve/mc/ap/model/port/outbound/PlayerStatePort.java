package gg.steve.mc.ap.model.port.outbound;

import gg.steve.mc.ap.model.id.ItemHandle;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.player.Velocity;
import gg.steve.mc.ap.model.player.WorldPosition;
import gg.steve.mc.ap.model.player.WornArmor;

import java.util.Optional;

/**
 * Outbound port for reading and modifying player state.
 * The domain calls these methods when abilities need to inspect or alter
 * player attributes. Adapters implement this by delegating to the platform
 * Player API. Implementations live outside the model package.
 */
public interface PlayerStatePort {

    WornArmor getWornArmor(PlayerId playerId);

    Optional<ItemHandle> getHeldItem(PlayerId playerId);

    void setWalkSpeed(PlayerId playerId, float speed);

    void setFlySpeed(PlayerId playerId, float speed);

    void setMaxHealth(PlayerId playerId, double maxHealth);

    void damage(PlayerId target, double amount, PlayerId source);

    void teleport(PlayerId playerId, WorldPosition position);

    void setVelocity(PlayerId playerId, Velocity velocity);

    void setFoodLevel(PlayerId playerId, int level);
}
