package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.gui.AbstractGui;
import gg.steve.mc.ap.message.CommandDebug;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.ItemBuilderUtil;
import gg.steve.mc.ap.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetGui extends AbstractGui {
    private Set set;

    /**
     * Constructor the create a new Gui
     */
    public SetGui(ConfigurationSection section, Set set) {
        super(section, section.getString("type"), section.getInt("size"));
        this.set = set;
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (Exception e) {
                continue;
            }
            setItemInSlot(section.getInt(entry + ".slot"), createItem(section, entry), player -> {
                switch (section.getString(entry + ".action")) {
                    case "none":
                        break;
                    case "close":
                        player.closeInventory();
                        break;
                    default:
                        purchase(section, entry, player);
                        break;
                }
            });
        }
    }

    private ItemStack createItem(ConfigurationSection section, String entry) {
        ItemBuilderUtil builder = new ItemBuilderUtil(section.getString(entry + ".item"), section.getString(entry + ".data"));
        builder.addName(section.getString(entry + ".name"));
        builder.addLore(section.getStringList(entry + ".lore"));
        builder.addEnchantments(section.getStringList(entry + ".enchantments"));
        builder.addItemFlags(section.getStringList(entry + ".item-flags"));
        builder.addNBT(set.getName());
        return builder.getItem();
    }

    private void purchase(ConfigurationSection section, String entry, Player player) {
        Piece piece;
        try{
            piece = Piece.valueOf(section.getString(entry + ".action"));
        } catch (Exception e) {
            LogUtil.warning("There is an error with your gui configuration for the " + this.set.getName() + " armor set, please review your configuration.");
            CommandDebug.GUI_CONFIGURATION_ERROR.message(player);
            player.closeInventory();
            return;
        }
        if (ArmorPlus.eco() == null) {
            player.getInventory().addItem(set.getSetPieces().get(piece));
        }
        if (ArmorPlus.eco().getBalance(player) >= section.getDouble(entry + ".cost")) {
            ArmorPlus.eco().withdrawPlayer(player, section.getDouble(entry + ".cost"));
            player.getInventory().addItem(set.getSetPieces().get(piece));
            MessageType.PURCHASE.message(player, piece.toString(), this.set.getName());
        } else {
            player.closeInventory();
            MessageType.INSUFFICIENT_FUNDS.message(player);
        }
    }
}
