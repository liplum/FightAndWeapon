package net.liplum.api.weapon;

import net.liplum.attributes.AttrCalculator;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class WeaponAttrModifierContext {
    @Nonnull
    public final ItemStack itemStack;
    @Nonnull
    public final AttrCalculator calculator;

    public WeaponAttrModifierContext(@Nonnull ItemStack itemStack, @Nonnull AttrCalculator calculator) {
        this.itemStack = itemStack;
        this.calculator = calculator;
    }
}
