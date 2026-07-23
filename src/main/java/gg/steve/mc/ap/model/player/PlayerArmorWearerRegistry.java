package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class PlayerArmorWearerRegistry {
    private final Map<PlayerId, ArmorSetWearer> wearers = new HashMap<>();

    public void add(ArmorSetWearer wearer) {
        wearers.put(wearer.getPlayerId(), wearer);
    }

    public void add(PlayerId playerId, ArmorSetId setId) {
        add(new ArmorSetWearer(playerId, setId));
    }

    public void remove(PlayerId playerId) {
        wearers.remove(playerId);
    }

    public boolean isWearing(PlayerId playerId) {
        return wearers.containsKey(playerId);
    }

    public Optional<ArmorSetWearer> get(PlayerId playerId) {
        return Optional.ofNullable(wearers.get(playerId));
    }

    public int count() {
        return wearers.size();
    }

    public void clear() {
        wearers.clear();
    }
}
