package gg.steve.mc.ap;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.model.player.PlayerArmorWearerRegistry;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.player.PlayerArmorSetService;

public class ArmorPlusModule extends AbstractModule {
    private final ArmorPlus plugin;

    public ArmorPlusModule(ArmorPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(ArmorPlus.class).toInstance(plugin);
        // Bind the pure model registries here rather than annotating them, so the domain layer stays DI-free.
        bind(new TypeLiteral<ArmorSetRegistry<Set>>() {}).in(Singleton.class);
        bind(PlayerArmorWearerRegistry.class).in(Singleton.class);
        bind(ArmorSetCatalog.class).in(Singleton.class);
        bind(PlayerArmorSetService.class).in(Singleton.class);
    }
}
