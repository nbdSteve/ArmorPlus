package gg.steve.mc.ap.model.port.inbound;

import gg.steve.mc.ap.model.ability.ArmorSetBasicStats;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;

import java.util.Optional;

/**
 * Inbound port for read-only queries about wearer state.
 * Adapters (PAPI expansion, commands) call these to inspect the domain's
 * current view of who is wearing what. Implementations live outside the
 * model package.
 */
public interface ArmorSetQueryPort {

    boolean isWearingSet(PlayerId playerId);

    Optional<ArmorSetId> getCurrentSet(PlayerId playerId);

    int getPiecesWearing(ArmorSetId setId, PlayerId playerId);

    Optional<ArmorSetBasicStats> getBasicStats(PlayerId playerId);
}
