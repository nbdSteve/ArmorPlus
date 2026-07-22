package gg.steve.mc.ap.model.set;

import gg.steve.mc.ap.model.id.ArmorSetId;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Pure registry of the armor sets known to the plugin, keyed by {@link ArmorSetId}.
 * Preserves registration order so callers that iterate see a deterministic sequence.
 * <p>
 * Generic over the stored set type: the pure domain does not yet own an {@code ArmorSet}
 * aggregate, so this registry stays value-type-agnostic and Bukkit-free while the
 * platform layer parameterizes it over its own set representation.
 *
 * @param <T> the stored representation of an armor set
 */
public final class ArmorSetRegistry<T> {
    private final Map<ArmorSetId, T> sets = new LinkedHashMap<>();

    /** Register (or replace) the set stored under the given id. */
    public void register(ArmorSetId id, T set) {
        sets.put(id, set);
    }

    /** The set stored under the given id, if one exists. */
    public Optional<T> get(ArmorSetId id) {
        return Optional.ofNullable(sets.get(id));
    }

    /** An unmodifiable view of every registered set, in registration order. */
    public Map<ArmorSetId, T> getAll() {
        return Collections.unmodifiableMap(sets);
    }

    /** Number of registered sets. */
    public int count() {
        return sets.size();
    }

    /** Drop all registered sets. */
    public void clear() {
        sets.clear();
    }
}
