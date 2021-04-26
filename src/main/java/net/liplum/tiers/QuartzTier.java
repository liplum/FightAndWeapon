package net.liplum.tiers;

import net.liplum.registeies.ItemRegistries;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;

public class QuartzTier implements IItemTier {
    @Override
    public int getUses() {
        return 80;
    }

    @Override
    public float getSpeed() {
        return 10.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }
}
