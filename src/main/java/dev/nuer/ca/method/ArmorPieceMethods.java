package dev.nuer.ca.method;

import dev.nuer.ca.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class handling methods to set metadata for an armor piece
 */
public class ArmorPieceMethods {

    /**
     * Sets the displayname of an armor piece
     *
     * @param filePath       the display name path
     * @param armorPieceMeta the itemmeta of the piece
     * @param lcf            LoadCarmorFiles instance
     */
    public static void setDisplayName(String filePath, ItemMeta armorPieceMeta, LoadCarmorFiles lcf) {
        armorPieceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                lcf.getArmor().getString(filePath)));
    }

    /**
     * Adds the specified lines to the item lore
     *
     * @param filePath the lines of lore to add
     * @param lore     the existing lore
     * @param lcf      LoadCarmorFiles instance
     */
    public static void addLore(String filePath, List<String> lore, LoadCarmorFiles lcf) {
        for (String line : lcf.getArmor().getStringList(filePath)) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    /**
     * Adds the enchantments to an armor piece
     *
     * @param filePath       the enchantments to add from the armor.yml
     * @param armorPieceMeta the itemmeta of the piece
     * @param lcf            LoadCarmorFiles instance
     */
    public static void addEnchantments(String filePath, ItemMeta armorPieceMeta, LoadCarmorFiles lcf) {
        for (String ench : lcf.getArmor().getStringList(filePath)) {
            String[] parts = ench.split(":");
            armorPieceMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()),
                    Integer.parseInt(parts[1]),
                    true);
        }
    }
}