package gg.steve.mc.ap.data.utils;

import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.PlayerDirectionUtil;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class WarpUtil {

    public static boolean isSafe(Block block) {
        if (block.getType().isSolid() && block.getLocation().add(0, 1, 0).getBlock().getType().isSolid()) {
            return false; //Fail, the block is solid and player will suffocate
        }
        Block headHeightBlock = block.getRelative(BlockFace.UP);
        if (headHeightBlock.getType().isSolid()) {
            return false;
        }
        Block standingOnBlock = block.getRelative(BlockFace.DOWN);
        if (!standingOnBlock.getType().isSolid()) {
            return false;
        }
        return true;
    }

    public static void warp(Player player, Entity attacker, double distance, ConfigurationSection section, String entry) {
        //Get the attackers direction to teleport behind them
        PlayerDirectionUtil facing = new PlayerDirectionUtil(attacker.getLocation().getYaw());
        //Set the player to have no knockback
        player.setVelocity(player.getVelocity().subtract(player.getVelocity()));
        //Create the new location
        Location behindPlayer = attacker.getLocation();
        //Create a new location to query
        Location safeLocation = null;
        //Check multiples first
        if (facing.positiveX() && facing.positiveZ()) {
            safeLocation = behindPlayer.add(0 - distance / 1.6, 0, 0 - distance / 1.6);
        } else if (facing.positiveX() && facing.negativeZ()) {
            safeLocation = behindPlayer.add(0 - distance / 1.6, 0, distance / 1.6);
        } else if (facing.negativeX() && facing.positiveZ()) {
            safeLocation = behindPlayer.add(distance / 1.6, 0, 0 - distance / 1.6);
        } else if (facing.negativeX() && facing.negativeZ()) {
            safeLocation = behindPlayer.add(distance / 1.6, 0, distance / 1.6);
        } else if (facing.positiveX()) {
            safeLocation = behindPlayer.add(0 - distance, 0, 0);
        } else if (facing.positiveZ()) {
            safeLocation = behindPlayer.add(0, 0, 0 - distance);
        } else if (facing.negativeX()) {
            safeLocation = behindPlayer.add(distance, 0, 0);
        } else if (facing.negativeZ()) {
            safeLocation = behindPlayer.add(0, 0, distance);
        }
        if (safeLocation != null) {
            if (isSafe(safeLocation.getBlock())) {
                player.teleport(safeLocation);
            } else {
                MessageType.UNSAFE_WARP_LOCATION.message(player);
                return;
            }
        }
        try {
            SoundUtil.playSound(section, entry, (Player) attacker);
            MessageType.doAttackedMessage(section, entry, (Player) attacker);
        } catch (Exception e) {
            //Do nothing the attacker just is not a player.
        }
    }
}