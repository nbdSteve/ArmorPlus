package gg.steve.mc.ap.gui;

import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.utils.GuiItemUtil;
import org.bukkit.configuration.ConfigurationSection;

public class ApGui extends AbstractGui {
    private ConfigurationSection section;
    private final ArmorSetCatalog catalog;

    /**
     * Constructor the create a new Gui
     *
     * @param section
     * @param catalog the armor-set catalog used to resolve the set a slot action opens
     */
    public ApGui(ConfigurationSection section, ArmorSetCatalog catalog) {
        super(section, section.getString("type"), section.getInt("size"));
        this.section = section;
        this.catalog = catalog;
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
                        catalog.getSet(section.getString(entry + ".action")).openGui(player);
                        break;
                }
            });
        }
    }
}