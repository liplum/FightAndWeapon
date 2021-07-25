package net.liplum.attributes;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public interface IAttrCalculator {
    @Nonnull
    @LongSupport
    WeaponCore getWeaponCore();

    @Nullable
    @LongSupport
    Modifier getModifier();

    @Nullable
    @LongSupport
    EntityPlayer getPlayer();

    @Nullable
    @LongSupport
    ItemStack getItemStack();

    @Developing
    boolean isUseSpecialValueWhenWeaponBroken();

    @LongSupport
    boolean isPostEvent();

    @Nonnull
    @LongSupport
    FinalAttrValue calcu(@Nonnull IAttribute attribute);
}
