package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.armor.ArmorSetCatalog;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.set.ArmorSetRegistry;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerCommandListenerTest {

    private static final Path EVIDENCE = Path.of(
            System.getProperty("java.io.tmpdir"),
            "no-mistakes-evidence", "01KY69XZQMQHZWP62V198YSZ3P",
            "player-command-intercept-transcript.txt");

    @Mock private Player player;
    @Mock private Set dragonSet;
    @Mock private Set knightSet;
    @Mock private YamlConfiguration permissionsConfig;
    @Mock private ArmorPlus plugin;

    private ArmorSetCatalog catalog;
    private PlayerCommandListener listener;
    private final List<String> transcript = new ArrayList<>();

    @BeforeEach
    void setUp() {
        catalog = new ArmorSetCatalog(new ArmorSetRegistry<>(), plugin);
        catalog.getRegistry().register(ArmorSetId.of("dragon"), dragonSet);
        catalog.getRegistry().register(ArmorSetId.of("knight"), knightSet);
        listener = new PlayerCommandListener(catalog);
    }

    private PlayerCommandPreprocessEvent type(String typed) {
        // explicit recipient set avoids the internal getServer().getOnlinePlayers() the two-arg form calls
        PlayerCommandPreprocessEvent event =
                new PlayerCommandPreprocessEvent(player, typed, Collections.emptySet());
        listener.onCmd(event);
        transcript.add(String.format("player types %-22s -> intercepted=%s", "\"" + typed + "\"", event.isCancelled()));
        return event;
    }

    private void writeTranscript(String title) {
        List<String> lines = new ArrayList<>();
        lines.add("=== " + title + " ===");
        lines.add("Registry keys (ArmorSetId): " + new ArrayList<>(catalog.getSets().keySet()));
        lines.addAll(transcript);
        try {
            Files.createDirectories(EVIDENCE.getParent());
            Files.write(EVIDENCE, (String.join(System.lineSeparator(), lines) + System.lineSeparator())
                    .getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void typingSetName_intercepted_opensThatSetsGui() {
        when(player.hasPermission(anyString())).thenReturn(true);
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("PERMISSIONS")).thenReturn(permissionsConfig);
            when(permissionsConfig.getString("command.gui")).thenReturn("armorplus.command.gui");

            PlayerCommandPreprocessEvent event = type("/dragon");

            assertTrue(event.isCancelled(), "typing a known set name must be intercepted");
            verify(dragonSet).openGui(player);
            verify(knightSet, never()).openGui(any());
            writeTranscript("Player types a set command -> that set's GUI opens");
        }
    }

    @Test
    void typingSetName_isCaseInsensitive() {
        when(player.hasPermission(anyString())).thenReturn(true);
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("PERMISSIONS")).thenReturn(permissionsConfig);
            when(permissionsConfig.getString("command.gui")).thenReturn("armorplus.command.gui");

            PlayerCommandPreprocessEvent event = type("/DRAGON");

            assertTrue(event.isCancelled(), "matching is case-insensitive, as before");
            verify(dragonSet).openGui(player);
        }
    }

    @Test
    void typingUnknownCommand_passesThroughUnintercepted() {
        PlayerCommandPreprocessEvent event = type("/spawn");

        assertFalse(event.isCancelled(), "commands that are not set names must not be intercepted");
        verify(dragonSet, never()).openGui(any());
        verify(knightSet, never()).openGui(any());
    }

    @Test
    void eachSetNameRoutesToItsOwnSet() {
        when(player.hasPermission(anyString())).thenReturn(true);
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("PERMISSIONS")).thenReturn(permissionsConfig);
            when(permissionsConfig.getString("command.gui")).thenReturn("armorplus.command.gui");

            type("/dragon");
            type("/knight");
            type("/spawn");

            verify(dragonSet).openGui(player);
            verify(knightSet).openGui(player);
            writeTranscript("Each set command routes to its own set; unknown command passes through");
        }
    }
}
