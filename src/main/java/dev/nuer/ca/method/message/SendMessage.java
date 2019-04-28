package dev.nuer.ca.method.message;

import dev.nuer.ca.file.LoadCarmorFiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Class that handles sending messages to a player
 */
public class SendMessage {

    /**
     * Send a default message to the player
     *
     * @param filePath the message to be sent
     * @param p        the player to send to
     * @param lcf      LoadCarmorFiles instance
     */
    public SendMessage(String filePath, Player p, LoadCarmorFiles lcf) {
        for (String line : lcf.getMessages().getStringList(filePath)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    /**
     * Send a message that has a placeholder in it
     *
     * @param filePath    the message to be sent
     * @param p           the player to send to
     * @param lcf         LoadCarmorFiles instance
     * @param placeHolder the thing to replace
     * @param replacement the replacement
     */
    public SendMessage(String filePath, Player p, LoadCarmorFiles lcf, String placeHolder,
                       String replacement) {
        for (String line : lcf.getMessages().getStringList(filePath)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace(placeHolder,
                    replacement));
        }
    }

    /**
     * Sends a message that has 2 placeholders to fill
     *
     * @param filePath     the message to be sent
     * @param p            the player to send to
     * @param lcf          LoadCarmorFiles instance
     * @param placeHolder  the thing to replace
     * @param replacement  the replacement
     * @param placeHolder2 the second thing to replace
     * @param replacement2 the second replacement
     */
    public SendMessage(String filePath, Player p, LoadCarmorFiles lcf, String placeHolder,
                       String replacement, String placeHolder2, String replacement2) {
        for (String line : lcf.getMessages().getStringList(filePath)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace(placeHolder,
                    replacement).replace(placeHolder2, replacement2));
        }
    }
}
