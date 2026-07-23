package gg.steve.mc.ap.armor;

import com.google.inject.Inject;
import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.utils.YamlFileUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class ArmorSetCatalog {
    private final ArmorSetRegistry<Set> registry;
    private final ArmorPlus plugin;

    @Inject
    public ArmorSetCatalog(ArmorSetRegistry<Set> registry, ArmorPlus plugin) {
        this.registry = registry;
        this.plugin = plugin;
    }

    public ArmorSetRegistry<Set> getRegistry() {
        return registry;
    }

    public void loadSets() {
        registry.clear();
        YamlConfiguration config = Objects.requireNonNull(ConfigManager.CONFIG.get(), "armor+.yml config is not loaded");
        for (String set : config.getStringList("loaded-sets")) {
            YamlFileUtil fileUtil = new YamlFileUtil("sets" + File.separator + set + ".yml", plugin);
            registry.register(ArmorSetId.of(set), new Set(set, fileUtil));
        }
    }

    public Map<ArmorSetId, Set> getSets() {
        return registry.getAll();
    }

    public Set getSet(String name) {
        if (name == null || name.isEmpty()) return null;
        return registry.get(ArmorSetId.of(name)).orElse(null);
    }
}
