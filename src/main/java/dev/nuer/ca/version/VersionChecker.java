package dev.nuer.ca.version;

import dev.nuer.ca.Carmor;
import dev.nuer.ca.file.LoadCarmorFiles;
import dev.nuer.ca.method.message.SendMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class that handles checking for version updates
 */
public class VersionChecker implements Listener {
    //Store the resource key from spigot
    private static String resourceKey = "56616";

    /**
     * Checks the latest version against the current version
     *
     * @param player Player, player to send the update message to
     * @param lcf    LoadCarmorFiles, files instance
     */
    public static void checkVersion(Player player, LoadCarmorFiles lcf) {
        try {
            URLConnection urlConn = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceKey).openConnection();
            String version = new BufferedReader(new InputStreamReader(urlConn.getInputStream())).readLine();
            if (!version.equalsIgnoreCase(lcf.getConfig().getString("version"))) {
                Carmor.getPlugin(Carmor.class).getLogger().severe("[Carmor] There is a new version of Tools+ available for download, please update to the latest version.");
                if (player != null) {
                    new SendMessage("outdated-version", player, lcf, "{currentVersion}",
                            lcf.getConfig().getString("version"), "{latestVersion}", version);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            Bukkit.getScheduler().runTaskLater(Carmor.getPlugin(Carmor.class), () -> {
                checkVersion(event.getPlayer(), Carmor.getPlugin(Carmor.class).getFiles());
            }, 5L);
        }
    }
}
