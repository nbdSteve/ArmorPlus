package dev.nuer.ca.method.checks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class PlayerSafeBlockCheck {

    public static boolean safeLocation(Location location) {
        Block currentBlock = location.getBlock();
        if (currentBlock.getType().isSolid() && currentBlock.getLocation().add(0, 1, 0).getBlock().getType().isSolid()) {
            return false; //Fail, the block is solid and player will suffocate
        }
        Block headHeightBlock = currentBlock.getRelative(BlockFace.UP);
        if (headHeightBlock.getType().isSolid()) {
            return false;
        }
        Block standingOnBlock = currentBlock.getRelative(BlockFace.DOWN);
        if (!standingOnBlock.getType().isSolid()) {
            return false;
        }
        return true;
    }
}
