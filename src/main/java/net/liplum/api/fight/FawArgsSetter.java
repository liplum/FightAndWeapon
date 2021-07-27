package net.liplum.api.fight;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public interface FawArgsSetter<T extends FawArgsSetter<?>> {
    @Nonnull
    @LongSupport
    T weapon(@Nonnull WeaponBaseItem weaponz);

    @Nonnull
    @LongSupport
    T modifier(@Nonnull Modifier modifier);

    @Nonnull
    @LongSupport
    T itemStack(@Nonnull ItemStack itemStack);

    @Nonnull
    @LongSupport
    T entity(@Nonnull EntityLivingBase entity);
}
