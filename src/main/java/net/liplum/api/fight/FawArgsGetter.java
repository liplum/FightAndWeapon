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
public interface FawArgsGetter {
    @Nonnull
    @LongSupport
    WeaponBaseItem weapon();

    @Nonnull
    @LongSupport
    default WeaponCore weaponCore(){
        return weapon().getCore();
    }

    @Nonnull
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
