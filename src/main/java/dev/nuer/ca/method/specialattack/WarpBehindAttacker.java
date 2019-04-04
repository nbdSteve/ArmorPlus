package dev.nuer.ca.method.specialattack;

import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.GetPlayerFacingDirection;
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
        //Check multiples first
        if (facingDirection.positiveX() && facingDirection.positiveZ()) {
            player.teleport(behindPlayer.add(0 - blocksWarped / 1.6, 0, 0 - blocksWarped / 1.6));
        } else if (facingDirection.positiveX() && facingDirection.negativeZ()) {
            player.teleport(behindPlayer.add(0 - blocksWarped / 1.6, 0, blocksWarped / 1.6));
        } else if (facingDirection.negativeX() && facingDirection.positiveZ()) {
            player.teleport(behindPlayer.add(blocksWarped / 1.6, 0, 0 - blocksWarped / 1.6));
        } else if (facingDirection.negativeX() && facingDirection.negativeZ()) {
            player.teleport(behindPlayer.add(blocksWarped / 1.6, 0, blocksWarped / 1.6));
        } else if (facingDirection.positiveX()) {
            player.teleport(behindPlayer.add(0 - blocksWarped, 0, 0));
        } else if (facingDirection.positiveZ()) {
            player.teleport(behindPlayer.add(0, 0, 0 - blocksWarped));
        } else if (facingDirection.negativeX()) {
            player.teleport(behindPlayer.add(blocksWarped, 0, 0));
        } else if (facingDirection.negativeZ()) {
            player.teleport(behindPlayer.add(0, 0, blocksWarped));
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
