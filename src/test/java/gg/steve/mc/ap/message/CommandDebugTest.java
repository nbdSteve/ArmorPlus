package gg.steve.mc.ap.message;

import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.utils.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Characterization tests for CommandDebug.message() placeholder substitution.
 * Mirrors MessageType but reads from ConfigManager.DEBUG.
 */
@ExtendWith(MockitoExtension.class)
class CommandDebugTest {

    @Mock private Player player;
    @Mock private CommandSender sender;
    @Mock private YamlConfiguration yamlConfig;

    @Test
    void message_player_replacesNodePlaceholder() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("DEBUG")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("insufficient-permission"))
                    .thenReturn(Collections.singletonList("Missing perm: {node}"));

            CommandDebug.INSUFFICIENT_PERMISSION.message(player, "armorplus.give");

            verify(player).sendMessage(ColorUtil.colorize("Missing perm: armorplus.give"));
        }
    }

    @Test
    void message_commandSender_replacesNodePlaceholder() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("DEBUG")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("insufficient-permission"))
                    .thenReturn(Collections.singletonList("Missing perm: {node}"));

            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, "armorplus.give");

            verify(sender).sendMessage(ColorUtil.colorize("Missing perm: armorplus.give"));
        }
    }

    @Test
    void message_noPlaceholders_sendsDirectly() {
        try (MockedStatic<FileManager> fm = mockStatic(FileManager.class)) {
            fm.when(() -> FileManager.get("DEBUG")).thenReturn(yamlConfig);
            when(yamlConfig.getStringList("invalid-amount"))
                    .thenReturn(Collections.singletonList("&cInvalid amount!"));

            CommandDebug.INVALID_AMOUNT.message(player);

            verify(player).sendMessage(ColorUtil.colorize("&cInvalid amount!"));
        }
    }
}
