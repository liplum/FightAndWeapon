package net.liplum.api.fight;

import net.liplum.entities.FlyingItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IFlyingItemBehavior {
    void onImpact(@Nonnull FlyingItemEntity weaponEntity, @Nonnull Entity target, @Nonnull ItemStack itemStack);
}
