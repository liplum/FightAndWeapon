package net.liplum;

import net.liplum.api.registeies.WeaponActionHook;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.Behavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class FawBehaviors {
    public static void onWeaponUse(@NotNull EntityLivingBase player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack) {
        WeaponActionHook.onBehaved(Behavior.UseWeapon, player, weapon, itemStack);
    }

    public static void onKillEntityWithWeapon(@NotNull EntityLivingBase player, WeaponBaseItem weapon, @NotNull ItemStack itemStack, @NotNull Entity target) {
        WeaponActionHook.onBehaved(Behavior.KillEntity, player, weapon, itemStack, target);
    }

    public static void onCauseDamageWithWeapon(@NotNull EntityLivingBase player, WeaponBaseItem weapon, @NotNull ItemStack itemStack, @NotNull Entity target, float damage) {
        WeaponActionHook.onBehaved(Behavior.CauseDamage, player, weapon, itemStack, target, damage);
    }
}
