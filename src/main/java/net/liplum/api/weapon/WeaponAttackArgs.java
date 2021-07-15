package net.liplum.api.weapon;

import net.liplum.attributes.AttrCalculator;
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
    private WeaponBaseItem weapon;
    private IGemstone gemstone;
    private Modifier modifier;
    private ItemStack itemStack;
    private DamageArgs initialDamage;
    private AttrCalculator calculator;
    private boolean isFullAttack;

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
    public WeaponBaseItem getWeapon() {
        return weapon;
    }

    @Nonnull
    public This setWeapon(@Nullable WeaponBaseItem weapon) {
        this.weapon = weapon;
        return (This) this;
    }

    @Nullable
    public Modifier getModifier() {
        return modifier;
    }

    @Nonnull
    public This setModifier(@Nullable Modifier modifier) {
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

    public boolean isFullAttack() {
        return isFullAttack;
    }

    public This setFullAttack(boolean fullAttack) {
        isFullAttack = fullAttack;
        return (This) this;
    }

    public AttrCalculator getCalculator() {
        return calculator;
    }

    public This setCalculator(AttrCalculator calculator) {
        this.calculator = calculator;
        return (This) this;
    }
}
