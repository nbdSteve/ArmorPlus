package dev.nuer.ca.command;

import dev.nuer.ca.Carmor;
import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.gui.MainGui;
import dev.nuer.ca.method.ArmorPieceMethods;
import dev.nuer.ca.method.message.SendMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Command class from /ca and /carmor
 */
public class CaCommand implements CommandExecutor {
    //Register the main class
    private Plugin pl = Carmor.getPlugin(Carmor.class);
    //Get the files for the plugin
    private LoadCarmorFiles lcf = ((Carmor) pl).getFiles();

    //Create the constructor to make the command work
    public CaCommand(Carmor pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if (c.getName().equalsIgnoreCase("ca") || c.getName().equalsIgnoreCase("carmor")) {
            //Store the player for the message methods
            Player p = null;
            if (s instanceof Player) {
                p = (Player) s;
            }
            if (args.length == 0) {
                if (s instanceof Player) {
                    if (s.hasPermission("carmor.gui")) {
                        MainGui mainMenu = new MainGui();
                        mainMenu.mainGui((Player) s);
                        new SendMessage("open-gui", p, lcf);
                    } else {
                        new SendMessage("no-permission", p, lcf);
                    }
                } else {
                    pl.getLogger().info("The gui can only be seen in game.");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.help")) {
                            new SendMessage("help", p ,lcf);
                        } else {
                            new SendMessage("no-permission", p, lcf);
                        }
                    } else {
                        pl.getLogger().info("The help message can only be seen using game chat.");
                    }
                } else if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.reload")) {
                            lcf.reload();
                            new SendMessage("reload", p, lcf);
                        } else {
                            new SendMessage("no-permission", p, lcf);
                        }
                    } else {
                        lcf.reload();
                        pl.getLogger().info("You successfully reloaded all of the configuration files");
                    }
                } else if (args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("list")) {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.list")) {
                            s.sendMessage(" ");
                            for (int i = 0; i <= 54; i++) {
                                String setNumber = "armor-set-" + String.valueOf(i);
                                try {
                                    s.sendMessage(String.valueOf(i) + ". " + ChatColor.translateAlternateColorCodes('&', lcf.getArmor().getString(setNumber + ".unique")));
                                } catch (Exception e) {
                                    //Do nothing this armor set just doesn't exist
                                }
                            }
                            s.sendMessage(" ");
                        } else {
                            new SendMessage("no-permission", p, lcf);
                        }
                    } else {
                        pl.getLogger().info("The list of armor sets can only be viewed in game, check your armor.yml");
                    }
                } else {
                    if (s instanceof Player) {
                        new SendMessage("invalid-command", p, lcf);
                    } else {
                        pl.getLogger().info("The command you entered is invalid.");
                    }
                }
            } else if (args.length == 4 || args.length == 5) {
                if (args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give")) {
                    if (s instanceof Player) {
                        if (!s.hasPermission("carmor.give")) {
                            new SendMessage("no-permission", p, lcf);
                            return true;
                        }
                    }
                    Player target = null;
                    int setNumber = 0;
                    int amount = 1;
                    String armorPiece = args[3].toUpperCase();
                    String item;
                    if (args[3].contains("helmet")) {
                        item = "helmet";
                    } else if (args[3].contains("chestplate")) {
                        item = "chestplate";
                    } else if (args[3].contains("leggings")) {
                        item  = "leggings";
                    } else if (args[3].contains("boots")) {
                        item = "boots";
                    } else {
                        if (s instanceof Player) {
                            new SendMessage("invalid-armor-piece", p, lcf);
                        } else {
                            pl.getLogger().info("The armor piece you entered isn't valid.");
                        }
                        return true;
                    }
                    String armorSet = "armor-set-" + args[2] + "." + item;
                    try {
                        target = pl.getServer().getPlayer(args[1]);
                    } catch (Exception e) {
                        if (s instanceof Player) {
                            new SendMessage("invalid-player", p, lcf);
                        } else {
                            pl.getLogger().info("The command you entered is invalid");
                        }
                    }
                    try {
                        setNumber = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        if (s instanceof Player) {
                            new SendMessage("invalid-armor-set", p, lcf);
                        } else {
                            pl.getLogger().info("The armor set you entered doesn't exist.");
                        }
                    }
                    try {
                        new ItemStack(Material.valueOf(armorPiece));
                    } catch (Exception e) {
                        if (s instanceof Player) {
                            new SendMessage("invalid-armor-piece", p, lcf);
                        } else {
                            pl.getLogger().info("The armor piece you entered isn't valid.");
                        }
                    }
                    if (args.length == 5) {
                        try {
                            amount = Integer.parseInt(args[4]);
                        } catch (Exception e) {
                            if (s instanceof Player) {
                                new SendMessage("invalid-amount", p, lcf);
                            } else {
                                pl.getLogger().info("The amount you entered is invalid.");
                            }
                        }
                    }
                    if (setNumber != 0) {
                        ItemStack setPiece = new ItemStack(Material.valueOf(armorPiece));
                        ItemMeta pieceMeta = setPiece.getItemMeta();
                        List<String> pieceLore = new ArrayList<>();
                        ArmorPieceMethods.setDisplayName(armorSet + ".name", pieceMeta, lcf);
                        ArmorPieceMethods.addLore(armorSet + ".lore", pieceLore, lcf);
                        ArmorPieceMethods.addEnchantments(armorSet + ".enchantments", pieceMeta, lcf);
                        pieceMeta.setLore(pieceLore);
                        setPiece.setItemMeta(pieceMeta);
                        setPiece.setAmount(amount);
                        target.getInventory().addItem(setPiece);
                    } else {
                        if (s instanceof Player) {
                            new SendMessage("invalid-armor-set", p, lcf);
                        } else {
                            pl.getLogger().info("The armor set you entered doesn't exist.");
                        }
                    }
                } else {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.give")) {
                            new SendMessage("invalid-command", p, lcf);
                        } else {
                            new SendMessage("no-permission", p, lcf);
                        }
                    } else {
                        pl.getLogger().info("the command you entered is invalid.");
                    }
                }
            } else {
                if (s instanceof Player) {
                    if (s.hasPermission("carmor.give") || s.hasPermission("carmor.reload")
                            || s.hasPermission("carmor.help")) {
                        new SendMessage("invalid-command", p, lcf);
                    } else {
                        new SendMessage("no-permission", p, lcf);
                    }
                } else {
                    pl.getLogger().info("the command you entered is invalid.");
                }
            }
        }
        return true;
    }
}