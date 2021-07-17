package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
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

import static net.liplum.Attributes.Continuous.MaxUseDuration;
import static net.liplum.Attributes.Harp.Frequency;

public class HarpItem extends WeaponBaseItem {
    private final HarpCore core;

    public HarpItem(@Nonnull HarpCore core) {
        super(core);
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        if (core.getWeaponSkillPredicate().canRelease(worldIn, playerIn, held)) {
            Modifier modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillReleaseEvent.Pre(worldIn, playerIn, this, modifier, held, handIn)
            );
            if (!cancelRelease) {
                WeaponSkillArgs args = new WeaponSkillArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn)
                        .setWeapon(this)
                        .setModifier(modifier)
                        .setCalculator(
                                new AttrCalculator(core).setModifier(modifier).setPlayer(playerIn)
                        );

                boolean releasedSuccessfully = FawItemUtil.releaseWeaponSkill(core, modifier, args);
                if (releasedSuccessfully) {
                    FawItemUtil.heatWeaponType(playerIn, getWeaponType());
                    playerIn.resetActiveHand();
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillReleaseEvent.Post(worldIn, playerIn, this, modifier, held, handIn)
                    );
                }
            }
        } else {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        Modifier modifier = GemUtil.getModifierFrom(stack);
        EntityPlayer p = (EntityPlayer) player;
        EnumHand hand = ItemTool.inWhichHand(p, stack);
        if (hand == null) {
            return;
        }
        World world = player.world;
        AttrCalculator calculator = new AttrCalculator()
                .setWeaponCore(core)
                .setModifier(modifier)
                .setPlayer(p);
        FinalAttrValue finalFrequency = calculator.calcu(Frequency);
        FinalAttrValue finalMaxUseDuration = calculator.calcu(MaxUseDuration);
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
                    .setWeapon(this)
                    .setCalculator(
                            new AttrCalculator(core).setModifier(modifier).setPlayer(p)
                    );
            if (modifier != null) {
                HarpModifier mod = (HarpModifier) modifier;
                mod.continueSkill(core, args);
            } else {
                core.continueSkill(args);
            }
        }
        if (currentDuration == maxUseDuration - 1) {
            FawItemUtil.heatWeaponType(p, getWeaponType());
        }
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving, int timeLeft) {
        Item held = stack.getItem();
        //I'm not quite sure that the entityLiving is truly a EntityPlayer.
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entityLiving;
            FawItemUtil.heatWeaponType(p, getWeaponType());
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
        return new AttrCalculator(core).calcu(MaxUseDuration).getInt();
    }
}
