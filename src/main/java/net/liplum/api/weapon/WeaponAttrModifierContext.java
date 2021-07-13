package net.liplum.api.weapon;

import com.google.common.collect.Multimap;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class WeaponAttrModifierContext {
    @Nonnull
    public final ItemStack itemStack;
    @Nonnull
    public final AttrCalculator calculator;
    @Nonnull
    public final EntityEquipmentSlot slot;
    @Nonnull
    public final Multimap<String, AttributeModifier> attrModifierMap;

    public WeaponAttrModifierContext(@Nonnull ItemStack itemStack, @Nonnull AttrCalculator calculator, @Nonnull EntityEquipmentSlot slot, @Nonnull Multimap<String, AttributeModifier> attrModifierMap) {
        this.itemStack = itemStack;
        this.calculator = calculator;
        this.slot = slot;
        this.attrModifierMap = attrModifierMap;
    }
}
