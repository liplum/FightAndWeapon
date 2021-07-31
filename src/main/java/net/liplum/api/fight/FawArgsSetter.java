package net.liplum.api.fight;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

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
