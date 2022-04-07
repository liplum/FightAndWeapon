package net.liplum.api.fight;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@LongSupport
public interface FawArgsGetter {
    @NotNull
    @LongSupport
    WeaponBaseItem weapon();

    @NotNull
    @LongSupport
    default WeaponCore weaponCore() {
        return weapon().getCore();
    }

    @NotNull
    @LongSupport
    default WeaponType weaponType() {
        return weapon().getWeaponType();
    }

    @Nullable
    @LongSupport
    Modifier modifier();

    @Nullable
    @LongSupport
    ItemStack itemStack();

    @Nullable
    @LongSupport
    EntityLivingBase entity();

    @Nullable
    @LongSupport
    default EntityPlayer player() {
        EntityLivingBase entity = entity();
        return entity instanceof EntityPlayer ? (EntityPlayer) entity : null;
    }
}
