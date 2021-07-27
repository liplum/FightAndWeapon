package net.liplum.attributes;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public interface IAttrCalculator extends FawArgsGetter {
    @Developing
    boolean isUseSpecialValueWhenWeaponBroken();

    @LongSupport
    boolean isPostEvent();

    @Nonnull
    @LongSupport
    FinalAttrValue calcu(@Nonnull IAttribute attribute);
}
