package gg.steve.mc.ap.cmd.sub;

import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.message.CommandDebug;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCmd {

    public static void give(CommandSender sender, String[] args) {
        // /ap g phantom nbdsteve helmet 1
        if (args.length < 4) {
            CommandDebug.INVALID_NUMBER_OF_ARGUMENTS.message(sender);
            return;
        }
        if (!PermissionNode.GIVE.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.GIVE.get());
            return;
        }
        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            CommandDebug.TARGET_NOT_ONLINE.message(sender);
            return;
        }
        Set set;
        if (SetManager.getSet(args[1]) == null) {
            CommandDebug.INVALID_SET.message(sender);
            return;
        } else {
            set = SetManager.getSet(args[1]);
        }
        Piece piece = null;
        boolean all = false;
        if (!args[3].equalsIgnoreCase("all")) {
            try {
                piece = Piece.valueOf(args[3].toUpperCase());
            } catch (Exception e) {
                CommandDebug.INVALID_PIECE.message(sender);
                return;
            }
        } else {
            all = true;
        }
        int amount = 1;
        if (args.length == 5) {
            try {
                amount = Integer.parseInt(args[4]);
            } catch (Exception e) {
                CommandDebug.INVALID_AMOUNT.message(sender);
                return;
            }
        }
        for (int i = 0; i < amount; i++) {
            if (all) {
                for (ItemStack item : set.getSetPieces().values()) {
                    target.getInventory().addItem(item);
                }
            } else {
                try {
                    target.getInventory().addItem(set.getPiece(piece));
                } catch (Exception e) {
                    CommandDebug.PIECE_DOES_NOT_EXIST.message(sender);
                    return;
                }
            }
        }
        if (all) {
            MessageType.GIVE_RECEIVER.message(target, "all", set.getName(), String.valueOf(amount));
        } else {
            MessageType.GIVE_RECEIVER.message(target, piece.toString(), set.getName(), String.valueOf(amount));
        }
        Player player = null;
        if (sender instanceof Player) {
            if (!target.getUniqueId().equals(((Player) sender).getUniqueId())) {
                player = (Player) sender;
            }
        }
        if (!(sender instanceof Player) || player != null) {
            if (all) {
                MessageType.GIVE_GIVER.message(sender, target.getName(), "all", set.getName(), String.valueOf(amount));
            } else {
                MessageType.GIVE_GIVER.message(sender, target.getName(), piece.toString(), set.getName(), String.valueOf(amount));
            }
        }
    }
}