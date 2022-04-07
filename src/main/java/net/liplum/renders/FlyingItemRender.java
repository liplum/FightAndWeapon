package net.liplum.renders;

import net.liplum.entities.FlyingItemEntity;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FlyingItemRender extends RenderSnowball<FlyingItemEntity> {
    public FlyingItemRender(@NotNull RenderManager renderManagerIn, @NotNull Item itemIn, @NotNull RenderItem itemRendererIn) {
        super(renderManagerIn, itemIn, itemRendererIn);
    }

    @NotNull
    @Override
    public ItemStack getStackToRender(@NotNull FlyingItemEntity flyingItem) {
        return flyingItem.getFlyingItem();
    }
}
