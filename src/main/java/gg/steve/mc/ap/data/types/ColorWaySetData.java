package gg.steve.mc.ap.data.types;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.data.AbstractSetData;
import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.data.SetDataType;
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

import java.util.*;

public class ColorWaySetData extends AbstractSetData implements SetData {
    private List<UUID> colorWayPlayers;
    private Map<ColorType, List<Integer>> colorMap;
    private int r, g, b, rCount, gCount, bCount, count, topBottomDelay;
    private Long runnableDelay;

    public enum ColorType {
        RED,
        GREEN,
        BLUE;
    }

    public ColorWaySetData(ConfigurationSection section, String entry, Set set) {
        super(SetDataType.COLOR_WAY, section, entry, set);
        runnableDelay = section.getLong(entry + ".runnable-delay");
        topBottomDelay = section.getInt(entry + ".top-to-bottom-delay");
        colorMap = new HashMap<>();
        for (ColorType color : ColorType.values()) {
            colorMap.put(color, new ArrayList<>(section.getIntegerList(entry + "." + color.name().toLowerCase())));
        }
        if (colorWayPlayers == null) {
            initialise();
        }
    }

    public void initialise() {
        colorWayPlayers = new ArrayList<>();
        Bukkit.getScheduler().runTaskTimer(ArmorPlus.get(), () -> {
            if (colorWayPlayers.isEmpty()) return;
            try {
                r = colorMap.get(ColorType.RED).get(rCount);
            } catch (IndexOutOfBoundsException e) {
                rCount = 0;
                r = colorMap.get(ColorType.RED).get(rCount);
            }
            try {
                g = colorMap.get(ColorType.GREEN).get(gCount);
            } catch (IndexOutOfBoundsException e) {
                gCount = 0;
                g = colorMap.get(ColorType.GREEN).get(gCount);
            }
            try {
                b = colorMap.get(ColorType.BLUE).get(bCount);
            } catch (IndexOutOfBoundsException e) {
                bCount = 0;
                b = colorMap.get(ColorType.BLUE).get(bCount);
            }
            rCount++;
            gCount++;
            bCount++;
            if (topBottomDelay != -1) {
                count++;
            }
            Color color = Color.fromRGB(r, g, b);
            for (UUID playerId : colorWayPlayers) {
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

    public boolean isColorWayPlayer(UUID playerId) {
        return colorWayPlayers.contains(playerId);
    }

    public void addColorWayPlayer(UUID playerId) {
        if (colorWayPlayers.contains(playerId)) return;
        colorWayPlayers.add(playerId);
    }

    public void removeColorWayPlayer(UUID playerId) {
        if (!colorWayPlayers.contains(playerId)) return;
        colorWayPlayers.remove(playerId);
    }

    public ItemStack getColorArmor(ItemStack item, Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onApply(Player player) {
        colorWayPlayers.add(player.getUniqueId());
    }

    @Override
    public void onRemoval(Player player) {
        colorWayPlayers.remove(player.getUniqueId());
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
}
