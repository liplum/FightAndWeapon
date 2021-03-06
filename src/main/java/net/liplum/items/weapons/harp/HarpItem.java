package net.liplum.items.weapons.harp;

import net.liplum.FawBehaviors;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.FawUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import static net.liplum.Attributes.Generic.MaxUseDuration;
import static net.liplum.Attributes.Harp.Frequency;

public class HarpItem extends WeaponBaseItem {
    private final HarpCore core;

    public HarpItem(@NotNull HarpCore core) {
        super(core);
        this.core = core;
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        if (core.getWeaponSkillPredicate().canRelease(worldIn, playerIn, held)) {
            Modifier modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillReleaseEvent.Pre(worldIn, playerIn, this, modifier, held, handIn)
            );
            if (!cancelRelease) {
                WeaponSkillArgs args = new WeaponSkillArgs()
                        .world(worldIn)
                        .entity(playerIn)
                        .itemStack(held)
                        .hand(handIn)
                        .weapon(this)
                        .modifier(modifier);
                args.calculator(FawUtil.toCalculator(args));

                boolean releasedSuccessfully = FawItemUtil.releaseWeaponSkill(core, modifier, args);
                if (releasedSuccessfully) {
                    FawItemUtil.heatWeaponType(playerIn, getWeaponType());
                    playerIn.resetActiveHand();
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillReleaseEvent.Post(worldIn, playerIn, this, modifier, held, handIn)
                    );
                    FawBehaviors.onWeaponUse(playerIn, this, held);
                }
            }
        } else {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onUsingTick(@NotNull ItemStack stack, @NotNull EntityLivingBase player, int count) {
        Modifier modifier = GemUtil.getModifierFrom(stack);
        EntityPlayer p = (EntityPlayer) player;
        EnumHand hand = ItemUtil.inWhichHand(p, stack);
        if (hand == null) {
            return;
        }
        World world = player.world;
        AttrCalculator calculator = new AttrCalculator()
                .weapon(this)
                .modifier(modifier)
                .itemStack(stack)
                .entity(p);
        FinalAttrValue finalFrequency = calculator.calcu(Frequency);
        FinalAttrValue finalMaxUseDuration = calculator.calcu(MaxUseDuration);
        int frequency = finalFrequency.getInt();
        int maxUseDuration = finalMaxUseDuration.getInt();

        int currentDuration = maxUseDuration - count;
        int releasedCount = currentDuration / frequency;
        if (currentDuration % frequency == 0) {
            ContinuousHarpArgs args = new ContinuousHarpArgs()
                    .setReleasedCount(releasedCount);
            args.world(world)
                    .entity(p)
                    .itemStack(stack)
                    .hand(hand)
                    .weapon(this)
                    .modifier(modifier);
            args.calculator(FawUtil.toCalculator(args));
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
    public void onPlayerStoppedUsing(ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving, int timeLeft) {
        Item held = stack.getItem();
        //I'm not quite sure that the entityLiving is truly a EntityPlayer.
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entityLiving;
            FawItemUtil.heatWeaponType(p, getWeaponType());
            p.resetActiveHand();
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @NotNull
    @Override
    public HarpCore getConcreteCore() {
        return core;
    }
}
