package gg.steve.mc.ap.data;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public interface HandItemData {

    void hitWithoutSetBuff(EntityDamageByEntityEvent event, Player damager);

    double calculateFinalDamage(double damage, double setIncrease);

    EntityDamageEvent.DamageCause getActiveCause();
}
