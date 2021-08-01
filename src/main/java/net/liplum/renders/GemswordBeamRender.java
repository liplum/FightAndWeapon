package net.liplum.renders;

import net.liplum.entities.GemswordBeam;
import net.liplum.lib.FawLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GemswordBeamRender extends Render<GemswordBeam> {
    private static final ResourceLocation Texture =
            new FawLocation("textures/entity/gemsword_beam.png");

    protected GemswordBeamRender(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull GemswordBeam entity) {
        return Texture;
    }
}
