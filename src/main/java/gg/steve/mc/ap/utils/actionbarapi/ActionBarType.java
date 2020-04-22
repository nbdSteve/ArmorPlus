package gg.steve.mc.ap.utils.actionbarapi;

import gg.steve.mc.ap.managers.FileManager;
import gg.steve.mc.ap.utils.ColorUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum ActionBarType {
    COOLDOWN("cooldown", "{seconds-remaining}"),
    DELAY_COUNTDOWN("delay-countdown", "{seconds-remaining}"),
    TELEPORT_FAILED("teleport-failed"),
    DELAY("delay", "{seconds-remaining}");

    private String path;
    private List<String> placeholders;

    ActionBarType(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
    }

    public void send(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        String message = FileManager.get("action-bars").getString(this.path);
        for (int i = 0; i < this.placeholders.size(); i++) {
            message = message.replace(this.placeholders.get(i), data.get(i));
        }
        ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(message));
    }
}
