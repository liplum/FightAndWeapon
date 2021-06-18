package net.liplum.lib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class Texture {
    @Nonnull
    private final ResourceLocation texture;
    @Nonnull
    private final IView view;

    public Texture(@Nonnull ResourceLocation textureLocation, @Nonnull IView view) {
        this.texture = textureLocation;
        this.view = view;
    }

    public void bind(GuiContainer container) {
        container.mc.getTextureManager().bindTexture(texture);
    }

    public void draw(GuiContainer container, int x, int y) {
        container.drawTexturedModalRect(x, y,
                view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
    }
}
