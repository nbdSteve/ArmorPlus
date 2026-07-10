package gg.steve.mc.ap.model.port.inbound;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.player.WornArmor;
import gg.steve.mc.ap.model.set.ArmorPieceSlot;

/**
 * Inbound port for armor set equip/unequip lifecycle events.
 * Adapters (Bukkit listeners) call these methods when equipment changes;
 * the domain updates wearer tracking and applies/removes set abilities.
 * Implementations live outside the model package.
 */
public interface ArmorSetEquipPort {

    void onEquip(PlayerId playerId, ArmorSetId setId, ArmorPieceSlot slot);

    void onUnequip(PlayerId playerId, ArmorSetId setId, ArmorPieceSlot slot);

    void onJoin(PlayerId playerId, WornArmor currentArmor);

    void onQuit(PlayerId playerId);
}
