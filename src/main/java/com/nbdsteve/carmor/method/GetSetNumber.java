package com.nbdsteve.carmor.method;

import com.nbdsteve.carmor.file.LoadCarmorFiles;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * Class containing static method to check what armor set the player is wearing
 */
public class GetSetNumber {

    /**
     * Check what armor set the player is wearing from the armor.yml
     *
     * @param lore the lore of the item to check
     * @param lcf  the files configuration
     * @return String, the set number
     */
    public static String setNumber(List<String> lore, LoadCarmorFiles lcf) {
        String setNumber = null;
        for (int i = 0; i <= 54; i++) {
            setNumber = "armor-set-" + String.valueOf(i);
            try {
                lcf.getArmor().getString(setNumber + ".unique");
                if (lore.contains(ChatColor.translateAlternateColorCodes('&',
                        lcf.getArmor().getString(setNumber + ".unique")))) {
                    return setNumber;
                }
            } catch (Exception e) {
                //Do nothing this armor set just doesn't exist
            }
        }
        return setNumber;
    }
}
