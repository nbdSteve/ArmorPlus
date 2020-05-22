package gg.steve.mc.ap.player;

import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.utils.LogUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
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

    public static int getPiecesWearing(Set set, Player player) {
        int wearing = 0;
        for (Piece piece : set.getSetPieces().keySet()) {
            switch (piece) {
                case HELMET:
                    if (set.verifyPiece(player.getInventory().getHelmet())) wearing++;
                    break;
                case CHESTPLATE:
                    if (set.verifyPiece(player.getInventory().getChestplate())) wearing++;
                    break;
                case LEGGINGS:
                    if (set.verifyPiece(player.getInventory().getLeggings())) wearing++;
                    break;
                case BOOTS:
                    if (set.verifyPiece(player.getInventory().getBoots())) wearing++;
                    break;
                case HAND:
                    if (set.verifyPiece(player.getInventory().getItemInHand())) wearing++;
                    break;
            }
        }
        return wearing;
    }

    public static Set getSetFromHand(Player player) {
        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) return null;
        NBTItem hand = new NBTItem(player.getItemInHand());
        if (hand.getString("armor+.set").equalsIgnoreCase("")) return null;
        return SetManager.getSet(hand.getString("armor+.set"));
    }
}