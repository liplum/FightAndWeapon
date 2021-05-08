package net.liplum.lib.items;

import net.minecraft.item.ItemStack;

public abstract class WeaponBaseItem extends FAWItem {
    public WeaponBaseItem() {
        super();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }
}
