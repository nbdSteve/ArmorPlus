package gg.steve.mc.ap.gui;

import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.utils.GuiItemUtil;
import org.bukkit.configuration.ConfigurationSection;

public class ApGui extends AbstractGui {
    private ConfigurationSection section;

    /**
     * Constructor the create a new Gui
     *
     * @param section
     */
    public ApGui(ConfigurationSection section) {
        super(section, section.getString("type"), section.getInt("size"));
        this.section = section;
        refresh();
    }

    public void refresh() {
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (Exception e) {
                continue;
            }
            setItemInSlot(section.getInt(entry + ".slot"), GuiItemUtil.createItem(section, entry, null), player -> {
                switch (section.getString(entry + ".action")) {
                    case "none":
                        break;
                    case "close":
                        player.closeInventory();
                        break;
                    default:
                        SetManager.getSet(section.getString(entry + ".action")).openGui(player);
                        break;
                }
            });
        }
    }
}