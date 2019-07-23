package dev.nuer.ca.event;

import dev.nuer.ca.Carmor;
import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.GetSetNumber;
import dev.nuer.ca.method.InventoryArmorCheck;
import dev.nuer.ca.method.modifier.PassiveModifiers;
import dev.nuer.ca.method.potion.PlayerPotionCheck;
import dev.nuer.ca.method.armorequiplistener.ArmorEquipEvent;
import dev.nuer.ca.method.message.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Class that handles armor equiping, it will send a message to the player if they have a full set
 */
public class EquipEvent implements Listener {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    /**
     * All code for the equip event is in this method
     *
     * @param e the event, cannot be null
     */
    @EventHandler
    public void ArmorEquip(ArmorEquipEvent e) {
        //Store the set number
        String setNumber;
        //Store the player from the event
        Player p = e.getPlayer();
        //Make sure its an actual item and not air
        if (e.getNewArmorPiece() == null || e.getNewArmorPiece().getType().equals(Material.AIR)) {
            return;
        }
        //Verify that the player is wearing armor with meta & lore
        if (e.getNewArmorPiece().hasItemMeta()) {
            if (e.getNewArmorPiece().getItemMeta().hasLore()) {
                //Check to see if the armor is part of a full set
                if (InventoryArmorCheck.checkEquipArmor(p, lcf,
                        e.getNewArmorPiece().getItemMeta().getLore(), e.getNewArmorPiece().getType())) {
                    //Get the set number
                    setNumber = GetSetNumber.setNumber(e.getNewArmorPiece().getItemMeta().getLore(), lcf);
                    //Split the set number to get the permission node
                    String[] perm = setNumber.split("-");
                    if (lcf.getConfig().getBoolean("permission-based-wear")) {
                        if (!p.hasPermission("carmor.wear." + perm[2])) {
                            //Cancel the event
                            e.setCancelled(true);
                            //Send the player a message
                            new SendMessage("no-wear-permission", p, lcf);
                            return;
                        }
                    }
                    //Apply the passive modifiers to the player
                    PassiveModifiers.applyPassiveModifiers(lcf.getArmor().getStringList(setNumber + ".modifiers"), p);
                    //Send the player the equip message for that set
                    new SendMessage(setNumber + ".equip-message", p, lcf);
                    // Commands to be ran when they equip the item
                    for (String command : lcf.getArmor().getStringList(setNumber + ".commandonequip")) {
                    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{username}", p.getName()));
                    }
                    //Give the player the necessary potion effects
                    for (String effect : lcf.getArmor().getStringList(setNumber + ".potion-effects")) {
                        String[] parts = effect.split(":");
                        PlayerPotionCheck.potionCheck(p, parts[0].toUpperCase(),
                                Integer.parseInt(parts[1]) - 1);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(parts[0].toUpperCase()), 999999, Integer.parseInt(parts[1]) - 1));
                    }
                    //Working on adding particles
//                    new BukkitRunnable() {
//                        Location location = p.getLocation();
//                        double t = 0;
//                        double r = 1;
//
//                        @Override
//                        public void run() {
//
//                            t = t + Math.PI / 8;
//                            double x = r * cos(t);
//                            double y = t;
//                            double z = r * sin(t);
//
//                            location.add(x, y, z);
//                            p.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0);
//                            location.subtract(x, y, z);
//
//                            if (t > Math.PI * 2) {
//                                cancel();
//                            }
//                        }
//                    }.runTaskTimer(pl, 0, 1);
                }
            }
        }
    }
}
