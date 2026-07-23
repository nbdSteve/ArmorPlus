package gg.steve.mc.ap.armor;

import com.google.inject.Inject;
import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.utils.YamlFileUtil;

import java.io.File;
import java.util.Map;

/**
 * The plugin's live catalog of armor sets, parsed from configuration and keyed by {@link ArmorSetId}.
 * <p>
 * State lives in an injected {@link ArmorSetRegistry}; this class is the adapter that populates the
 * registry from Bukkit-backed config files and reads it back for the platform layer. It is a shared
 * singleton wired by Guice and passed to its callers as an injected instance, so there is no static
 * map or global entry point to reset between reloads.
 */
public class ArmorSetCatalog {
    private final ArmorSetRegistry<Set> registry;
    private final ArmorPlus plugin;

    @Inject
    public ArmorSetCatalog(ArmorSetRegistry<Set> registry, ArmorPlus plugin) {
        this.registry = registry;
        this.plugin = plugin;
    }

    /** The registry this catalog owns, for callers that read the raw id-to-set view. */
    public ArmorSetRegistry<Set> getRegistry() {
        return registry;
    }

    public void loadSets() {
        registry.clear();
        for (String set : ConfigManager.CONFIG.get().getStringList("loaded-sets")) {
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
