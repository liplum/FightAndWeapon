package net.liplum.renders;

import net.liplum.entities.FlyingItemEntity;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class FlyingItemRender extends RenderSnowball<FlyingItemEntity> {
    public FlyingItemRender(@Nonnull RenderManager renderManagerIn, @Nonnull Item itemIn, @Nonnull RenderItem itemRendererIn) {
        super(renderManagerIn, itemIn, itemRendererIn);
    }

    @Nonnull
    @Override
    public ItemStack getStackToRender(@Nonnull FlyingItemEntity flyingItem) {
        return flyingItem.getFlyingItem();
    }
}
