package net.liplum.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class MusicEnchantment extends Enchantment {
    public MusicEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1*enchantmentLevel*5;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 5*enchantmentLevel*5;
    }
}