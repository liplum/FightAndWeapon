package net.liplum.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public class DamageArgs {
    private final float damage;
    @Nonnull
    private final DamageSource damageSource;
    @Nonnull
    private final Entity target;

    public DamageArgs(float damage, @Nonnull DamageSource damageSource, @Nonnull Entity target) {
        this.damage = damage;
        this.damageSource = damageSource;
        this.target = target;
    }

    public float getDamage() {
        return damage;
    }

    @Nonnull
    public DamageSource getDamageSource() {
        return damageSource;
    }

    @Nonnull
    public Entity getTarget() {
        return target;
    }
}
