package net.liplum.api.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.fight.FawArgsSetter;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponAttackArgs<This extends WeaponAttackArgs<?>> implements FawArgsGetter, FawArgsSetter<This> {
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
    public World world() {
        return world;
    }

    @Nonnull
    public This world(@Nonnull World world) {
        this.world = world;
        return (This) this;
    }

    /**
     * @return the attacker
     */
    @Nullable
    @Override
    public EntityLivingBase entity() {
        return attacker;
    }

    @Nonnull
    public EntityLivingBase attacker() {
        return attacker;
    }

    @Nonnull
    public This attacker(@Nonnull EntityLivingBase attacker) {
        this.attacker = attacker;
        return (This) this;
    }

    @Nonnull
    public Entity target() {
        return target;
    }

    @Nonnull
    public This target(@Nonnull Entity target) {
        this.target = target;
        return (This) this;
    }

    @Nonnull
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Nonnull
    public This weapon(@Nullable WeaponBaseItem weapon) {
        this.weapon = weapon;
        return (This) this;
    }

    @Nullable
    public Modifier modifier() {
        return modifier;
    }

    @Nonnull
    public This modifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return (This) this;
    }

    @Nonnull
    public ItemStack itemStack() {
        return itemStack;
    }

    @Nonnull
    public This itemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        return (This) this;
    }

    @Nonnull
    @Override
    public This entity(@Nonnull EntityLivingBase entity) {
        this.attacker = entity;
        return (This) this;
    }

    @Nonnull
    public DamageArgs initialDamage() {
        return initialDamage;
    }

    @Nonnull
    public This initialDamage(@Nonnull DamageArgs initialDamage) {
        this.initialDamage = initialDamage;
        return (This) this;
    }

    public IGemstone gemstone() {
        return gemstone;
    }

    public This gemstone(IGemstone gemstone) {
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

    public AttrCalculator calculator() {
        return calculator;
    }

    public This calculator(AttrCalculator calculator) {
        this.calculator = calculator;
        return (This) this;
    }
}
