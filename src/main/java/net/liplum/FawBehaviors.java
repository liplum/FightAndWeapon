package net.liplum;

import net.liplum.api.registeies.WeaponActionHook;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.Behavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public final class FawBehaviors {
    public static void onWeaponUse(@Nonnull EntityLivingBase player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack) {
        WeaponActionHook.onBehaved(Behavior.UseWeapon, player, weapon, itemStack);
    }

    public static void onKillEntityWithWeapon(@Nonnull EntityLivingBase player, WeaponBaseItem weapon, @Nonnull ItemStack itemStack, @Nonnull Entity target) {
        WeaponActionHook.onBehaved(Behavior.KillEntity, player, weapon, itemStack, target);
    }

    public static void onCauseDamageWithWeapon(@Nonnull EntityLivingBase player, WeaponBaseItem weapon, @Nonnull ItemStack itemStack, @Nonnull Entity target, float damage) {
        WeaponActionHook.onBehaved(Behavior.CauseDamage, player, weapon, itemStack, target, damage);
    }
}
