package gg.steve.mc.ap.armor;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.utils.YamlFileUtil;

import java.io.File;
import java.util.Map;

public class SetManager {
    private static final SetManager INSTANCE = new SetManager();

    private final ArmorSetRegistry<Set> registry = new ArmorSetRegistry<>();

    /** The shared instance the static entry points below delegate to, so the registry state is held by an instance rather than a static map. */
    public static SetManager get() {
        return INSTANCE;
    }

    /** The registry this instance owns, for callers that can take the instance directly. */
    public ArmorSetRegistry<Set> getRegistry() {
        return registry;
    }

    public static void loadSets() {
        ArmorSetRegistry<Set> registry = INSTANCE.registry;
        registry.clear();
        for (String set : ConfigManager.CONFIG.get().getStringList("loaded-sets")) {
            YamlFileUtil fileUtil = new YamlFileUtil("sets" + File.separator + set + ".yml", ArmorPlus.get());
            registry.register(ArmorSetId.of(set), new Set(set, fileUtil));
        }
    }

    public static Map<ArmorSetId, Set> getSets() {
        return INSTANCE.registry.getAll();
    }

    public static Set getSet(String name) {
        if (name == null || name.isEmpty()) return null;
        return INSTANCE.registry.get(ArmorSetId.of(name)).orElse(null);
    }
}
