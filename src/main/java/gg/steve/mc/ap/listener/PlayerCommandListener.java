package gg.steve.mc.ap.listener;

import gg.steve.mc.ap.armor.Piece;
import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import gg.steve.mc.ap.message.CommandDebug;
import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        String setName = null;
        for (String key : SetManager.getSets().keySet()) {
            if (args[0].equalsIgnoreCase("/" + key)) {
                setName = key;
                event.setCancelled(true);
            }
        }
        if (setName == null) return;
        Set set = SetManager.getSet(setName);
        if (args.length == 1) {
            if (!PermissionNode.GUI.hasPermission(event.getPlayer())) {
                CommandDebug.INSUFFICIENT_PERMISSION.message(event.getPlayer(), PermissionNode.GUI.get());
            } else {
                set.openGui(event.getPlayer());
            }
            return;
        }
        if (args[1].equalsIgnoreCase("g") || args[1].equalsIgnoreCase("give")) {
            if (args.length < 4) {
                CommandDebug.INVALID_NUMBER_OF_ARGUMENTS.message(event.getPlayer());
                return;
            }
            if (!PermissionNode.GIVE.hasPermission(event.getPlayer())) {
                CommandDebug.INSUFFICIENT_PERMISSION.message(event.getPlayer(), PermissionNode.GIVE.get());
                return;
            }
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                CommandDebug.TARGET_NOT_ONLINE.message(event.getPlayer());
                return;
            }
            Piece piece = null;
            boolean all = false;
            if (!args[3].equalsIgnoreCase("all")) {
                try {
                    piece = Piece.valueOf(args[3].toUpperCase());
                } catch (Exception e) {
                    CommandDebug.INVALID_PIECE.message(event.getPlayer());
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
                    CommandDebug.INVALID_AMOUNT.message(event.getPlayer());
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
                        CommandDebug.PIECE_DOES_NOT_EXIST.message(event.getPlayer());
                        return;
                    }
                }
            }
            if (all) {
                MessageType.GIVE_RECEIVER.message(target, "all", set.getName(), String.valueOf(amount));
            } else {
                MessageType.GIVE_RECEIVER.message(target, piece.toString(), set.getName(), String.valueOf(amount));
            }
            if (!target.getUniqueId().equals(event.getPlayer().getUniqueId())) {
                if (all) {
                    MessageType.GIVE_GIVER.message(event.getPlayer(), target.getName(), "all", set.getName(), String.valueOf(amount));
                } else {
                    MessageType.GIVE_GIVER.message(event.getPlayer(), target.getName(), piece.toString(), set.getName(), String.valueOf(amount));
                }
            }
        } else {
            CommandDebug.INVALID_COMMAND.message(event.getPlayer());
        }
    }
}