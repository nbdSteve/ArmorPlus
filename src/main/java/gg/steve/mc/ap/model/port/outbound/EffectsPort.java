package gg.steve.mc.ap.model.port.outbound;

import gg.steve.mc.ap.model.effect.PotionEffect;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.id.PotionEffectId;
import gg.steve.mc.ap.model.player.WorldPosition;

import java.util.List;

/**
 * Outbound port for applying visual/physical effects in the world.
 * The domain calls these when abilities trigger potion effects, lightning
 * strikes, TNT, or falling blocks. Adapters translate to platform API calls.
 * Implementations live outside the model package.
 */
public interface EffectsPort {

    void applyPotionEffect(PlayerId playerId, PotionEffect effect);

    void removePotionEffect(PlayerId playerId, PotionEffectId type);

    List<PotionEffect> getActivePotionEffects(PlayerId playerId);

    void strikeLightning(WorldPosition position);

    void spawnTnt(WorldPosition position, int fuse);

    void spawnFallingBlocks(List<WorldPosition> positions);
}
