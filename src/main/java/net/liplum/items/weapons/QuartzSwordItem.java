package net.liplum.items.weapons;

import net.liplum.registeies.ItemGroupRegistries;
import net.liplum.registeies.TierRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class QuartzSwordItem extends SwordItem implements ITestInterface{
    public QuartzSwordItem() {
        super(TierRegistries.QUARTZ_TIER,
                3,
                -2.4F,
                new Properties().tab(ItemGroupRegistries.FawItemGroup));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        /*StringTextComponent str= new StringTextComponent("");
        player.sendMessage(str);*/
        return null;
    }
}
