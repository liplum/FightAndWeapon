package net.liplum.items.weapons.harp;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
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

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Generic.AbilityPower;
import static net.liplum.Attributes.Generic.CoolDown;
import static net.liplum.Attributes.Harp.*;

public class HarpItem extends WeaponBaseItem {
    private final HarpCore core;

    public HarpItem(@Nonnull HarpCore core) {
        super();
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        if (core.getWeaponSkillPredicate().canRelease(worldIn, playerIn, held)) {
            Modifier modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            FinalAttrValue finalRadius = FawItemUtil.calcuAttribute(Radius, core, modifier, playerIn);
            FinalAttrValue finalAP = FawItemUtil.calcuAttribute(AbilityPower, core, modifier, playerIn);
            FinalAttrValue finalCoolDown = FawItemUtil.calcuAttribute(CoolDown, core, modifier);
            if (!cancelRelease) {
                WeaponSkillArgs args = new WeaponSkillArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn)
                        .setWeapon(this)
                        .setModifier(modifier);

                boolean releasedSuccessfully = FawItemUtil.releaseWeaponSkill(core,modifier,args);
                if (releasedSuccessfully) {
                    FawItemUtil.heatWeaponType(playerIn, getWeaponType(), finalCoolDown.getInt());
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
        Modifier modifier = GemUtil.getModifierFrom(stack);
        EntityPlayer p = (EntityPlayer) player;
        EnumHand hand = ItemTool.inWhichHand(p, stack);
        if (hand == null) {
            return;
        }
        World world = player.world;
        FinalAttrValue finalCoolDown = FawItemUtil.calcuAttribute(CoolDown, core, modifier);
        FinalAttrValue finalFrequency = FawItemUtil.calcuAttribute(Frequency, core, modifier);
        FinalAttrValue finalMaxUseDuration = FawItemUtil.calcuAttribute(MaxUseDuration, core, modifier);
        int frequency = finalFrequency.getInt();
        int maxUseDuration = finalMaxUseDuration.getInt();

        int currentDuration = maxUseDuration - count;
        int releasedCount = currentDuration / frequency;
        if (currentDuration % frequency == 0) {
            ContinuousHarpArgs args = new ContinuousHarpArgs()
                    .setReleasedCount(releasedCount);
            args.setWorld(world)
                    .setPlayer(p)
                    .setItemStack(stack)
                    .setHand(hand)
                    .setWeapon(this);
            if (modifier != null) {
                HarpModifier mod = (HarpModifier) modifier;
                mod.continueSkill(core, args);
            } else {
                core.continueSkill(args);
            }
        }
        if (currentDuration == maxUseDuration - 1) {
            FawItemUtil.heatWeaponType(p, getWeaponType(), finalCoolDown.getInt());
        }
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving, int timeLeft) {
        Item held = stack.getItem();
        //I'm not quite sure that the entityLiving is truly a EntityPlayer.
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entityLiving;
            Modifier modifier = GemUtil.getModifierFrom(stack);
            FinalAttrValue finalCoolDown = FawItemUtil.calcuAttribute(CoolDown, core, modifier);
            FawItemUtil.heatWeaponType(p, getWeaponType(), finalCoolDown.getInt());
            p.resetActiveHand();
        }
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return FawItemUtil.calcuAttribute(MaxUseDuration, core).getInt();
    }

    @Nonnull
    @Override
    public HarpCore getCore() {
        return core;
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Harp;
    }
}
