package net.liplum.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class Texture {
    @NotNull
    private final ResourceLocation texture;
    @NotNull
    private final View view;

    public Texture(@NotNull ResourceLocation textureLocation, @NotNull View view) {
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
