package net.liplum.items.weapons.bow;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

public class BowItem extends WeaponBaseItem {
    private final BowCore core;

    public BowItem(@Nonnull BowCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
        this.addProperty();
    }

    protected void addProperty() {

    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        if (FawItemUtil.isWeaponBroken(held)) {
            return ActionResult.newResult(EnumActionResult.FAIL, held);
        }
        if (playerIn.isCreative() || FawItemUtil.hasAmmo(playerIn)) {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving, int useTime) {
        EntityPlayer player = (EntityPlayer) entityLiving;
        useTime = ForgeEventFactory.onArrowLoose(stack, worldIn, player, useTime, true);
        if (useTime < 5) {
            return;
        }
        FawItemUtil.onWeaponUse(player,this);
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, useTime);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        if (core.isCheckPulling()) {
            EntityPlayer p = (EntityPlayer) player;
            EnumHand hand = ItemTool.inWhichHand(p, stack);
            if (hand == null) {
                return;
            }
            Modifier modifier = GemUtil.getModifierFrom(stack);
            AttrCalculator calculator = new AttrCalculator()
                    .weaponCore(core)
                    .modifier(modifier)
                    .entity(p);
            PullingBowArgs args = new PullingBowArgs();
            args.setPullingTick(count)
                    .entity(p)
                    .setHand(hand)
                    .world(p.world)
                    .setWeapon(this)
                    .itemStack(stack)
                    .modifier(modifier)
                    .setCalculator(calculator);
            core.onPulling(args);
        }
    }
}
