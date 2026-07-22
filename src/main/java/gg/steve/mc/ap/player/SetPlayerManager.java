package gg.steve.mc.ap.player;

import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.player.PlayerArmorWearerRegistry;
import gg.steve.mc.ap.nbt.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SetPlayerManager {
    private static final PlayerArmorWearerRegistry registry = new PlayerArmorWearerRegistry();

    public static void init() {
        registry.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Set set : SetManager.getSets().values()) {
                if (!set.isWearingSet(player, null, null)) continue;
                SetPlayerManager.addSetPlayer(player, set.getName());
                set.apply(player);
                return;
            }
        }
    }

    public static void addSetPlayer(Player player, String setName) {
        registry.add(PlayerId.of(player.getUniqueId()), ArmorSetId.of(setName));
    }

    public static void removeSetPlayer(Player player) {
        registry.remove(PlayerId.of(player.getUniqueId()));
    }

    public static boolean isWearingSet(Player player) {
        return registry.isWearing(PlayerId.of(player.getUniqueId()));
    }

    public static SetPlayer getSetPlayer(Player player) {
        return registry.get(PlayerId.of(player.getUniqueId()))
                .map(wearer -> new SetPlayer(player, wearer.getSetId().toString()))
                .orElse(null);
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
