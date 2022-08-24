package gg.steve.mc.ap.cmd;

import gg.steve.mc.ap.cmd.sub.GiveCmd;
import gg.steve.mc.ap.cmd.sub.GuiCmd;
import gg.steve.mc.ap.cmd.sub.HelpCmd;
import gg.steve.mc.ap.cmd.sub.ReloadCmd;
import gg.steve.mc.ap.message.CommandDebug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class ApCmd implements TabExecutor {

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tab = new ArrayList<>();
        if (args.length == 1) {
            tab.add("give");
            tab.add("gui");
            tab.add("help");
            tab.add("reload");
        }
        if (args.length >= 2) {
            tab.add("");
        }
        return null;
    }
}
