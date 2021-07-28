package net.liplum.masteries;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IBehaviorHandler {
    void handle(@Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args);

    @Nonnull
    Behavior getBehavior();
}
