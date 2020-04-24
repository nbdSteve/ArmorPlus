package gg.steve.mc.ap.permission;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.managers.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum PermissionNode {
    PURCHASE("purchase.node"),
    GUI("command.gui"),
    RELOAD("command.reload"),
    GIVE("command.give"),
    HELP("command.help");

    private String path;

    PermissionNode(String path) {
        this.path = path;
    }

    public String get() {
        return ConfigManager.PERMISSIONS.get().getString(this.path);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(get());
    }

    public boolean hasPurchasePermission(Player player, Set set) {
        return  player.hasPermission(get() + set.getName());
    }

    public static boolean isPurchasePerms() {
        return ConfigManager.PERMISSIONS.get().getBoolean("purchase.enabled");
    }
}
