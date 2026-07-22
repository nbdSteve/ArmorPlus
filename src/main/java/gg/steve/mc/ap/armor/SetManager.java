package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.utils.YamlFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SetManager {
    private static final ArmorSetRegistry<Set> registry = new ArmorSetRegistry<>();

    public static void loadSets() {
        registry.clear();
        for (String set : ConfigManager.CONFIG.get().getStringList("loaded-sets")) {
            YamlFileUtil fileUtil = new YamlFileUtil("sets" + File.separator + set + ".yml", ArmorPlus.get());
            registry.register(ArmorSetId.of(set), new Set(set, fileUtil));
        }
    }

    public static Map<String, Set> getSets() {
        Map<String, Set> sets = new HashMap<>();
        registry.getAll().forEach((id, set) -> sets.put(id.toString(), set));
        return sets;
    }

    public static Set getSet(String name) {
        if (name == null || name.isEmpty()) return null;
        return registry.get(ArmorSetId.of(name)).orElse(null);
    }
}
