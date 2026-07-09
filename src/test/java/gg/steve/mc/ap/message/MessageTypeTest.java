package gg.steve.mc.ap.message;

import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.utils.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Characterization tests for MessageType.message() placeholder substitution.
 * Pins the replacement logic and ColorUtil integration.
 */
@ExtendWith(MockitoExtension.class)
class MessageTypeTest {

    @Mock private Player player;
    @Mock private CommandSender sender;
    @Mock private YamlConfiguration yamlConfig;

    @BeforeEach
    void setUp() {
        // We need to mock the static FileManager.get() call that ConfigManager.MESSAGES.get() uses
    }

    @Test
    void message_player_singleLine_replacesPlaceholders() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("MESSAGES")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("purchase"))
                    .thenReturn(Collections.singletonList("You purchased {piece} from {set-name}!"));

            MessageType.PURCHASE.message(player, "HELMET", "warrior");

            // ColorUtil.colorize translates & codes; with no & codes the string passes through
            verify(player).sendMessage(ColorUtil.colorize("You purchased HELMET from warrior!"));
        }
    }

    @Test
    void message_player_multipleLines_eachLineProcessed() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("MESSAGES")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("purchase"))
                    .thenReturn(Arrays.asList("Line 1: {piece}", "Line 2: {set-name}"));

            MessageType.PURCHASE.message(player, "BOOTS", "mage");

            verify(player).sendMessage(ColorUtil.colorize("Line 1: BOOTS"));
            verify(player).sendMessage(ColorUtil.colorize("Line 2: mage"));
        }
    }

    @Test
    void message_commandSender_replacesPlaceholders() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("MESSAGES")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("reload"))
                    .thenReturn(Collections.singletonList("Config reloaded!"));

            MessageType.RELOAD.message(sender);

            verify(sender).sendMessage(ColorUtil.colorize("Config reloaded!"));
        }
    }

    @Test
    void message_noPlaceholders_sendsRawColorizedLine() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("MESSAGES")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("help"))
                    .thenReturn(Collections.singletonList("&aHelp text"));

            MessageType.HELP.message(player);

            verify(player).sendMessage(ColorUtil.colorize("&aHelp text"));
        }
    }

    @Test
    void message_givePiece_fourPlaceholders() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("MESSAGES")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("give-piece-giver"))
                    .thenReturn(Collections.singletonList("Gave {player} {amount}x {piece} ({set-name})"));

            MessageType.GIVE_GIVER.message(player, "Steve", "HELMET", "warrior", "3");

            verify(player).sendMessage(ColorUtil.colorize("Gave Steve 3x HELMET (warrior)"));
        }
    }

    @Test
    void doMessage_static_sendsEachLineColorized() {
        MessageType.doMessage(player, Arrays.asList("&cRed line", "&bBlue line"));

        verify(player).sendMessage(ColorUtil.colorize("&cRed line"));
        verify(player).sendMessage(ColorUtil.colorize("&bBlue line"));
    }

    @Test
    void doMessage_emptyList_sendsNothing() {
        MessageType.doMessage(player, Collections.emptyList());

        verify(player, never()).sendMessage(anyString());
    }
}
