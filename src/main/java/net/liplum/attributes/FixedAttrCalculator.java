package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public class FixedAttrCalculator implements IAttrCalculator {
    @Nonnull
    private final WeaponBaseItem weapon;
    @Nullable
    private final Modifier modifier;
    @Nullable
    private final EntityLivingBase entity;
    @Nullable
    private final ItemStack itemStack;

    private final boolean postEvent;

    private final boolean useSpecialValueWhenWeaponBroken;

    @LongSupport
    public FixedAttrCalculator(@Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nullable EntityLivingBase entity, @Nullable ItemStack itemStack, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.entity = entity;
        this.postEvent = postEvent;
        this.useSpecialValueWhenWeaponBroken = useSpecialValueWhenWeaponBroken;
    }

    @LongSupport
    public FixedAttrCalculator(@Nonnull WeaponBaseItem weapon, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(weapon, null, null, null, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @LongSupport
    public FixedAttrCalculator(@Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nullable ItemStack itemStack, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(weapon, modifier, null, itemStack, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @LongSupport
    public FixedAttrCalculator(@Nonnull WeaponBaseItem weapon, @Nullable EntityPlayer player, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(weapon, null, player, null, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @Nonnull
    @Override
    public WeaponBaseItem weapon() {
        return weapon;
    }
    @Nullable
    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Nullable
    @Override
    public EntityLivingBase entity() {
        return entity;
    }

    @Nullable
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }

    @Override
    public boolean isPostEvent() {
        return postEvent;
    }

    @Nonnull
    @Override
    public FinalAttrValue calcu(@Nonnull IAttribute attribute) {
        return AttrCalculator.calcuAttribute(attribute, weapon, modifier, player(), itemStack, true, true);
    }
}
