package gg.steve.mc.ap.cmd.sub;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.managers.ConfigManager;
import gg.steve.mc.ap.message.CommandDebug;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCmd {

    public static void reload(CommandSender sender) {
        if (!PermissionNode.RELOAD.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.RELOAD.get());
            return;
        }
        ConfigManager.reload();
        Bukkit.getPluginManager().disablePlugin(ArmorPlus.get());
        Bukkit.getPluginManager().enablePlugin(ArmorPlus.get());
        MessageType.RELOAD.message(sender);
    }
}
