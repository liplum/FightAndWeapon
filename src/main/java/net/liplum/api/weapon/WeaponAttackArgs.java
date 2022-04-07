package net.liplum.api.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.fight.FawArgsSetter;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
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

    @NotNull
    public World world() {
        return world;
    }

    @NotNull
    public This world(@NotNull World world) {
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

    @NotNull
    public EntityLivingBase attacker() {
        return attacker;
    }

    @NotNull
    public This attacker(@NotNull EntityLivingBase attacker) {
        this.attacker = attacker;
        return (This) this;
    }

    @NotNull
    public Entity target() {
        return target;
    }

    @NotNull
    public This target(@NotNull Entity target) {
        this.target = target;
        return (This) this;
    }

    @NotNull
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @NotNull
    public This weapon(@NotNull WeaponBaseItem weapon) {
        this.weapon = weapon;
        return (This) this;
    }

    @Nullable
    public Modifier modifier() {
        return modifier;
    }

    @NotNull
    public This modifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return (This) this;
    }

    @NotNull
    public ItemStack itemStack() {
        return itemStack;
    }

    @NotNull
    public This itemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        return (This) this;
    }

    @NotNull
    @Override
    public This entity(@NotNull EntityLivingBase entity) {
        this.attacker = entity;
        return (This) this;
    }

    @NotNull
    public DamageArgs initialDamage() {
        return initialDamage;
    }

    @NotNull
    public This initialDamage(@NotNull DamageArgs initialDamage) {
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
