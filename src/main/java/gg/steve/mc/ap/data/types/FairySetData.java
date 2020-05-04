package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FairySetData implements SetData {
    private static List<UUID> fairyPlayers;
    private static int state, r, g, b;

    public FairySetData() {
        if (fairyPlayers == null) {
            initialise();
        }
    }

    public static void initialise() {
        fairyPlayers = new ArrayList<>();
        r = 255;
        Bukkit.getScheduler().runTaskTimer(ArmorPlus.get(), () -> {
            if (fairyPlayers.isEmpty()) return;
            if (state == 0) {
                g++;
                if (g == 255)
                    state = 1;
            }
            if (state == 1) {
                r--;
                if (r == 0)
                    state = 2;
            }
            if (state == 2) {
                b++;
                if (b == 255)
                    state = 3;
            }
            if (state == 3) {
                g--;
                if (g == 0)
                    state = 4;
            }
            if (state == 4) {
                r++;
                if (r == 255)
                    state = 5;
            }
            if (state == 5) {
                b--;
                if (b == 0)
                    state = 0;
            }
            Color color = Color.fromRGB(r, g, b);
            for (UUID playerId : fairyPlayers) {
                Player player = Bukkit.getPlayer(playerId);
                if (player.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET)) {
                    player.getInventory().setHelmet(getColorArmor(player.getInventory().getHelmet(), color));
                }
                if (player.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE)) {
                    player.getInventory().setChestplate(getColorArmor(player.getInventory().getChestplate(), color));
                }
                if (player.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS)) {
                    player.getInventory().setLeggings(getColorArmor(player.getInventory().getLeggings(), color));
                }
                if (player.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                    player.getInventory().setBoots(getColorArmor(player.getInventory().getBoots(), color));
                }
            }
        }, 0L, ConfigManager.CONFIG.get().getLong("fairy-delay"));
    }

    public static boolean isFairyPlayer(UUID playerId) {
        return fairyPlayers.contains(playerId);
    }

    public static void addFairyPlayer(UUID playerId) {
        if (fairyPlayers.contains(playerId)) return;
        fairyPlayers.add(playerId);
    }

    public static void removeFairyPlayer(UUID playerId) {
        if (!fairyPlayers.contains(playerId)) return;
        fairyPlayers.remove(playerId);
    }

    public static ItemStack getColorArmor(ItemStack item, Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onApply(Player player) {
        fairyPlayers.add(player.getUniqueId());
    }

    @Override
    public void onRemoval(Player player) {
        fairyPlayers.remove(player.getUniqueId());
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, Player damager) {

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {

    }

    @Override
    public void onFall(EntityDamageEvent event) {

    }

    @Override
    public void onHungerDeplete(FoodLevelChangeEvent event) {

    }

    // <-- Getters and Setters from this point on -->
    public static List<UUID> getFairyPlayers() {
        return fairyPlayers;
    }

    public static void setFairyPlayers(List<UUID> fairyPlayers) {
        FairySetData.fairyPlayers = fairyPlayers;
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        FairySetData.state = state;
    }

    public static int getR() {
        return r;
    }

    public static void setR(int r) {
        FairySetData.r = r;
    }

    public static int getG() {
        return g;
    }

    public static void setG(int g) {
        FairySetData.g = g;
    }

    public static int getB() {
        return b;
    }

    public static void setB(int b) {
        FairySetData.b = b;
    }
}
