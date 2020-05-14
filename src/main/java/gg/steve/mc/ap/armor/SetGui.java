package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.gui.AbstractGui;
import gg.steve.mc.ap.utils.GuiItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SetGui extends AbstractGui {
    private ConfigurationSection section;
    private Set set;

    /**
     * Constructor the create a new Gui
     */
    public SetGui(ConfigurationSection section, Set set) {
        super(section, section.getString("type"), section.getInt("size"));
        this.section = section;
        this.set = set;
        refresh();
    }

    public void refresh() {
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (Exception e) {
                continue;
            }
            setItemInSlot(section.getInt(entry + ".slot"), GuiItemUtil.createItem(section, entry, set), player -> {
                switch (section.getString(entry + ".action")) {
                    case "none":
                        break;
                    case "back":
                        ArmorPlus.getApGui().open(player);
                        break;
                    case "close":
                        player.closeInventory();
                        break;
                    default:
                        GuiItemUtil.purchase(section, entry, player, set);
                        break;
                }
            });
        }
    }
}