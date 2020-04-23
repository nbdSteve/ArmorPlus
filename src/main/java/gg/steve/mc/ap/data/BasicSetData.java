package gg.steve.mc.ap.data;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class BasicSetData implements SetData {
    private SetDataType type;
    private double increase;
    private double reduction;
    private double kb;

    public BasicSetData(double increase, double reduction, double kb) {
        this.type = SetDataType.BASIC;
        this.increase = increase;
        this.reduction = reduction;
        this.kb = kb;
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * this.increase);
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        event.setDamage(EntityDamageEvent.DamageModifier.BASE, event.getDamage() * this.reduction);
        event.getEntity().setVelocity(event.getDamager().getLocation().getDirection().setY(0).normalize().multiply(this.kb));
    }

    // <-- Getters and Setters from this point on -->
    public SetDataType getType() {
        return type;
    }

    public void setType(SetDataType type) {
        this.type = type;
    }

    public double getIncrease() {
        return increase;
    }

    public void setIncrease(double increase) {
        this.increase = increase;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public double getKb() {
        return kb;
    }

    public void setKb(double kb) {
        this.kb = kb;
    }
}
