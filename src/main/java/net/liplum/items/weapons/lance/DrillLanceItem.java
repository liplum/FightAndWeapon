package net.liplum.items.weapons.lance;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.FawUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class DrillLanceItem extends WeaponBaseItem {
    private final LanceCore core;

    public DrillLanceItem(@NotNull LanceCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.FAIL;
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
                result = EnumActionResult.SUCCESS;
            }
        }
        return new ActionResult<>(result, held);
    }

    @NotNull
    @Override
    public LanceCore getConcreteCore() {
        return core;
    }

    @Override
    public void onUsingTick(@NotNull ItemStack stack, @NotNull EntityLivingBase player, int count) {
        EnumHand hand = ItemUtil.inWhichHand(player, stack);
        hand = hand == null ? EnumHand.MAIN_HAND : hand;
        WeaponSkillArgs args = new WeaponSkillArgs()
                .weapon(this)
                .entity(player)
                .itemStack(stack)
                .hand(hand)
                .modifier(GemUtil.getModifierFrom(stack))
                .world(player.world);
        AttrCalculator calculator = FawUtil.toCalculator(args);
        args.calculator(calculator);
        core.onContinuousEffectTick(args, count);
    }

    @Override
    public void onPlayerStoppedUsing(@NotNull ItemStack stack, @NotNull World worldIn,
                                     @NotNull EntityLivingBase entityLiving, int timeLeft) {
        EnumHand hand = ItemUtil.inWhichHand(entityLiving, stack);
        hand = hand == null ? EnumHand.MAIN_HAND : hand;
        int maxUseDuration = getMaxItemUseDuration(stack);
        int usedTicks = maxUseDuration - timeLeft;
        WeaponSkillArgs args = new WeaponSkillArgs()
                .weapon(this)
                .entity(entityLiving)
                .itemStack(stack)
                .hand(hand)
                .modifier(GemUtil.getModifierFrom(stack))
                .world(entityLiving.world);
        AttrCalculator calculator = FawUtil.toCalculator(args);
        args.calculator(calculator);
        core.onContinuousEffectStop(args, usedTicks, timeLeft);
        FawItemUtil.heatWeaponType(entityLiving, getWeaponType());
    }
}
