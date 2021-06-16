package net.liplum.items;

import net.liplum.api.registeies.CastRegistry;
import net.liplum.lib.nbt.FawNbtTool;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class CastBlueprintItem extends Item {
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        for (String name : CastRegistry.getAllCastNames()) {
            ItemStack itemStack = new ItemStack(this);
            FawNbtTool.setWeaponPart(itemStack, name);
            items.add(itemStack);
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
