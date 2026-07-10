package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorPieceSlot;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

/**
 * Snapshot of what armor set pieces a player is currently wearing.
 * Maps each occupied slot to the armor set it belongs to.
 */
@Value
@Builder
public class WornArmor {
    @Singular Map<ArmorPieceSlot, ArmorSetId> slots;

    public Optional<ArmorSetId> getSlot(ArmorPieceSlot slot) {
        return Optional.ofNullable(slots.get(slot));
    }

    public boolean isEmpty() {
        return slots.isEmpty();
    }
}
