package gg.steve.mc.ap.model.port.inbound;

import gg.steve.mc.ap.model.combat.CombatDamageContext;
import gg.steve.mc.ap.model.combat.CombatDamageModification;
import gg.steve.mc.ap.model.id.PlayerId;

import java.util.Optional;

/**
 * Inbound port for combat-related domain events.
 * Adapters (Bukkit damage listeners) translate platform events into domain
 * contexts and call these methods; the domain applies set abilities and returns
 * damage modifications. Implementations live outside the model package.
 */
public interface ArmorCombatPort {

    Optional<CombatDamageModification> onDealDamage(CombatDamageContext context);

    Optional<CombatDamageModification> onTakeDamage(CombatDamageContext context);

    boolean onFallDamage(PlayerId playerId);

    boolean onHungerDeplete(PlayerId playerId);

    void onTargetKilled(PlayerId killerId, int baseXp);
}
