package com.nbdsteve.carmor.command;

import com.nbdsteve.carmor.Carmor;
import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
        if (c.getName().equalsIgnoreCase("ca") || c.getName().equalsIgnoreCase("carmar")) {
            if (args.length == 0) {
                if (s instanceof Player) {
                    if (s.hasPermission("carmor.help")) {
                        for (String line : lcf.getMessages().getStringList("help")) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    } else {
                        for (String line : lcf.getMessages().getStringList("no-permission")) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    }
                } else {
                    pl.getLogger().info("The help message can only be seen using game chat.");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.help")) {
                            for (String line : lcf.getMessages().getStringList("help")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            for (String line : lcf.getMessages().getStringList("no-permission")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        }
                    } else {
                        pl.getLogger().info("The help message can only be seen using game chat.");
                    }
                } else if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.reload")) {
                            lcf.reload();
                            for (String line : lcf.getMessages().getStringList("reload")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            for (String line : lcf.getMessages().getStringList("no-permission")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        }
                    } else {
                        lcf.reload();
                        pl.getLogger().info("You successfully reloaded all of the configuration files");
                    }
                } else {
                    if (s instanceof Player) {
                        for (String line : lcf.getMessages().getStringList("invalid-command")) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    } else {
                        pl.getLogger().info("The command you entered is invalid.");
                    }
                }
            } else if (args.length == 4 || args.length == 5) {
                if (args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give")) {
                    if (s instanceof Player) {
                        if (!s.hasPermission("carmor.give")) {
                            for (String line : lcf.getMessages().getStringList("no-permission")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                            return true;
                        }
                    }
                    Player target = null;
                    int setNumber = 0;
                    int amount = 1;
                    String armorPiece = args[3].toUpperCase();
                    String item = null;
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
                            for (String line : lcf.getMessages().getStringList("invalid-armor-piece")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
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
                            for (String line : lcf.getMessages().getStringList("invalid-player")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            pl.getLogger().info("The command you entered is invalid");
                        }
                    }
                    try {
                        setNumber = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        if (s instanceof Player) {
                            for (String line : lcf.getMessages().getStringList("invalid-armor-set")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            pl.getLogger().info("The armor set you entered doesn't exist.");
                        }
                    }
                    try {
                        new ItemStack(Material.valueOf(armorPiece));
                    } catch (Exception e) {
                        if (s instanceof Player) {
                            for (String line : lcf.getMessages().getStringList("invalid-armor-piece")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            pl.getLogger().info("The armor piece you entered isn't valid.");
                        }
                    }
                    if (args.length == 5) {
                        try {
                            amount = Integer.parseInt(args[4]);
                        } catch (Exception e) {
                            if (s instanceof Player) {
                                for (String line : lcf.getMessages().getStringList("invalid-amount")) {
                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                                }
                            } else {
                                pl.getLogger().info("The amount you entered is invalid.");
                            }
                        }
                    }
                    if (setNumber != 0) {
                        ItemStack setPiece = new ItemStack(Material.valueOf(armorPiece));
                        ItemMeta pieceMeta = setPiece.getItemMeta();
                        List<String> pieceLore = new ArrayList<>();
                        pieceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                lcf.getArmor().getString(armorSet + ".name")));
                        for (String lore : lcf.getArmor().getStringList(armorSet + ".lore")) {
                            pieceLore.add(ChatColor.translateAlternateColorCodes('&', lore));
                        }
                        for (String ench : lcf.getArmor().getStringList(armorSet + ".enchantments")) {
                            String[] parts = ench.split(":");
                            pieceMeta.addEnchant(Enchantment.getByName(parts[0].toUpperCase()), Integer.parseInt(parts[1]), true);
                        }
                        pieceMeta.setLore(pieceLore);
                        setPiece.setItemMeta(pieceMeta);
                        setPiece.setAmount(amount);
                        target.getInventory().addItem(setPiece);
                    } else {
                        if (s instanceof Player) {
                            for (String line : lcf.getMessages().getStringList("invalid-armor-set")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            pl.getLogger().info("The armor set you entered doesn't exist.");
                        }
                    }
                } else {
                    if (s instanceof Player) {
                        if (s.hasPermission("carmor.give")) {
                            for (String line : lcf.getMessages().getStringList("invalid-command")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        } else {
                            for (String line : lcf.getMessages().getStringList("no-permission")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }
                        }
                    } else {
                        pl.getLogger().info("the command you entered is invalid.");
                    }
                }
            } else {
                if (s instanceof Player) {
                    if (s.hasPermission("carmor.give") || s.hasPermission("carmor.reload")
                            || s.hasPermission("carmor.help")) {
                        for (String line : lcf.getMessages().getStringList("invalid-command")) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    } else {
                        for (String line : lcf.getMessages().getStringList("no-permission")) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    }
                } else {
                    pl.getLogger().info("the command you entered is invalid.");
                }
            }
        }
        return true;
    }
}