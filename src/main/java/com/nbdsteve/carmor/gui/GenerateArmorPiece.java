package com.nbdsteve.carmor.gui;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

class GenerateArmorPiece {

    static void generateHelmet(String setNumber, LoadCarmorFiles lcf, Inventory setGui) {
        String[] helmParts = lcf.getArmorGui().getString(setNumber + ".helmet").split(":");
        ItemStack helm = new ItemStack(Material.valueOf(helmParts[0].toUpperCase()));
        ItemMeta helmMeta = helm.getItemMeta();
        List<String> helmLore = new ArrayList<>();
        helmMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                lcf.getArmor().getString(setNumber + ".helmet.name")));
        for (String lore : lcf.getArmor().getStringList(setNumber + ".helmet.lore")) {
            helmLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        for (String lore : lcf.getArmorGui().getStringList(setNumber + ".gui-lore")) {
            helmLore.add(ChatColor.translateAlternateColorCodes('&', lore).replace("%Price", helmParts[2]));
        }
        for (String ench : lcf.getArmor().getStringList(setNumber + ".helmet.enchantments")) {
            String[] parts = ench.split(":");
            helmMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()), Integer.parseInt(parts[1]), true);
        }
        helmMeta.setLore(helmLore);
        helm.setItemMeta(helmMeta);
        setGui.setItem(Integer.parseInt(helmParts[1]), helm);
    }

    static void generateChestplate(String setNumber, LoadCarmorFiles lcf, Inventory setGui) {
        String[] chestplateParts = lcf.getArmorGui().getString(setNumber + ".chestplate").split(":");
        ItemStack chestplate = new ItemStack(Material.valueOf(chestplateParts[0].toUpperCase()));
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        List<String> chestplateLore = new ArrayList<>();
        chestplateMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                lcf.getArmor().getString(setNumber + ".chestplate.name")));
        for (String lore : lcf.getArmor().getStringList(setNumber + ".chestplate.lore")) {
            chestplateLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        for (String lore : lcf.getArmorGui().getStringList(setNumber + ".gui-lore")) {
            chestplateLore.add(ChatColor.translateAlternateColorCodes('&', lore).replace("%Price", chestplateParts[2]));
        }
        for (String ench : lcf.getArmor().getStringList(setNumber + ".chestplate.enchantments")) {
            String[] parts = ench.split(":");
            chestplateMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()), Integer.parseInt(parts[1]), true);
        }
        chestplateMeta.setLore(chestplateLore);
        chestplate.setItemMeta(chestplateMeta);
        setGui.setItem(Integer.parseInt(chestplateParts[1]), chestplate);
    }

    static void generateLeggings(String setNumber, LoadCarmorFiles lcf, Inventory setGui) {
        String[] leggingsParts = lcf.getArmorGui().getString(setNumber + ".leggings").split(":");
        ItemStack leggings = new ItemStack(Material.valueOf(leggingsParts[0].toUpperCase()));
        ItemMeta leggingsMeta = leggings.getItemMeta();
        List<String> leggingsLore = new ArrayList<>();
        leggingsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                lcf.getArmor().getString(setNumber + ".leggings.name")));
        for (String lore : lcf.getArmor().getStringList(setNumber + ".leggings.lore")) {
            leggingsLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        for (String lore : lcf.getArmorGui().getStringList(setNumber + ".gui-lore")) {
            leggingsLore.add(ChatColor.translateAlternateColorCodes('&', lore).replace("%Price", leggingsParts[2]));
        }
        for (String ench : lcf.getArmor().getStringList(setNumber + ".leggings.enchantments")) {
            String[] parts = ench.split(":");
            leggingsMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()), Integer.parseInt(parts[1]), true);
        }
        leggingsMeta.setLore(leggingsLore);
        leggings.setItemMeta(leggingsMeta);
        setGui.setItem(Integer.parseInt(leggingsParts[1]), leggings);
    }

    static void generateBoots(String setNumber, LoadCarmorFiles lcf, Inventory setGui) {
        String[] bootsParts = lcf.getArmorGui().getString(setNumber + ".boots").split(":");
        ItemStack boots = new ItemStack(Material.valueOf(bootsParts[0].toUpperCase()));
        ItemMeta bootsMeta = boots.getItemMeta();
        List<String> bootsLore = new ArrayList<>();
        bootsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                lcf.getArmor().getString(setNumber + ".boots.name")));
        for (String lore : lcf.getArmor().getStringList(setNumber + ".boots.lore")) {
            bootsLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        for (String lore : lcf.getArmorGui().getStringList(setNumber + ".gui-lore")) {
            bootsLore.add(ChatColor.translateAlternateColorCodes('&', lore).replace("%Price", bootsParts[2]));
        }
        for (String ench : lcf.getArmor().getStringList(setNumber + ".boots.enchantments")) {
            String[] parts = ench.split(":");
            bootsMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()), Integer.parseInt(parts[1]), true);
        }
        bootsMeta.setLore(bootsLore);
        boots.setItemMeta(bootsMeta);
        setGui.setItem(Integer.parseInt(bootsParts[1]), boots);
    }
}
