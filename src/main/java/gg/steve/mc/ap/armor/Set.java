package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.armorequipevent.ArmorType;
import gg.steve.mc.ap.data.BasicSetData;
import gg.steve.mc.ap.data.SetData;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.nbt.NBTItem;
import gg.steve.mc.ap.utils.CommandUtil;
import gg.steve.mc.ap.utils.ItemBuilderUtil;
import gg.steve.mc.ap.utils.YamlFileUtil;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Set {
    private String name;
    private YamlFileUtil fileUtil;
    private YamlConfiguration config;
    private List<SetData> data;
    private Map<Piece, ItemStack> setPieces;

    public Set(String name, YamlFileUtil fileUtil) {
        this.name = name;
        this.fileUtil = fileUtil;
        this.config = this.fileUtil.get();
        this.setPieces = new HashMap<>();
        ConfigurationSection dataTypes = config.getConfigurationSection("set-data");
        for (String entry : dataTypes.getKeys(false)) {
            switch (dataTypes.getString(entry + ".type")) {
                case "basic":
                    this.data.add(new BasicSetData(config.getDouble("modifiers.damage-increase"), config.getDouble("modifiers.damage-decrease"), config.getDouble("modifiers.kb")));
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
    }

    public boolean hasFullSet(Player player) {
        for (Map.Entry item : setPieces.entrySet()) {
            NBTItem nbtItem = null;
            switch (item.getKey().toString()) {
                case "HELMET":
                    nbtItem = new NBTItem(player.getInventory().getHelmet());
                    break;
                case "CHESTPLATE":
                    nbtItem = new NBTItem(player.getInventory().getChestplate());
                    break;
                case "LEGGINGS":
                    nbtItem = new NBTItem(player.getInventory().getLeggings());
                    break;
                case "BOOTS":
                    nbtItem = new NBTItem(player.getInventory().getBoots());
                    break;
                case "HAND":
                    nbtItem = new NBTItem(player.getInventory().getItemInHand());
                    break;
            }
            if (!nbtItem.getString("armor+.set").equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    public boolean hadFullSet(Player player, ArmorType type, ItemStack oldItem) {
        if (!verifyOldPiece(oldItem)) return false;
        for (Map.Entry item : setPieces.entrySet()) {
            NBTItem nbtItem = null;
            switch (item.getKey().toString()) {
                case "HELMET":
                    if (type.toString().equalsIgnoreCase("HELMET")) continue;
                    nbtItem = new NBTItem(player.getInventory().getHelmet());
                    break;
                case "CHESTPLATE":
                    if (type.toString().equalsIgnoreCase("CHESTPLATE")) continue;
                    nbtItem = new NBTItem(player.getInventory().getChestplate());
                    break;
                case "LEGGINGS":
                    if (type.toString().equalsIgnoreCase("LEGGINGS")) continue;
                    nbtItem = new NBTItem(player.getInventory().getLeggings());
                    break;
                case "BOOTS":
                    if (type.toString().equalsIgnoreCase("HAND")) continue;
                    nbtItem = new NBTItem(player.getInventory().getBoots());
                    break;
                case "HAND":
                    nbtItem = new NBTItem(player.getInventory().getItemInHand());
                    break;
            }
            if (nbtItem.getString("armor+.set") == null) return false;
            if (!nbtItem.getString("armor+.set").equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    public boolean verifyOldPiece(ItemStack oldItem) {
        NBTItem nbtItem = new NBTItem(oldItem);
        if (nbtItem.getString("armor+.set") == null) return false;
        return nbtItem.getString("armor+.set").equalsIgnoreCase(this.name);
    }

    public void apply(Player player) {
        if (config.getBoolean("apply.message.enabled")) {
            MessageType.doMessage(player, config.getStringList("apply.message.text"));
        }
        if (config.getBoolean("apply.sound.enabled")) {
            player.playSound(player.getLocation(),
                    Sound.valueOf(config.getString("apply.sound.type").toUpperCase()),
                    config.getInt("apply.sound.volume"),
                    config.getInt("apply.sound.pitch"));
        }
        if (config.getBoolean("apply.commands.enabled")) {
            CommandUtil.execute(config.getStringList("apply.commands.list"), player);
        }
    }

    public void remove(Player player) {
        if (config.getBoolean("remove.message.enabled")) {
            MessageType.doMessage(player, config.getStringList("remove.message.text"));
        }
        if (config.getBoolean("remove.sound.enabled")) {
            player.playSound(player.getLocation(),
                    Sound.valueOf(config.getString("remove.sound.type").toUpperCase()),
                    config.getInt("remove.sound.volume"),
                    config.getInt("remove.sound.pitch"));
        }
        if (config.getBoolean("remove.commands.enabled")) {
            CommandUtil.execute(config.getStringList("remove.commands.list"), player);
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
}