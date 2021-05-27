package net.liplum.items.weapons;

import net.liplum.Tags;
import net.liplum.lib.utils.FawNbtTool;
import net.liplum.registeies.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class QuartzSwordItem extends ItemSword {
    public QuartzSwordItem() {
        super(ToolMaterial.IRON);
    }

/*    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) {
            StringTextComponent str = new StringTextComponent("YEAH!!!");
            playerIn.sendMessage(str, UUID.randomUUID());
        }
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }*/

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack item = new ItemStack(ItemRegistry.Lance_Item);

        NBTTagList gemstoneList = FawNbtTool.getGemstoneList(item);

        NBTTagCompound ruby = new NBTTagCompound();
        NBTTagString rubyName = new NBTTagString("Ruby");
        ruby.setTag(Tags.BaseSub.GemstoneObject.Gemstone,rubyName);

        gemstoneList.appendTag(ruby);
        playerIn.addItemStackToInventory(item);
        return new ActionResult<>(EnumActionResult.SUCCESS,playerIn.getHeldItem(handIn));
    }
}
