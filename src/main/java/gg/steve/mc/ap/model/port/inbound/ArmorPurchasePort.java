package gg.steve.mc.ap.model.port.inbound;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.set.ArmorPieceSlot;
import gg.steve.mc.ap.model.set.ArmorSetPurchaseResult;

/**
 * Inbound port for set piece purchases.
 * Adapters (GUI click handlers) call this when a player attempts to buy
 * an armor piece; the domain checks economy and permissions, returning
 * the outcome. Implementations live outside the model package.
 */
public interface ArmorPurchasePort {

    ArmorSetPurchaseResult purchase(PlayerId buyer, ArmorSetId setId, ArmorPieceSlot piece);
}
