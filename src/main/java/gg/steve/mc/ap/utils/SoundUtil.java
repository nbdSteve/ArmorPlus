package gg.steve.mc.ap.utils;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void playSound(ConfigurationSection section, String entry, Player player) {
        if (section.getBoolean(entry + ".sound.enabled")) {
            String sound = section.getString(entry + ".sound.type").toUpperCase();
            try {
                Sound.valueOf(sound);
            } catch (Exception e) {
                LogUtil.warning("You are using old sound values for your Minecraft version, the plugin automatically converts most of them. However, it is recommended that you change them in the configuration to the latest values from here: https://https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html");
                String attempt1 = "ENTITY_" + sound;
                try {
                    Sound.valueOf(attempt1);
                    sound = attempt1;
                } catch (Exception e1) {
                    String attempt2 = "BLOCK_" + sound;
                    try {
                        Sound.valueOf(attempt2);
                        sound = attempt2;
                    } catch (Exception e2) {
                        LogUtil.warning("Multiple attempts to convert the sound type were made but all failed, please check your configuration.");
                    }
                }
            }
            player.playSound(player.getLocation(),
                    Sound.valueOf(sound),
                    section.getInt(entry + ".sound.volume"),
                    section.getInt(entry + ".sound.pitch"));
        }
    }
}
