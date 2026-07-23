package gg.steve.mc.ap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.player.PlayerArmorWearerRegistry;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import gg.steve.mc.ap.player.PlayerArmorSetService;
import gg.steve.mc.ap.player.SetPlayer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercises the real Guice composition root that {@link ArmorPlus#onEnable()} builds.
 * <p>
 * This is the wiring the whole change turns on: the static {@code SetManager}/{@code SetPlayerManager}
 * singletons were deleted and replaced by injected {@link ArmorSetCatalog} / {@link PlayerArmorSetService}
 * instances that share the pure {@link ArmorSetRegistry} / {@link PlayerArmorWearerRegistry}. Every other
 * test constructs those collaborators by hand with {@code new}; this one drives the actual
 * {@link ArmorPlusModule} through a live {@link Injector} - the same objects {@code onEnable} resolves
 * and threads to the listeners, commands and expansion - so the DI contract itself is verified, not
 * just the classes in isolation.
 * <p>
 * The end-to-end check proves the property the old statics used to give for free: state written through
 * one injector-resolved collaborator (register a set on the catalog, mark a player wearing it on the
 * service) is observable through another, because Guice hands out one shared instance of each. A
 * transcript of what the injector produced is written to the evidence directory.
 */
@ExtendWith(MockitoExtension.class)
class ArmorPlusModuleTest {

    private static final Path EVIDENCE = Path.of(
            System.getProperty("java.io.tmpdir"),
            "no-mistakes-evidence", "01KY78FKDPAVYJW7ME7YQYCTNA",
            "guice-composition-root-transcript.txt");

    private static final Key<ArmorSetRegistry<Set>> SET_REGISTRY_KEY =
            Key.get(new TypeLiteral<ArmorSetRegistry<Set>>() {});

    @Mock private ArmorPlus plugin;
    @Mock private Set dragonSet;
    @Mock private Player player;

    @Test
    void injectorWiresSharedSingletonsAndTheyRoundTripState() {
        List<String> transcript = new ArrayList<>();

        // Composition root, exactly as ArmorPlus.onEnable builds it.
        Injector injector = Guice.createInjector(new ArmorPlusModule(plugin));

        // 1. Both adapters resolve as singletons: repeat lookups hand back the same object.
        ArmorSetCatalog catalog = injector.getInstance(ArmorSetCatalog.class);
        PlayerArmorSetService service = injector.getInstance(PlayerArmorSetService.class);
        assertSame(catalog, injector.getInstance(ArmorSetCatalog.class),
                "catalog must be a shared singleton (replacing the old static SetManager)");
        assertSame(service, injector.getInstance(PlayerArmorSetService.class),
                "player service must be a shared singleton (replacing the old static SetPlayerManager)");
        transcript.add("injector.getInstance(ArmorSetCatalog)        -> singleton " + (catalog != null));
        transcript.add("injector.getInstance(PlayerArmorSetService)  -> singleton " + (service != null));

        // 2. The pure registries are the same instances the adapters were constructor-injected with:
        //    the catalog holds the injector's ArmorSetRegistry singleton, proving the graph is threaded.
        ArmorSetRegistry<Set> sharedRegistry = injector.getInstance(SET_REGISTRY_KEY);
        assertSame(sharedRegistry, catalog.getRegistry(),
                "the catalog must own the singleton ArmorSetRegistry the injector binds");
        assertSame(injector.getInstance(PlayerArmorWearerRegistry.class),
                injector.getInstance(PlayerArmorWearerRegistry.class),
                "wearer registry must be a shared singleton too");
        transcript.add("catalog.getRegistry() == injected ArmorSetRegistry singleton -> "
                + (sharedRegistry == catalog.getRegistry()));

        // 3. End-to-end: write through the catalog, read back through the service.
        //    getSetPlayer resolves the worn set name back through the SAME catalog the service was
        //    injected with, so this only works if Guice threaded one shared catalog into the service.
        UUID id = UUID.fromString("00000000-0000-0000-0000-0000000000aa");
        org.mockito.Mockito.when(player.getUniqueId()).thenReturn(id);

        catalog.getRegistry().register(ArmorSetId.of("dragon"), dragonSet);
        service.addSetPlayer(player, "dragon");

        assertTrue(service.isWearingSet(player), "service must see the player it just recorded");
        SetPlayer resolved = service.getSetPlayer(player);
        assertNotNull(resolved, "getSetPlayer must reconstruct the wearer");
        assertSame(dragonSet, resolved.getSet(),
                "the set resolved through the service must be the one registered on the shared catalog");
        transcript.add("catalog.register(dragon); service.addSetPlayer(player, \"dragon\")");
        transcript.add("service.isWearingSet(player)                 -> " + service.isWearingSet(player));
        transcript.add("service.getSetPlayer(player).getSet()        -> same instance registered on catalog: "
                + (resolved.getSet() == dragonSet));

        writeTranscript(transcript);
    }

    private void writeTranscript(List<String> body) {
        List<String> lines = new ArrayList<>();
        lines.add("=== Guice composition root wires shared singletons over the pure registries ===");
        lines.add("(built from the real ArmorPlusModule via a live Injector - the same wiring ArmorPlus.onEnable uses)");
        lines.addAll(body);
        try {
            Files.createDirectories(EVIDENCE.getParent());
            Files.write(EVIDENCE, (String.join(System.lineSeparator(), lines) + System.lineSeparator())
                    .getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
