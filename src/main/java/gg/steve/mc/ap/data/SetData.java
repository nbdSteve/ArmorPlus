package gg.steve.mc.ap.data;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface SetData {
    //    private SetDataType type;
//    private double increase;
//    private double reduction;
//    private double kb;
//
//    public SetData(SetDataType type) {
//        this.type = type;
//        this.increase = increase;
//        this.reduction = reduction;
//        this.kb = kb;
//    }
//
//    public SetDataType getType() {
//        return type;
//    }
//
//    public void applyAggressiveModifiers(EntityDamageByEntityEvent event) {
//        event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * increase);
//    }
//
//    public void applyPassiveModifiers(EntityDamageByEntityEvent event) {
//        event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * reduction);
//        event.getEntity().setVelocity(event.getDamager().getLocation().getDirection().setY(0).normalize().multiply(kb));
    void onApply(Player player);

    void onRemoval(Player player);

    void onHit(EntityDamageByEntityEvent event);

    void onDamage(EntityDamageByEntityEvent event);
}
