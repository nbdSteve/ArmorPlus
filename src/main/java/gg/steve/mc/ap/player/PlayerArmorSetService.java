package gg.steve.mc.ap.player;

import com.google.inject.Inject;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import gg.steve.mc.ap.model.player.PlayerArmorWearerRegistry;
import gg.steve.mc.ap.nbt.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerArmorSetService {
    private final PlayerArmorWearerRegistry registry;
    private final ArmorSetCatalog catalog;

    @Inject
    public PlayerArmorSetService(PlayerArmorWearerRegistry registry, ArmorSetCatalog catalog) {
        this.registry = registry;
        this.catalog = catalog;
    }

    public void init() {
        registry.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Set set : catalog.getSets().values()) {
                if (!set.isWearingSet(player, null, null)) continue;
                addSetPlayer(player, set.getName());
                set.apply(player);
                return;
            }
        }
    }

    public void addSetPlayer(Player player, String setName) {
        registry.add(PlayerId.of(player.getUniqueId()), ArmorSetId.of(setName));
    }

    public void removeSetPlayer(Player player) {
        registry.remove(PlayerId.of(player.getUniqueId()));
    }

    public boolean isWearingSet(Player player) {
        return registry.isWearing(PlayerId.of(player.getUniqueId()));
    }

    public SetPlayer getSetPlayer(Player player) {
        return registry.get(PlayerId.of(player.getUniqueId()))
                .map(wearer -> new SetPlayer(player, catalog.getSet(wearer.getSetId().toString())))
                .orElse(null);
    }

    public int getPiecesWearing(Set set, Player player) {
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

    public Set getSetFromHand(Player player) {
        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) return null;
        NBTItem hand = new NBTItem(player.getItemInHand());
        if (hand.getString("armor+.set").equalsIgnoreCase("")) return null;
        return catalog.getSet(hand.getString("armor+.set"));
    }
}
