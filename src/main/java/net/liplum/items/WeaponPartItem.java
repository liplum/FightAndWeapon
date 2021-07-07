package net.liplum.items;

import net.liplum.I18ns;
import net.liplum.api.registeies.WeaponPartRegistry;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.lib.nbt.FawNbtTool;
import net.liplum.lib.utils.FawI18n;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class WeaponPartItem extends Item {
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (String name : WeaponPartRegistry.getAllWeaponPartNames()) {
                ItemStack itemStack = new ItemStack(this);
                FawNbtTool.setWeaponPart(itemStack, name);
                items.add(itemStack);
            }
        }
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String weaponPartName = FawNbtTool.getWeaponPart(stack);
        WeaponPart weaponPart = WeaponPartRegistry.getWeaponPart(weaponPartName);
        if (weaponPart != null) {
            tooltip.add(TextFormatting.BLUE + I18n.format(FawI18n.getNameI18nKey(weaponPart)));
        } else {
            tooltip.add(TextFormatting.RED + I18n.format(I18ns.Tooltip.NoSuchWeaponPart));
        }
    }
}
