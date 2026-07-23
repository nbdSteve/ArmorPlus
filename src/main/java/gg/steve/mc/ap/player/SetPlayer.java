package gg.steve.mc.ap.player;

import gg.steve.mc.ap.armor.Set;
import org.bukkit.entity.Player;

public class SetPlayer {
    private Player player;
    private Set set;

    public SetPlayer(Player player, Set set) {
        this.player = player;
        this.set = set;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
