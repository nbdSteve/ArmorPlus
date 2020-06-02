package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import org.bukkit.configuration.ConfigurationSection;

public abstract class AbstractSetData {
    private SetDataType dataType;
    private ConfigurationSection section;
    private String entry;
    private Set set;

    public AbstractSetData(SetDataType dataType, ConfigurationSection section, String entry, Set set) {
        this.dataType = dataType;
        this.section = section;
        this.entry = entry;
        this.set = set;
    }

    public SetDataType getDataType() {
        return dataType;
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public String getEntry() {
        return entry;
    }

    public Set getSet() {
        return set;
    }
}
