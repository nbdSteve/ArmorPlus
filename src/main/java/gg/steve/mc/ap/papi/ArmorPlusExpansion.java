package gg.steve.mc.ap.papi;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.player.SetPlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.nio.file.Files;

public class ArmorPlusExpansion extends PlaceholderExpansion {
    private ArmorPlus instance;

    public ArmorPlusExpansion(ArmorPlus instance) {
        this.instance = instance;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return instance.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "armor+";
    }

    @Override
    public String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";
        String fallback = ConfigManager.CONFIG.get().getString("fallback-placeholder");
        if (identifier.equalsIgnoreCase("current_name")) {
            if (SetPlayerManager.isWearingSet(player)) {
                return SetPlayerManager.getSetPlayer(player).getSet().getName();
            } else {
                return fallback;
            }
        }
        if (identifier.contains("current_increase")) {
            double increase = 0;
            if (SetPlayerManager.getSetFromHand(player) != null) {
                Set set = SetPlayerManager.getSetFromHand(player);
                if (set.getHandData() != null) {
                    if (identifier.endsWith("raw")) {
                        if (set.getHandData().requiresFullSet() && SetPlayerManager.isWearingSet(player) && SetPlayerManager.getSetPlayer(player).getSet().equals(set)) {
                            increase += set.getHandData().getIncrease() - 1;
                        } else if (!set.getHandData().requiresFullSet()) {
                            increase += set.getHandData().getIncrease() - 1;
                        }
                    } else if (identifier.endsWith("percentage")) {
                        if (set.getHandData().requiresFullSet() && SetPlayerManager.isWearingSet(player) && SetPlayerManager.getSetPlayer(player).getSet().equals(set)) {
                            increase += (set.getHandData().getIncrease() - 1) * 100;
                        } else if (!set.getHandData().requiresFullSet()) {
                            increase += (set.getHandData().getIncrease() - 1) * 100;
                        }
                    }
                }
            }
            if (SetPlayerManager.isWearingSet(player)) {
                Set set = SetPlayerManager.getSetPlayer(player).getSet();
                if (set.getBasicData().getIncrease() == -1 && increase == 0) {
                    return "Default";
                }
                if (identifier.endsWith("raw")) {
                    if (set.getBasicData().getIncrease() != -1) increase += set.getBasicData().getIncrease() - 1;
                } else if (identifier.endsWith("percentage")) {
                    if (set.getBasicData().getIncrease() != -1) increase += (set.getBasicData().getIncrease() - 1) * 100;
                }
            }
            if (!SetPlayerManager.isWearingSet(player) && increase == 0) {
                return fallback;
            }
            if (identifier.endsWith("raw")) {
                return String.valueOf(increase);
            } else if (identifier.endsWith("formatted")) {
                return ArmorPlus.formatNumber(increase);
            } else if (identifier.endsWith("percentage")) {
                return ArmorPlus.formatNumber(increase) + "%";
            }
        }
        if (identifier.contains("current_reduction")) {
            if (SetPlayerManager.isWearingSet(player)) {
                if (SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getReduction() == -1) {
                    return "Default";
                }
                if (identifier.endsWith("raw")) {
                    return String.valueOf(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getReduction());
                } else if (identifier.endsWith("formatted")) {
                    return ArmorPlus.formatNumber(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getReduction());
                } else if (identifier.endsWith("percentage")) {
                    return ArmorPlus.formatNumber(Math.abs((SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getReduction() - 1) * 100)) + "%";
                }
            } else {
                return fallback;
            }
        }
        if (identifier.contains("current_kb")) {
            if (SetPlayerManager.isWearingSet(player)) {
                if (SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getKb() == -1) {
                    return "Default";
                }
                if (identifier.endsWith("raw")) {
                    return String.valueOf(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getKb());
                } else if (identifier.endsWith("formatted")) {
                    return ArmorPlus.formatNumber(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getKb());
                }
            } else {
                return fallback;
            }
        }
        if (identifier.contains("current_health")) {
            if (SetPlayerManager.isWearingSet(player)) {
                if (SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getHealth() == -1) {
                    return "Default";
                }
                if (identifier.endsWith("raw")) {
                    return String.valueOf(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getHealth());
                } else if (identifier.endsWith("formatted")) {
                    return ArmorPlus.formatNumber(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getHealth());
                } else if (identifier.endsWith("hearts")) {
                    return ArmorPlus.formatNumber(SetPlayerManager.getSetPlayer(player).getSet().getBasicData().getHealth() / 2) + ChatColor.RED + "❤";
                }
            } else {
                return fallback;
            }
        }
        if (identifier.endsWith("pieces_wearing")) {
            String[] parts = identifier.split("_");
            Set set;
            if ((set = SetManager.getSet(parts[0])) == null) {
                return "Invalid set";
            }
            return String.valueOf(SetPlayerManager.getPiecesWearing(set, player));
        }
        if (identifier.endsWith("pieces_total")) {
            String[] parts = identifier.split("_");
            Set set;
            if ((set = SetManager.getSet(parts[0])) == null) {
                return "Invalid set";
            }
            return String.valueOf(set.getSetPieces().size());
        }
        return "Error";
    }
}
