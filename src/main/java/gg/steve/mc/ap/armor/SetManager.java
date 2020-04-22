package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
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
        File setFolder = new File("plugins" + File.separator + "ArmorPlus" + File.separator + "sets");
        for (File set : setFolder.listFiles()) {
            String name = set.getName().split(".yml")[0];
            YamlFileUtil fileUtil = new YamlFileUtil("sets" + File.separator + set.getName(), ArmorPlus.get());
            setConfigs.put(name, fileUtil);
            sets.put(name, new Set(name, fileUtil));
        }
    }

    public static Set getSet(String name) {
        return sets.get(name);
    }
}
