package net.liplum.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import org.jetbrains.annotations.NotNull;

public class DamageArgs {
    private final float damage;
    @NotNull
    private final DamageSource damageSource;
    @NotNull
    private final Entity target;

    public DamageArgs(float damage, @NotNull DamageSource damageSource, @NotNull Entity target) {
        this.damage = damage;
        this.damageSource = damageSource;
        this.target = target;
    }

    public float getDamage() {
        return damage;
    }

    @NotNull
    public DamageSource getDamageSource() {
        return damageSource;
    }

    @NotNull
    public Entity getTarget() {
        return target;
    }
}
