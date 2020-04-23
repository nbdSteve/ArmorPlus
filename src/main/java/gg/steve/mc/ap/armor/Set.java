package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.armorequipevent.ArmorType;
import gg.steve.mc.ap.data.BasicSetData;
import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.utils.CommandUtil;
import gg.steve.mc.ap.utils.ItemBuilderUtil;
import gg.steve.mc.ap.utils.YamlFileUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Set {
    private String name;
    private YamlFileUtil fileUtil;
    private YamlConfiguration config;
    private List<SetData> data;
    private Map<Piece, ItemStack> setPieces;
    private SetGui gui;

    public Set(String name, YamlFileUtil fileUtil) {
        this.name = name;
        this.fileUtil = fileUtil;
        this.config = this.fileUtil.get();
        this.setPieces = new HashMap<>();
        this.data = new ArrayList<>();
        ConfigurationSection dataTypes = config.getConfigurationSection("set-data");
        for (String entry : dataTypes.getKeys(false)) {
            switch (dataTypes.getString(entry + ".type")) {
                case "basic":
                    this.data.add(new BasicSetData(dataTypes.getDouble(entry + ".damage-increase"), dataTypes.getDouble(entry + ".damage-decrease"), dataTypes.getDouble(entry + ".kb"), dataTypes.getDouble(entry + ".health")));
                    break;
                case "yijiki":
                    break;
                case "warp":
                    break;
                case "potion":
                    break;
            }
        }
        ConfigurationSection section = config.getConfigurationSection("set-pieces");
        for (String entry : section.getKeys(false)) {
            Piece piece = Piece.valueOf(entry.toUpperCase());
            ItemBuilderUtil builder = new ItemBuilderUtil(section.getString(entry + ".item"), section.getString(entry + ".data"));
            builder.addName(section.getString(entry + ".name"));
            builder.addLore(section.getStringList(entry + ".lore"));
            builder.addEnchantments(section.getStringList(entry + ".enchantments"));
            builder.addItemFlags(section.getStringList(entry + ".item-flags"));
            builder.addNBT(name);
            setPieces.put(piece, builder.getItem());
        }
        this.gui = new SetGui(config.getConfigurationSection("gui"), this);
    }

    public boolean isWearingSet(Player player, ArmorType type, ItemStack changedItem) {
        if (!verifyPiece(changedItem)) return false;
        for (Map.Entry item : setPieces.entrySet()) {
            NBTItem nbtItem = null;
            switch (item.getKey().toString()) {
                case "HELMET":
                    if (type.toString().equalsIgnoreCase("HELMET")) continue;
                    if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType().equals(Material.AIR))
                        return false;
                    nbtItem = new NBTItem(player.getInventory().getHelmet());
                    break;
                case "CHESTPLATE":
                    if (type.toString().equalsIgnoreCase("CHESTPLATE")) continue;
                    if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType().equals(Material.AIR))
                        return false;
                    nbtItem = new NBTItem(player.getInventory().getChestplate());
                    break;
                case "LEGGINGS":
                    if (type.toString().equalsIgnoreCase("LEGGINGS")) continue;
                    if (player.getInventory().getLeggings() == null || player.getInventory().getLeggings().getType().equals(Material.AIR))
                        return false;
                    nbtItem = new NBTItem(player.getInventory().getLeggings());
                    break;
                case "BOOTS":
                    if (type.toString().equalsIgnoreCase("HAND")) continue;
                    if (player.getInventory().getBoots() == null || player.getInventory().getBoots().getType().equals(Material.AIR))
                        return false;
                    nbtItem = new NBTItem(player.getInventory().getBoots());
                    break;
                case "HAND":
                    if (player.getInventory().getItemInHand() == null || player.getInventory().getItemInHand().getType().equals(Material.AIR))
                        return false;
                    nbtItem = new NBTItem(player.getInventory().getItemInHand());
                    break;
            }
            if (nbtItem.getString("armor+.set") == null) return false;
            if (!nbtItem.getString("armor+.set").equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    public boolean verifyPiece(ItemStack oldItem) {
        NBTItem nbtItem = new NBTItem(oldItem);
        if (nbtItem.getString("armor+.set") == null) return false;
        return nbtItem.getString("armor+.set").equalsIgnoreCase(this.name);
    }

    public void notifyPlayer(String method, Player player) {
        if (config.getBoolean(method + ".message.enabled")) {
            MessageType.doMessage(player, config.getStringList(method + ".message.text"));
        }
        if (config.getBoolean(method + ".sound.enabled")) {
            player.playSound(player.getLocation(),
                    Sound.valueOf(config.getString(method + ".sound.type").toUpperCase()),
                    config.getInt(method + ".sound.volume"),
                    config.getInt(method + ".sound.pitch"));
        }
        if (config.getBoolean(method + ".commands.enabled")) {
            CommandUtil.execute(config.getStringList(method + ".commands.list"), player);
        }
    }

    public void apply(Player player) {
        notifyPlayer("apply", player);
        for (SetData setData : this.data) {
            setData.onApply(player);
        }
    }

    public void remove(Player player) {
        notifyPlayer("remove", player);
        for (SetData setData : this.data) {
            setData.onRemoval(player);
        }
    }

    public void onHit(EntityDamageByEntityEvent event) {
        for (SetData setData : this.data) {
            setData.onHit(event);
        }
    }

    public void onDamage(EntityDamageByEntityEvent event) {
        for (SetData setData : this.data) {
            setData.onDamage(event);
        }
    }

    public void openGui(Player player) {
        this.gui.open(player);
    }

    // <-- Getters and Setters from this point on -->
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YamlFileUtil getFileUtil() {
        return fileUtil;
    }

    public void setFileUtil(YamlFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

    public List<SetData> getData() {
        return data;
    }

    public void setData(List<SetData> data) {
        this.data = data;
    }

    public Map<Piece, ItemStack> getSetPieces() {
        return setPieces;
    }

    public void setSetPieces(Map<Piece, ItemStack> setPieces) {
        this.setPieces = setPieces;
    }

    public SetGui getGui() {
        return gui;
    }

    public void setGui(SetGui gui) {
        this.gui = gui;
    }
}