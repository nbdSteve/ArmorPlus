package gg.steve.mc.ap.model.port.outbound;

import gg.steve.mc.ap.model.id.ItemHandle;

import java.util.Optional;

/**
 * Outbound port for reading and writing NBT tags on items.
 * The domain uses this to determine which set an item belongs to, and to
 * stamp newly created items with set/uuid metadata. Adapters implement this
 * via the vendored NBT-API or PersistentDataContainer.
 * Implementations live outside the model package.
 */
public interface NbtPort {

    Optional<String> getSetTag(ItemHandle item);

    ItemHandle setSetTag(ItemHandle item, String setName);

    ItemHandle setUuid(ItemHandle item, String uuid);
}
