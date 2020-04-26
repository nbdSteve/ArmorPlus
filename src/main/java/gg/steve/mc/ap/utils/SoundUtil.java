package gg.steve.mc.ap.utils;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void playSound(ConfigurationSection section, String entry, Player player) {
        if (section.getBoolean(entry + ".sound.enabled")) {
            player.playSound(player.getLocation(),
                    Sound.valueOf(section.getString(entry + ".sound.type").toUpperCase()),
                    section.getInt(entry + ".sound.volume"),
                    section.getInt(entry + ".sound.pitch"));
        }
    }
}
