package dev.nuer.ca.method.specialattack;

import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.GetPlayerFacingDirection;
import dev.nuer.ca.method.checks.PlayerSafeBlockCheck;
import dev.nuer.ca.method.message.SendMessage;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class WarpBehindAttacker {

    public WarpBehindAttacker(Player player, Entity attacker, int blocksWarped,
                              LoadCarmorFiles lcf) {
        //Get the attackers direction to teleport behind them
        GetPlayerFacingDirection facingDirection =
                new GetPlayerFacingDirection(attacker.getLocation().getYaw());
        //Set the player to have no knockback
        player.setVelocity(player.getVelocity().subtract(player.getVelocity()));
        //Create the new location
        Location behindPlayer = attacker.getLocation();
        //Create a new location to query
        Location safeLocation = null;
        //Check multiples first
        if (facingDirection.positiveX() && facingDirection.positiveZ()) {
            safeLocation = behindPlayer.add(0 - blocksWarped / 1.6, 0, 0 - blocksWarped / 1.6);
        } else if (facingDirection.positiveX() && facingDirection.negativeZ()) {
            safeLocation = behindPlayer.add(0 - blocksWarped / 1.6, 0, blocksWarped / 1.6);
        } else if (facingDirection.negativeX() && facingDirection.positiveZ()) {
            safeLocation = behindPlayer.add(blocksWarped / 1.6, 0, 0 - blocksWarped / 1.6);
        } else if (facingDirection.negativeX() && facingDirection.negativeZ()) {
            safeLocation = behindPlayer.add(blocksWarped / 1.6, 0, blocksWarped / 1.6);
        } else if (facingDirection.positiveX()) {
            safeLocation = behindPlayer.add(0 - blocksWarped, 0, 0);
        } else if (facingDirection.positiveZ()) {
            safeLocation = behindPlayer.add(0, 0, 0 - blocksWarped);
        } else if (facingDirection.negativeX()) {
            safeLocation = behindPlayer.add(blocksWarped, 0, 0);
        } else if (facingDirection.negativeZ()) {
            safeLocation = behindPlayer.add(0, 0, blocksWarped);
        }
        if (safeLocation != null) {
            if (PlayerSafeBlockCheck.safeLocation(safeLocation)) {
                player.teleport(safeLocation);
            } else {
                new SendMessage("unsafe-warp-location", player, lcf);
                return;
            }
        }
        try {
            new SendMessage("warp-attack-attacker", (Player) attacker, lcf, "{player}",
                    player.getName());
        } catch (Exception e) {
            //Do nothing the attacker just is not a player.
        }
        new SendMessage("warp-attack-player", player, lcf, "{player}", attacker.getName());
    }
}
