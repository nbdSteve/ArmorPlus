package gg.steve.mc.ap.cmd.sub;

import gg.steve.mc.ap.ArmorPlus;
import gg.steve.mc.ap.message.CommandDebug;
import gg.steve.mc.ap.permission.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCmd {

    public static void gui(CommandSender sender) {
        if (!(sender instanceof Player)) {
            CommandDebug.ONLY_PLAYERS_ACCESSIBLE.message(sender);
            return;
        }
        if (!PermissionNode.GUI.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.GUI.get());
            return;
        }
        ArmorPlus.getApGui().open((Player) sender);
    }
}