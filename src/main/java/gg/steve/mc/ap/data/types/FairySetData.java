package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.data.SetData;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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
    private List<UUID> fairyPlayers;
    private int state, r, g, b, count, colorJump, topBottomDelay;
    private Long runnableDelay;

    public FairySetData(ConfigurationSection section, String entry) {
        runnableDelay = section.getLong(entry + ".runnable-delay");
        colorJump = section.getInt(entry + ".color-jump");
        topBottomDelay = section.getInt(entry + ".top-to-bottom-delay");
        if (fairyPlayers == null) {
            initialise();
        }
    }

    public void initialise() {
        fairyPlayers = new ArrayList<>();
        r = 255;
        count = 0;
        Bukkit.getScheduler().runTaskTimer(ArmorPlus.get(), () -> {
            if (fairyPlayers.isEmpty()) return;
            if (state == 0) {
                g += colorJump;
                if (g == 255)
                    state = 1;
            }
            if (state == 1) {
                r -= colorJump;
                if (r == 0)
                    state = 2;
            }
            if (state == 2) {
                b += colorJump;
                if (b == 255)
                    state = 3;
            }
            if (state == 3) {
                g -= colorJump;
                if (g == 0)
                    state = 4;
            }
            if (state == 4) {
                r += colorJump;
                if (r == 255)
                    state = 5;
            }
            if (state == 5) {
                b -= colorJump;
                if (b == 0)
                    state = 0;
            }
            if (topBottomDelay != -1) {
                count++;
            }
            Color color = Color.fromRGB(r, g, b);
            for (UUID playerId : fairyPlayers) {
                Player player = Bukkit.getPlayer(playerId);
                if (topBottomDelay == -1) {
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
                } else {
                    if (count < topBottomDelay) {
                        if (player.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET)) {
                            player.getInventory().setHelmet(getColorArmor(player.getInventory().getHelmet(), color));
                        }
                    } else if (count < topBottomDelay * 2) {
                        if (player.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE)) {
                            player.getInventory().setChestplate(getColorArmor(player.getInventory().getChestplate(), color));
                        }
                    } else if (count < topBottomDelay * 3) {
                        if (player.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS)) {
                            player.getInventory().setLeggings(getColorArmor(player.getInventory().getLeggings(), color));
                        }
                    } else if (count < topBottomDelay * 4) {
                        if (player.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                            player.getInventory().setBoots(getColorArmor(player.getInventory().getBoots(), color));
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }, 0L, runnableDelay);
    }

    public boolean isFairyPlayer(UUID playerId) {
        return fairyPlayers.contains(playerId);
    }

    public void addFairyPlayer(UUID playerId) {
        if (fairyPlayers.contains(playerId)) return;
        fairyPlayers.add(playerId);
    }

    public void removeFairyPlayer(UUID playerId) {
        if (!fairyPlayers.contains(playerId)) return;
        fairyPlayers.remove(playerId);
    }

    public ItemStack getColorArmor(ItemStack item, Color color) {
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
    public List<UUID> getFairyPlayers() {
        return fairyPlayers;
    }

    public void setFairyPlayers(List<UUID> fairyPlayers) {
        this.fairyPlayers = fairyPlayers;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
