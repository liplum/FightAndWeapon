package net.liplum.tiers;

import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;

public class QuartzTier implements IItemTier {
    @Override
    public int getMaxUses() {
        return 80;
    }

    @Override
    public float getEfficiency() {
        return 10.0F;
    }

    @Override
    public float getAttackDamage() {
        return 10;
    }

    @Override
    public int getHarvestLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.EMPTY;
    }
}
