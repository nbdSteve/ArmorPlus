package com.nbdsteve.carmor.method.potion;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import com.nbdsteve.carmor.method.GetSetNumber;
import com.nbdsteve.carmor.method.InventoryArmorCheck;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This class contains a runnable the will check every second, this updates potion effects
 */
public class ServerPotionCheckRunnable {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    /**
     * Method containing code for the runnable
     */
    public ServerPotionCheckRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (pl.getServer().getOnlinePlayers().size() > 0) {
                    for (Player p : pl.getServer().getOnlinePlayers()) {
                        if (InventoryArmorCheck.checkArmor(p, lcf)) {
                            String setNumber = GetSetNumber.setNumber(p.getInventory().getHelmet().getItemMeta().getLore(), lcf);
                            for (String effect : lcf.getArmor().getStringList(setNumber + ".potion-effects")) {
                                String[] parts = effect.split(":");
                                PlayerPotionCheck.potionCheck(p, parts[0].toUpperCase(),
                                        Integer.parseInt(parts[1]) - 1);
                                p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(parts[0].toUpperCase()), 999999, Integer.parseInt(parts[1]) - 1));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(pl, 0L, lcf.getConfig().getInt("potion-check-delay"));
    }
}
