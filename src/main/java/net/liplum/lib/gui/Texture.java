package net.liplum.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class Texture {
    @Nonnull
    private final ResourceLocation texture;
    @Nonnull
    private final View view;

    public Texture(@Nonnull ResourceLocation textureLocation, @Nonnull View view) {
        this.texture = textureLocation;
        this.view = view;
    }

    public void bind(Minecraft mc) {
        mc.getTextureManager().bindTexture(texture);
    }

    public void draw(Gui gui, int x, int y) {
        gui.drawTexturedModalRect(x, y,
                view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
    }

    public int getWidth() {
        return view.getWidth();
    }

    public int getHeight() {
        return view.getHeight();
    }
}
