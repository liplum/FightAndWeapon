package net.liplum.lib.gui;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TextureFactory {
    @Nonnull
    private final ResourceLocation textureLocation;

    public TextureFactory(@Nonnull ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    public Texture gen(IView view) {
        return new Texture(textureLocation, view);
    }

    @Nonnull
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
