package net.liplum.api.fight;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@LongSupport
public interface FawArgsSetter<T extends FawArgsSetter<?>> {
    @NotNull
    @LongSupport
    T weapon(@NotNull WeaponBaseItem weaponz);

    @NotNull
    @LongSupport
    T modifier(@NotNull Modifier modifier);

    @NotNull
    @LongSupport
    T itemStack(@NotNull ItemStack itemStack);

    @NotNull
    @LongSupport
    T entity(@NotNull EntityLivingBase entity);
}
