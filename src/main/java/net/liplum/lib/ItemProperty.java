package net.liplum.lib;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class ItemProperty implements IItemPropertyGetter {
    @NotNull
    private final ResourceLocation propertyName;

    public ItemProperty(@NotNull ResourceLocation propertyName) {
        this.propertyName = propertyName;
    }

    public ItemProperty(@NotNull String propertyName) {
        this(new ResourceLocation(propertyName));
    }

    @NotNull
    public ResourceLocation getPropertyName() {
        return propertyName;
    }
}
