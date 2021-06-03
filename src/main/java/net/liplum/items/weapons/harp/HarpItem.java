package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.IModifier;
import net.liplum.events.WeaponSkillReleasePostEvent;
import net.liplum.events.WeaponSkillReleasePreEvent;
import net.liplum.lib.cores.harp.ContinuousHarpArgs;
import net.liplum.lib.cores.harp.IHarpCore;
import net.liplum.lib.cores.harp.SingleHarpArgs;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.modifiers.HarpModifier;
import net.liplum.lib.utils.FawGemUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

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
        int coolDown = core.getCoolDown();
        if (playerIn.isSneaking()) {
            IModifier modifier = FawGemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillReleasePreEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            if (!cancelRelease) {
                if (modifier != null) {
                    HarpModifier mod = (HarpModifier) modifier;
                    ap = FawItemUtil.calcuAttribute(ap, mod.getAbilityPowerDelta(), mod.getAbilityPowerRate());
                    radius = FawItemUtil.calcuAttribute(radius, mod.getRadiusDelta(), mod.getRadiusRate());
                    coolDown = FawItemUtil.calcuAttributeInRate(coolDown, mod.getCoolDownRate());
                }
                SingleHarpArgs args = new SingleHarpArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn)
                        .setAbilityPower(ap)
                        .setRadius(radius);
                boolean releasedSuccessfully = core.releaseSkill(args);
                if (releasedSuccessfully) {
                    ItemTool.heatWeaponIfSurvival(playerIn, held.getItem(), coolDown);
                    playerIn.resetActiveHand();
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillReleasePostEvent(worldIn, playerIn, core, modifier, held, handIn)
                    );
                }
            }
        } else {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        Item held = stack.getItem();
        IModifier modifier = FawGemUtil.getModifierFrom(stack);
        EntityPlayer p = (EntityPlayer) player;
        EnumHand hand = ItemTool.inWhichHand(p, stack);
        if (hand == null) {
            return;
        }
        World world = player.world;
        double radius = core.getRadius();
        float ap = core.getAbilityPower();
        int coolDown = core.getCoolDown();
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

        if (modifier != null) {
            HarpModifier mod = (HarpModifier) modifier;
            ap = FawItemUtil.calcuAttribute(ap, mod.getAbilityPowerDelta(), mod.getAbilityPowerRate());
            radius = FawItemUtil.calcuAttribute(radius, mod.getRadiusDelta(), mod.getRadiusRate());
            frequency = FawItemUtil.calcuAttribute(frequency, mod.getFrequencyDelta(), mod.getFrequencyRate());
            frequency = MathUtil.fixMin(frequency, 1);
        }
        int currentDuration = maxDuration - count;
        int releasedCount = currentDuration / frequency;
        if (currentDuration % frequency == 0) {
            ContinuousHarpArgs args = new ContinuousHarpArgs()
                    .setWorld(world)
                    .setPlayer(p)
                    .setItemStack(stack)
                    .setHand(hand)
                    .setAbilityPower(ap)
                    .setRadius(radius)
                    .setReleasedCount(releasedCount);
            core.continueSkill(args);

        }
        if (currentDuration == maxDuration - 1) {
            ItemTool.heatWeaponIfSurvival(p, held, coolDown);
        }
        //}
        return;
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int coolDown = core.getCoolDown();
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
