package net.liplum.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ILeftClickEntityBehavior {
    boolean onLeftClickEntity(@NotNull WeaponBaseItem weaponItem, @NotNull ItemStack stack, @NotNull EntityPlayer attacker, @NotNull Entity target);
}
