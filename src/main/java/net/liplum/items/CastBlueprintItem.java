package net.liplum.items;

import net.liplum.I18ns;
import net.liplum.api.registeies.CastRegistry;
import net.liplum.api.registeies.WeaponPartRegistry;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.capabilities.CastStudyCapability;
import net.liplum.lib.nbt.FawNbtTool;
import net.liplum.lib.utils.FawI18n;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class CastBlueprintItem extends Item {
    @Override
    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (String name : CastRegistry.getAllCastNames()) {
                ItemStack itemStack = new ItemStack(this);
                FawNbtTool.setWeaponPart(itemStack, name);
                items.add(itemStack);
            }
        }
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        String weaponPartName = FawNbtTool.getWeaponPart(heldItem);
        CastStudyCapability castStudyCapability = playerIn.getCapability(CapabilityRegistry.CastStudy_Capability, null);
        if (castStudyCapability != null) {
            boolean studySuccessfully = castStudyCapability.studyNew(weaponPartName);
        }
        if (!playerIn.isCreative()) {
            heldItem.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
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
