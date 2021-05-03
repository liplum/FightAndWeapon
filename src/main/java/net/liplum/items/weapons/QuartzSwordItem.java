package net.liplum.items.weapons;

import net.liplum.registeies.TierRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.UUID;

public class QuartzSwordItem extends SwordItem{
    public QuartzSwordItem(Properties properties) {
        super(TierRegistry.QUARTZ_TIER,
                3,
                -2.4F,
                properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) {
            StringTextComponent str = new StringTextComponent("YEAH!!!");
            playerIn.sendMessage(str, UUID.randomUUID());
        }
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }
}
