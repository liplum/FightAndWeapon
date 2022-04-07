package net.liplum.lib.gui;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TextureFactory {
    @NotNull
    private final ResourceLocation textureLocation;

    public TextureFactory(@NotNull ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    public Texture gen(View view) {
        return new Texture(textureLocation, view);
    }

    @NotNull
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
