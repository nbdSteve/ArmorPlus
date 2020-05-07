package gg.steve.mc.ap.utils;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.message.CommandDebug;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class GuiItemUtil {

    public static ItemStack createItem(ConfigurationSection section, String entry, Set set) {
        String material = section.getString(entry + ".item");
        ItemBuilderUtil builder = null;
        if (material.startsWith("hdb")) {
            String[] id = section.getString(entry + ".item").split("-");
            try {
                builder = new ItemBuilderUtil(new HeadDatabaseAPI().getItemHead(id[1]));
            } catch (NullPointerException e) {
                LogUtil.info("Tried to create hdn item but the required plugin 'HeadDatabase' was not found, default type set to stone.");
                material = "stone";
            }
        } else if (material.equalsIgnoreCase("skull_item")) {
            try {
                Material.valueOf(material.toUpperCase());
            } catch (Exception e) {
                material = "legacy_skull_item";
            }
            builder = new ItemBuilderUtil(material, section.getString(entry + ".data"));
        } else {
            builder = new ItemBuilderUtil(material, section.getString(entry + ".data"));
        }
        if (section.getString(entry + ".owner") != null) {
            SkullMeta meta = (SkullMeta) builder.getItemMeta();
            meta.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(section.getString(entry + ".owner"))).getName());
            builder.setItemMeta(meta);
        }
        if (section.getString(entry + ".color") != null) {
            if (material.startsWith("leather")) {
                String[] parts = section.getString(entry + ".color").split(":");
                if (parts.length != 3) {
                    LogUtil.warning("Error when trying to apply color to pieces from the " + set.getName() + " armor set, please check your configuration.");
                } else {
                    Color color = Color.fromRGB(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    LeatherArmorMeta meta = (LeatherArmorMeta) builder.getItemMeta();
                    meta.setColor(color);
                    builder.setItemMeta(meta);
                }
            }
        }
        builder.addName(section.getString(entry + ".name"));
        builder.addLore(section.getStringList(entry + ".lore"));
        builder.addEnchantments(section.getStringList(entry + ".enchantments"));
        builder.addItemFlags(section.getStringList(entry + ".item-flags"));
        if (set != null) {
            builder.addNBT(set.getName());
        }
        return builder.getItem();
    }

    public static void purchase(ConfigurationSection section, String entry, Player player, Set set) {
        if (PermissionNode.isPurchasePerms() && !PermissionNode.PURCHASE.hasPurchasePermission(player, set)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(player, PermissionNode.PURCHASE.get() + set.getName());
            player.closeInventory();
            return;
        }
        Piece piece;
        try {
            piece = Piece.valueOf(section.getString(entry + ".action").toUpperCase());
        } catch (Exception e) {
            LogUtil.warning("There is an error with your gui configuration for the " + set.getName() + " armor set, please review your configuration.");
            CommandDebug.GUI_CONFIGURATION_ERROR.message(player);
            player.closeInventory();
            return;
        }
        if (ArmorPlus.eco() == null) {
            // add a random uuid to the armor piece, it will make the item unstackable
            NBTItem nbtItem = new NBTItem(set.getPiece(piece));
            nbtItem.setString("armor+.uuid", String.valueOf(UUID.randomUUID()));
            player.getInventory().addItem(nbtItem.getItem());
        }
        if (ArmorPlus.eco().getBalance(player) >= section.getDouble(entry + ".cost")) {
            ArmorPlus.eco().withdrawPlayer(player, section.getDouble(entry + ".cost"));
            // add a random uuid to the armor piece, it will make the item unstackable
            NBTItem nbtItem = new NBTItem(set.getPiece(piece));
            nbtItem.setString("armor+.uuid", String.valueOf(UUID.randomUUID()));
            player.getInventory().addItem(nbtItem.getItem());
            MessageType.PURCHASE.message(player, piece.toString(), set.getName());
        } else {
            player.closeInventory();
            MessageType.INSUFFICIENT_FUNDS.message(player);
        }
    }
}
