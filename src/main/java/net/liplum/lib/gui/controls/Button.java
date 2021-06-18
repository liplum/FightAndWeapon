package net.liplum.lib.gui.controls;

import net.liplum.lib.gui.IView;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class Button extends GuiButton {
    protected ResourceLocation texture;
    protected IView view;

    public Button(int buttonId,  String buttonText) {
        super(buttonId, 0, 0, buttonText);
    }

    public Button setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public Button setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public IView getView() {
        return view;
    }

    public Button setView(IView view) {
        this.view = view;
        return this;
    }
}
