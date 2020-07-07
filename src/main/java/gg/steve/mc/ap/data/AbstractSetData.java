package gg.steve.mc.ap.data;

import gg.steve.mc.ap.armor.Set;
import org.bukkit.configuration.ConfigurationSection;

public abstract class AbstractSetData {
    protected SetDataType dataType;
    protected ConfigurationSection section;
    protected String entry;
    protected Set set;

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

    public void setDataType(SetDataType dataType) {
        this.dataType = dataType;
    }

    public void setSection(ConfigurationSection section) {
        this.section = section;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
