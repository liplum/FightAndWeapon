package net.liplum.api.weapon;

import com.google.common.collect.Multimap;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WeaponAttrModifierContext {
    @NotNull
    public final ItemStack itemStack;
    @NotNull
    public final AttrCalculator calculator;
    @NotNull
    public final EntityEquipmentSlot slot;
    @NotNull
    public final Multimap<String, AttributeModifier> attrModifierMap;

    public WeaponAttrModifierContext(@NotNull ItemStack itemStack, @NotNull AttrCalculator calculator, @NotNull EntityEquipmentSlot slot, @NotNull Multimap<String, AttributeModifier> attrModifierMap) {
        this.itemStack = itemStack;
        this.calculator = calculator;
        this.slot = slot;
        this.attrModifierMap = attrModifierMap;
    }
}
