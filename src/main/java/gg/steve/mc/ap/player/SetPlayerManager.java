package gg.steve.mc.ap.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SetPlayerManager {
    private static Map<UUID, SetPlayer> playersWearingSets;

    public static void init() {
        playersWearingSets = new HashMap<>();
    }

    public static void addSetPlayer(Player player, String setName) {
        if (playersWearingSets.containsKey(player.getUniqueId())) removeSetPlayer(player);
        playersWearingSets.put(player.getUniqueId(), new SetPlayer(player, setName));
    }

    public static void removeSetPlayer(Player player) {
        playersWearingSets.remove(player.getUniqueId());
    }

    public static boolean isWearingSet(Player player) {
        return playersWearingSets.containsKey(player.getUniqueId());
    }

    public static SetPlayer getSetPlayer(Player player) {
        return playersWearingSets.get(player.getUniqueId());
    }
}