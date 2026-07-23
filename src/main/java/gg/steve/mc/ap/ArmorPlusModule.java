package gg.steve.mc.ap;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.model.player.PlayerArmorWearerRegistry;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.player.PlayerArmorSetService;

/**
 * Guice bindings for the plugin's shared collaborators.
 * <p>
 * The pure {@code model} registries carry no framework annotations, so they are bound here
 * (Guice instantiates them via their no-arg constructors) rather than annotated in place -
 * this keeps the domain layer free of any dependency-injection imports. The adapter-layer
 * catalog and player service are constructor-injected singletons; a single instance of each
 * is shared across every listener, command, and expansion the injector wires.
 */
public class ArmorPlusModule extends AbstractModule {
    private final ArmorPlus plugin;

    public ArmorPlusModule(ArmorPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(ArmorPlus.class).toInstance(plugin);
        bind(new TypeLiteral<ArmorSetRegistry<Set>>() {}).in(Singleton.class);
        bind(PlayerArmorWearerRegistry.class).in(Singleton.class);
        bind(ArmorSetCatalog.class).in(Singleton.class);
        bind(PlayerArmorSetService.class).in(Singleton.class);
    }
}
