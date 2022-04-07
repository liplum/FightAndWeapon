package net.liplum.api.fight;

import net.liplum.entities.FlyingItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IFlyingItemBehavior {
    void onImpact(@NotNull FlyingItemEntity weaponEntity, @NotNull Entity target, @NotNull ItemStack itemStack);
}
