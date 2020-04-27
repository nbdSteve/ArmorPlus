package gg.steve.mc.ap.data;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public interface SetData {

    void onApply(Player player);

    void onRemoval(Player player);

    void onHit(EntityDamageByEntityEvent event, Player damager);

    void onDamage(EntityDamageByEntityEvent event);

    void onFall(EntityDamageEvent event);

    void onHungerDeplete(FoodLevelChangeEvent event);
}
