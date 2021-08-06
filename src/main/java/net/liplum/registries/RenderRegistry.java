package net.liplum.registries;

import net.liplum.entities.FlyingItemEntity;
import net.liplum.renders.FlyingItemRender;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public final class RenderRegistry {

    public static void onRegister() {
        RenderingRegistry.registerEntityRenderingHandler(
                FlyingItemEntity.class,
                manager -> new FlyingItemRender(manager,
                        ItemRegistry.Athame_Item,
                        Minecraft.getMinecraft().getRenderItem()));
    }
}

