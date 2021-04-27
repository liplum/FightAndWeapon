package net.liplum.items.weapons;

import net.liplum.registeies.ItemGroupRegistries;
import net.liplum.registeies.TierRegistries;
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
    public QuartzSwordItem() {
        super(TierRegistries.QUARTZ_TIER,
                3,
                -2.4F,
                new Properties().tab(ItemGroupRegistries.FawItemGroup));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(world.isClientSide) {
            StringTextComponent str = new StringTextComponent("YEAH!!!");
            player.sendMessage(str, UUID.randomUUID());
        }
        return new ActionResult<>(ActionResultType.SUCCESS,player.getItemInHand(hand));
    }
}
