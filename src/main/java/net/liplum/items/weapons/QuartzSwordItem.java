package net.liplum.items.weapons;

import net.liplum.lib.weapons.WeaponBaseItem;

public class QuartzSwordItem extends WeaponBaseItem {
    public QuartzSwordItem(ToolMaterial material) {
        super(material);
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
