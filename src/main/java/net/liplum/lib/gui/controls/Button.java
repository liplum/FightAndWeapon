package net.liplum.lib.gui.controls;

import net.liplum.lib.gui.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;

public abstract class Button extends GuiButton {
    protected Texture normalTexture;
    protected Texture hoveredTexture;
    protected Texture pressedTexture;
    protected boolean selected;

    public Button(int buttonId) {
        super(buttonId, 0, 0, "");
    }

    public Button setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    protected void genSize(Texture texture) {
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Button setTextures(Texture normalTexture, Texture hoveredTexture, Texture pressedTexture) {
        this.normalTexture = normalTexture;
        this.hoveredTexture = hoveredTexture;
        this.pressedTexture = pressedTexture;
        genSize(normalTexture);
        return this;
    }

    public Texture getNormalTexture() {
        return normalTexture;
    }

    public Button setNormalTexture(Texture normalTexture) {
        this.normalTexture = normalTexture;
        genSize(normalTexture);
        return this;
    }

    public Texture getHoveredTexture() {
        return hoveredTexture;
    }

    public Button setHoveredTexture(Texture hoveredTexture) {
        this.hoveredTexture = hoveredTexture;
        genSize(hoveredTexture);
        return this;
    }

    public Texture getPressedTexture() {
        return pressedTexture;
    }

    public Button setPressedTexture(Texture pressedTexture) {
        this.pressedTexture = pressedTexture;
        genSize(pressedTexture);
        return this;
    }

    protected boolean checkHovered(int mouseX, int mouseY) {
        return mouseX >= this.x &&
                mouseY >= y &&
                mouseX < x + width &&
                mouseY < y + height;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            hovered = checkHovered(mouseX, mouseY);
            Texture currentTexture = null;
            if (!enabled) {
                currentTexture = normalTexture;
            } else if (selected) {
                currentTexture = pressedTexture;
            } else if (hovered) {
                currentTexture = hoveredTexture;
            } else {
                currentTexture = normalTexture;
            }
            currentTexture.bind(mc);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            currentTexture.draw(this, x, y);
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public abstract void onTrigger();
}
