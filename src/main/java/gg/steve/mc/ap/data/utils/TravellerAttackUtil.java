package gg.steve.mc.ap.data.utils;

import gg.steve.mc.ap.message.MessageType;
import gg.steve.mc.ap.utils.LogUtil;
import gg.steve.mc.ap.utils.SoundUtil;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TravellerAttackUtil implements Listener {
    private static List<UUID> fallingBlocks = new ArrayList<>();
//    private static BukkitTask task;
//
//    public static BukkitTask init() {
//        task = Bukkit.getScheduler().runTaskTimer(ArmorPlus.get(), () -> {
//            if (fallingBlocks.isEmpty()) return;
//            for (FallingBlock block : fallingBlocks) {
//                if (block.set) {
//                    block.getLocation().getBlock().setType(Material.AIR);
//                    fallingBlocks.remove(block);
//                }
//            }
//            task.cancel();
//        },0l, 1l);
//        return task;
//    }

    public static void attack(Player player, Player damager, int size, double damage, double height, ConfigurationSection section, String entry) {
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                Block spawn = player.getWorld().getHighestBlockAt(player.getLocation().getBlock().getRelative(x, 0, z).getLocation()).getRelative(0, -1, 0);
                FallingBlock falling = spawn.getWorld().spawnFallingBlock(spawn.getLocation().add(0, height, 0), spawn.getType(), spawn.getData());
                fallingBlocks.add(falling.getUniqueId());
            }
        }
//        if (task == null) {
//            init();
//        }
        player.damage(damage, damager);
        player.setVelocity(player.getVelocity().subtract(player.getVelocity()));
        SoundUtil.playSound(section, entry, player);
        MessageType.doAttackedMessage(section, entry, player);
    }

    @EventHandler
    public void blockPlace(EntityChangeBlockEvent event) {
        if (fallingBlocks.contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
            fallingBlocks.remove(event.getEntity().getUniqueId());
        }
    }
}
