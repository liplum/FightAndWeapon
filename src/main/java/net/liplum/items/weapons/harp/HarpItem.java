package net.liplum.items.weapons.harp;

import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.items.weaponutils.HarpUtils;
import net.liplum.lib.utils.ItemTool;
import net.liplum.lib.weaponcores.IHarpCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class HarpItem extends WeaponBaseItem<IHarpCore> {
    private IHarpCore core;

    public HarpItem(@Nonnull IHarpCore core) {
        super();
        this.core = core;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        double radius = core.getRadius();
        float ap = core.getAbilityPower();
        ItemStack held = playerIn.getHeldItem(handIn);
        int coolDown = getCoolDown();
        if (playerIn.isSneaking()) {
            boolean releasedSuccessfully = core.releaseSkill(worldIn, playerIn, held, handIn, radius, ap);
            if (releasedSuccessfully) {
                ItemTool.heatWeaponIfSurvival(playerIn, held.getItem(), coolDown);
                playerIn.resetActiveHand();
            }
        } else {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        Item held = stack.getItem();
        EntityPlayer p = (EntityPlayer) player;
        EnumHand hand = ItemTool.inWhichHand(p, stack);
        if (hand == null) {
            return;
        }
        World world = player.world;
        double radius = core.getRadius();
        float ap = core.getAbilityPower();
        int coolDown = getCoolDown();
       /* if (player.isSneaking()) {
            boolean releasedSuccessfully = core.releaseSkill(world, p, stack, hand, radius, ap);
            if (releasedSuccessfully) {
                ItemTool.heatWeaponIfSurvival(p, held, coolDown);
                //HarpUtils.setState(stack, HarpUtils.HarpState.JustReleased);
                player.resetActiveHand();
            }
            return;
        } else {*/
        int frequency = core.getFrequency();
        int maxDuration = core.getMaxUseDuration();

        int currentDuration = maxDuration - count;
        int releasedCount = currentDuration / frequency;
        if (currentDuration % frequency == 0) {

        /*Modifier modifier = FawGemUtil.getModifierFrom(stack);
        if (modifier != null) {
            HarpModifier mod = (HarpModifier) modifier;
            maxDuration =  (int) FawItemUtil.calcuAttribute(maxDuration,mod.getMaxUseDelta(),mod.getMaxUseRate());
            frequency = (int) FawItemUtil.calcuAttribute(frequency,mod.getFrequencyDelta(),mod.getFrequencyRate());
        }else {

        }*/
            core.continueSkill(world, p, stack, hand, radius, ap, releasedCount);

        }
        if (currentDuration == maxDuration - 1) {
            ItemTool.heatWeaponIfSurvival(p, held, coolDown);
        }
        //}
        return;
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int coolDown = getCoolDown();
        Item held = stack.getItem();
        //I'm not quite sure that the entityLiving is truly a EntityPlayer.
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entityLiving;
            ItemTool.heatWeaponIfSurvival(p, held, coolDown);
            p.resetActiveHand();
        }
        //HarpUtils.setState(stack, HarpUtils.HarpState.Normal);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        int normalDuration = core.getMaxUseDuration();
       /* HarpUtils.HarpState state = HarpUtils.getState(stack);
        if (state == HarpUtils.HarpState.JustReleased) {
            //HarpUtils.setState(stack, HarpUtils.HarpState.AfterReleasing);
            //HarpUtils.setState(stack, HarpUtils.HarpState.Normal);
            return 1;
        }*//*else if (state == HarpUtils.HarpState.AfterReleasing) {
            HarpUtils.setState(stack, HarpUtils.HarpState.Normal);
            return normalDuration;
        }*/
        return normalDuration;
    }
    @Override
    public IHarpCore getCore() {
        return core;
    }
}
