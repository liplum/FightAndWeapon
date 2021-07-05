package net.liplum.items.weapons.harp;

import net.liplum.I18ns;
import net.liplum.Vanilla;
import net.liplum.api.weapon.Modifier;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class HarpItem extends WeaponBaseItem<HarpCore> {
    private final HarpCore core;

    public HarpItem(@Nonnull HarpCore core) {
        super();
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        double radius = core.getRadius();
        float ap = core.getAbilityPower();
        ItemStack held = playerIn.getHeldItem(handIn);
        int coolDown = core.getCoolDown();
        if (playerIn.isSneaking()) {
            Modifier<?> modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
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
                            new WeaponSkillPostReleasedEvent(worldIn, playerIn, core, modifier, held, handIn)
                    );
                }
            }
        } else {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onUsingTick(ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        Item held = stack.getItem();
        Modifier<?> modifier = GemUtil.getModifierFrom(stack);
        EntityPlayer p = (EntityPlayer) player;
        EnumHand hand = ItemTool.inWhichHand(p, stack);
        if (hand == null) {
            return;
        }
        World world = player.world;
        double radius = core.getRadius();
        float ap = core.getAbilityPower();
        int coolDown = core.getCoolDown();
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

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        /* HarpUtils.HarpState state = HarpUtils.getState(stack);
        if (state == HarpUtils.HarpState.JustReleased) {
            //HarpUtils.setState(stack, HarpUtils.HarpState.AfterReleasing);
            //HarpUtils.setState(stack, HarpUtils.HarpState.Normal);
            return 1;
        }*//*else if (state == HarpUtils.HarpState.AfterReleasing) {
            HarpUtils.setState(stack, HarpUtils.HarpState.Normal);
            return normalDuration;
        }*/
        return core.getMaxUseDuration();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addAttributesTooltip(@Nonnull ItemStack stack, @Nullable Modifier<?> modifier, @Nonnull List<String> attributesTooltip, TooltipOption option) {
        boolean shown = super.addAttributesTooltip(stack, modifier, attributesTooltip, option);
        HarpModifier harpModifier = null;
        if (modifier instanceof HarpModifier) {
            harpModifier = (HarpModifier) modifier;
        }

        double radius = core.getRadius();
        if (harpModifier != null) {
            radius = FawItemUtil.calcuAttribute(radius, harpModifier.getRadiusDelta(), harpModifier.getRadiusRate());
        }
        if (radius > 0) {
            FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Harp.Radius, radius,
                    "%.1f", option.isUnitShown() ? I18ns.Tooltip.Unit.Unit : null);
            shown = true;
        }
        if (option.isMoreDetailsShown()) {
            int frequency = core.getFrequency();
            if (harpModifier != null) {
                frequency = FawItemUtil.calcuAttribute(frequency, harpModifier.getFrequencyDelta(), harpModifier.getFrequencyRate());
            }
            if (frequency > 0) {
                float frequencyDecimal = (float) frequency / Vanilla.TPS;
                FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Harp.Frequency, frequencyDecimal,
                        "%.1f", option.isUnitShown() ? I18ns.Tooltip.Unit.TriggerPerSecond : null);
                shown = true;
            }
        }
        return shown;
    }

    @Nonnull
    @Override
    public HarpCore getCore() {
        return core;
    }
}
