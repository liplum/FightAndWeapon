package net.liplum.lib;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public abstract class ItemProperty implements IItemPropertyGetter {
    @Nonnull
    private final ResourceLocation propertyName;

    public ItemProperty(@Nonnull ResourceLocation propertyName) {
        this.propertyName = propertyName;
    }

    public ItemProperty(@Nonnull String propertyName) {
        this(new ResourceLocation(propertyName));
    }

    @Nonnull
    public ResourceLocation getPropertyName() {
        return propertyName;
    }
}
