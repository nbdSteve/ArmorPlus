package gg.steve.mc.ap.cmd;

import gg.steve.mc.ap.cmd.sub.GiveCmd;
import gg.steve.mc.ap.cmd.sub.GuiCmd;
import gg.steve.mc.ap.cmd.sub.HelpCmd;
import gg.steve.mc.ap.cmd.sub.ReloadCmd;
import gg.steve.mc.ap.message.CommandDebug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ApCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ap")) {
            if (args.length == 0) {
                GuiCmd.gui(sender);
                return true;
            }
        }
        switch (args[0].toLowerCase()) {
            case "h":
            case "help":
                HelpCmd.help(sender);
                break;
            case "r":
            case "reload":
                ReloadCmd.reload(sender);
                break;
            case "gui":
            case "m":
            case "menu":
                GuiCmd.gui(sender);
                break;
            case "g":
            case "give":
                GiveCmd.give(sender, args);
                break;
            default:
                CommandDebug.INVALID_COMMAND.message(sender);
                break;
        }
        return true;
    }
}
