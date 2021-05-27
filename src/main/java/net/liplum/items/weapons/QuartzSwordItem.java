package net.liplum.items.weapons;

import net.liplum.lib.items.WeaponBaseItem;
import net.minecraft.item.ItemSword;

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
}
