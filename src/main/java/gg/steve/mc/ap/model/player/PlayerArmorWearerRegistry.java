package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Pure in-memory record of which players are currently wearing which armor set.
 * Holds the {@link PlayerId}-to-{@link ArmorSetWearer} state, with no reference to
 * Bukkit types. Instances are mutable but self-contained, so they can be unit-tested
 * without any server or static-state gymnastics.
 */
public final class PlayerArmorWearerRegistry {
    private final Map<PlayerId, ArmorSetWearer> wearers = new HashMap<>();

    /** Record that a player is wearing a set, replacing any existing entry for that player. */
    public void add(ArmorSetWearer wearer) {
        wearers.put(wearer.getPlayerId(), wearer);
    }

    /** Convenience overload building the {@link ArmorSetWearer} from its identity parts. */
    public void add(PlayerId playerId, ArmorSetId setId) {
        add(new ArmorSetWearer(playerId, setId));
    }

    /** Forget any set the player was recorded as wearing. */
    public void remove(PlayerId playerId) {
        wearers.remove(playerId);
    }

    /** Whether the player is currently recorded as wearing a set. */
    public boolean isWearing(PlayerId playerId) {
        return wearers.containsKey(playerId);
    }

    /** The wearer record for a player, if one exists. */
    public Optional<ArmorSetWearer> get(PlayerId playerId) {
        return Optional.ofNullable(wearers.get(playerId));
    }

    /** Number of players currently recorded as wearing a set. */
    public int count() {
        return wearers.size();
    }

    /** Drop all wearer records. */
    public void clear() {
        wearers.clear();
    }
}
