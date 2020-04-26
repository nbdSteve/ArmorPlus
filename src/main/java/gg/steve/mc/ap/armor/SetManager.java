package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.utils.YamlFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SetManager {
    private static Map<String, YamlFileUtil> setConfigs;
    private static Map<String, Set> sets;

    public static void loadSets() {
        setConfigs = new HashMap<>();
        sets = new HashMap<>();
        for (String set : ConfigManager.CONFIG.get().getStringList("loaded-sets")) {
            YamlFileUtil fileUtil = new YamlFileUtil("sets" + File.separator + set + ".yml", ArmorPlus.get());
            setConfigs.put(set, fileUtil);
            sets.put(set, new Set(set, fileUtil));
        }
    }

    public static Map<String, Set> getSets() {
        return sets;
    }

    public static Set getSet(String name) {
        return sets.get(name);
    }
}
