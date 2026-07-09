package gg.steve.mc.ap.data.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Characterization tests for WarpUtil.isSafe().
 * Pins the three-block safety check logic before 4H extracts warp math.
 */
@ExtendWith(MockitoExtension.class)
class WarpUtilTest {

    @Test
    void isSafe_solidFloor_airAbove_returnsTrue() {
        // Block at feet level is not solid
        Block block = mock(Block.class);
        Material blockMat = mock(Material.class);
        when(block.getType()).thenReturn(blockMat);
        when(blockMat.isSolid()).thenReturn(false);

        // Head level - not solid
        Block head = mock(Block.class);
        Material headMat = mock(Material.class);
        when(block.getRelative(BlockFace.UP)).thenReturn(head);
        when(head.getType()).thenReturn(headMat);
        when(headMat.isSolid()).thenReturn(false);

        // Floor - solid
        Block floor = mock(Block.class);
        Material floorMat = mock(Material.class);
        when(block.getRelative(BlockFace.DOWN)).thenReturn(floor);
        when(floor.getType()).thenReturn(floorMat);
        when(floorMat.isSolid()).thenReturn(true);

        assertTrue(WarpUtil.isSafe(block));
    }

    @Test
    void isSafe_solidBlock_solidAbove_returnsFalse() {
        // Both the block and block+1 are solid -> player suffocates
        Block block = mock(Block.class);
        when(block.getType()).thenReturn(mock(Material.class));
        when(block.getType().isSolid()).thenReturn(true);

        Location loc = mock(Location.class);
        when(block.getLocation()).thenReturn(loc);
        when(loc.add(0, 1, 0)).thenReturn(loc);
        Block above = mock(Block.class);
        when(loc.getBlock()).thenReturn(above);
        when(above.getType()).thenReturn(mock(Material.class));
        when(above.getType().isSolid()).thenReturn(true);

        assertFalse(WarpUtil.isSafe(block));
    }

    @Test
    void isSafe_solidHeadBlock_returnsFalse() {
        // Block not solid, but head-height block is solid
        Block block = mock(Block.class);
        when(block.getType()).thenReturn(mock(Material.class));
        when(block.getType().isSolid()).thenReturn(false);

        Block headBlock = mock(Block.class);
        when(block.getRelative(BlockFace.UP)).thenReturn(headBlock);
        when(headBlock.getType()).thenReturn(mock(Material.class));
        when(headBlock.getType().isSolid()).thenReturn(true);

        assertFalse(WarpUtil.isSafe(block));
    }

    @Test
    void isSafe_noSolidFloor_returnsFalse() {
        // Block and head not solid, but nothing to stand on
        Block block = mock(Block.class);
        when(block.getType()).thenReturn(mock(Material.class));
        when(block.getType().isSolid()).thenReturn(false);

        Block headBlock = mock(Block.class);
        when(block.getRelative(BlockFace.UP)).thenReturn(headBlock);
        when(headBlock.getType()).thenReturn(mock(Material.class));
        when(headBlock.getType().isSolid()).thenReturn(false);

        Block floorBlock = mock(Block.class);
        when(block.getRelative(BlockFace.DOWN)).thenReturn(floorBlock);
        when(floorBlock.getType()).thenReturn(mock(Material.class));
        when(floorBlock.getType().isSolid()).thenReturn(false);

        assertFalse(WarpUtil.isSafe(block));
    }

    @Test
    void isSafe_solidBlock_nonSolidAbove_checksHeadBlock() {
        // Block is solid but block+1 is NOT solid. First check passes.
        // Then checks headBlock (relative UP) which is not solid.
        // Then checks floor (relative DOWN) which is solid.
        // NOTE: There's a quirk here - the first check uses block.getLocation().add(0,1,0).getBlock()
        // while the second check uses block.getRelative(BlockFace.UP). These reference the same block.
        Block block = mock(Block.class);
        when(block.getType()).thenReturn(mock(Material.class));
        when(block.getType().isSolid()).thenReturn(true);

        Location loc = mock(Location.class);
        when(block.getLocation()).thenReturn(loc);
        when(loc.add(0, 1, 0)).thenReturn(loc);
        Block above = mock(Block.class);
        when(loc.getBlock()).thenReturn(above);
        when(above.getType()).thenReturn(mock(Material.class));
        when(above.getType().isSolid()).thenReturn(false);

        // headHeightBlock via getRelative
        Block headBlock = mock(Block.class);
        when(block.getRelative(BlockFace.UP)).thenReturn(headBlock);
        when(headBlock.getType()).thenReturn(mock(Material.class));
        when(headBlock.getType().isSolid()).thenReturn(false);

        // standingOnBlock
        Block floorBlock = mock(Block.class);
        when(block.getRelative(BlockFace.DOWN)).thenReturn(floorBlock);
        when(floorBlock.getType()).thenReturn(mock(Material.class));
        when(floorBlock.getType().isSolid()).thenReturn(true);

        assertTrue(WarpUtil.isSafe(block));
    }

}
