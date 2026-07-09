package gg.steve.mc.ap.model.port.inbound;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.set.ArmorPieceSlot;
import gg.steve.mc.ap.model.set.GiveResult;

/**
 * Inbound port for administrative commands (give, gui, reload).
 * Adapters (command executors) parse arguments and call these methods;
 * the domain performs the requested action. Implementations live outside
 * the model package.
 */
public interface ArmorCommandPort {

    GiveResult giveSet(ArmorSetId setId, ArmorPieceSlot piece, PlayerId target, int amount);

    void openGui(PlayerId playerId, ArmorSetId setId);

    void reload();
}
