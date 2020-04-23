package gg.steve.mc.ap.player;

import gg.steve.mc.ap.armor.Set;
import gg.steve.mc.ap.armor.SetManager;
import org.bukkit.entity.Player;

public class SetPlayer {
    private Player player;
    private Set set;

    public SetPlayer(Player player, String setName) {
        this.player = player;
        this.set = SetManager.getSet(setName);
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
