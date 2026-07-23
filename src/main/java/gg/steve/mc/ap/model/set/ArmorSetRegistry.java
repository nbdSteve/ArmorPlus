package gg.steve.mc.ap.model.set;

import gg.steve.mc.ap.model.id.ArmorSetId;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

// Generic over the set type: the pure domain has no ArmorSet aggregate yet, so the platform layer supplies its own.
public final class ArmorSetRegistry<T> {
    private final Map<ArmorSetId, T> sets = new LinkedHashMap<>();

    public void register(ArmorSetId id, T set) {
        sets.put(id, set);
    }

    public Optional<T> get(ArmorSetId id) {
        return Optional.ofNullable(sets.get(id));
    }

    public Map<ArmorSetId, T> getAll() {
        return Collections.unmodifiableMap(sets);
    }

    public int count() {
        return sets.size();
    }

    public void clear() {
        sets.clear();
    }
}
