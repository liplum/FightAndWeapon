package net.liplum.renders;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.IModelState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class WeaponPartModel implements IModel {
    /**
     * Only single one
     */
    private final ImmutableList<ResourceLocation> texture;

    public WeaponPartModel(ResourceLocation texture) {
        this.texture = ImmutableList.of(texture);
    }

    @NotNull
    @Override
    public IBakedModel bake(@NotNull IModelState state, @NotNull VertexFormat format, @NotNull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IBakedModel base = new ItemLayerModel(texture).bake(state, format, bakedTextureGetter);

        return null;
    }

    public static class BakedCastModel implements IBakedModel {

        @NotNull
        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return null;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return false;
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @NotNull
        @Override
        public TextureAtlasSprite getParticleTexture() {
            return null;
        }

        @NotNull
        @Override
        public ItemOverrideList getOverrides() {
            return null;
        }
    }
}
