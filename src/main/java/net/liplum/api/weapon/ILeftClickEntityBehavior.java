package net.liplum.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface ILeftClickEntityBehavior {
    boolean onLeftClickEntity(@Nonnull WeaponBaseItem weaponItem,@Nonnull ItemStack stack, @Nonnull EntityPlayer attacker, @Nonnull Entity target);}
