package net.liplum.api.weapon;

import net.liplum.lib.items.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponAttackArgs<This extends WeaponAttackArgs<?>> {
    private World world;
    private EntityLivingBase attacker;
    private Entity target;
    private IWeaponCore weaponCore;
    private IGemstone gemstone;
    private IModifier<?> modifier;
    private ItemStack itemStack;
    private DamageArgs initialDamage;

    @Nonnull
    public World getWorld() {
        return world;
    }

    @Nonnull
    public This setWorld(@Nonnull World world) {
        this.world = world;
        return (This) this;
    }

    @Nonnull
    public EntityLivingBase getAttacker() {
        return attacker;
    }

    @Nonnull
    public This setAttacker(@Nonnull EntityLivingBase attacker) {
        this.attacker = attacker;
        return (This) this;
    }

    @Nonnull
    public Entity getTarget() {
        return target;
    }

    @Nonnull
    public This setTarget(@Nonnull Entity target) {
        this.target = target;
        return (This) this;
    }

    @Nonnull
    public IWeaponCore getWeaponCore() {
        return weaponCore;
    }

    @Nonnull
    public This setWeaponCore(@Nullable IWeaponCore weaponCore) {
        this.weaponCore = weaponCore;
        return (This) this;
    }

    @Nullable
    public IModifier<?> getModifier() {
        return modifier;
    }

    @Nonnull
    public This setModifier(@Nullable IModifier<?> modifier) {
        this.modifier = modifier;
        return (This) this;
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Nonnull
    public This setItemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        return (This) this;
    }

    @Nonnull
    public DamageArgs getInitialDamage() {
        return initialDamage;
    }

    @Nonnull
    public This setInitialDamage(@Nonnull DamageArgs initialDamage) {
        this.initialDamage = initialDamage;
        return (This) this;
    }

    public IGemstone getGemstone() {
        return gemstone;
    }

    public This setGemstone(IGemstone gemstone) {
        this.gemstone = gemstone;
        return (This) this;
    }
}
