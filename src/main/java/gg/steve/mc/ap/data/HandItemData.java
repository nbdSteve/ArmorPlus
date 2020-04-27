package gg.steve.mc.ap.data;

import org.bukkit.event.entity.EntityDamageEvent;

public interface HandItemData {

    double calculateFinalDamage(double damage, double setIncrease);

    EntityDamageEvent.DamageCause getActiveCause();
}
