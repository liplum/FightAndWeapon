package net.liplum;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.Behavior;
import net.liplum.masteries.MasterySheet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public final class FawBehaviors {
    public static void onWeaponUse(@Nonnull EntityLivingBase player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack) {
        MasterySheet.onBehaved(Behavior.UseWeapon,player,weapon,itemStack);
    }

    public static void onKillEntityWithWeapon(@Nonnull EntityLivingBase player, WeaponBaseItem weapon, @Nonnull ItemStack itemStack) {

    }
}
