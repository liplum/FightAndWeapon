package net.liplum.attributes;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IAttrCalculator {
    @Nonnull
    WeaponCore getWeaponCore();

    @Nullable
    Modifier getModifier();

    @Nullable
    EntityPlayer getPlayer();

    @Nullable
    ItemStack getItemStack();

    boolean isUseSpecialValueWhenWeaponBroken();

    boolean isPostEvent();

    @Nonnull
    FinalAttrValue calcu(@Nonnull IAttribute attribute);
}
