package net.liplum.masteries;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IBehaviorHandler {
    void handle(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack, Object... args);

    @NotNull
    Behavior getBehavior();
}
