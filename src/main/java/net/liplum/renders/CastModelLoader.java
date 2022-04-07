package net.liplum.renders;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.jetbrains.annotations.NotNull;

public class CastModelLoader implements ICustomModelLoader {
    @Override
    public void onResourceManagerReload(@NotNull IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(@NotNull ResourceLocation modelLocation) {
        return false;
    }

    @NotNull
    @Override
    public IModel loadModel(@NotNull ResourceLocation modelLocation) throws Exception {

        return ModelLoaderRegistry.getMissingModel();
    }
}
